package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import play.libs.F.Promise;
import play.mvc.Action.Simple;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;
import sun.misc.BASE64Encoder;

import com.appirio.diageo.db.manager.ContactDBManager;
import com.appirio.diageo.db.manager.SecurityDBManager;

public class SecureAction extends Simple {

	private static final String SIGNATURE_KEY = "dFtYHa1875DgHbBHyraVs3sFdcaeDerDkLMno9";
	private static final boolean SECURITY_ENABLED = !(System.getenv("SECURITY_DISABLED") != null && System.getenv("SECURITY_DISABLED").equalsIgnoreCase("true"));  

	@Override
	public Promise<SimpleResult> call(Context ctx) throws Throwable {
		if(!SECURITY_ENABLED) {
			return delegate.call(ctx);
		}
		
		String salt = ctx.request().getHeader("Salt");
		String signature = ctx.request().getHeader("Signature");
		String userId = ctx.request().getHeader("uid");

		if (salt == null || signature == null || userId == null) {
			String missingHeaders = "";
			
			if(salt == null) {
				missingHeaders += "1";
			}
			
			if(signature == null) {
				missingHeaders += "2";
			}

			if(userId == null) {
				missingHeaders += "3";
			}
			
			System.out
					.println("Authentication failure: missing required headers: " + missingHeaders);
			return Promise.pure((SimpleResult)unauthorized(ControllerUtils.messageToJson("Missing required headers")));
		} else {
			ContactDBManager contactManager = new ContactDBManager();
			SecurityDBManager manager = new SecurityDBManager();

			try {

				String email = contactManager.getEmail(userId);

				if (email == null) {
					System.out
							.println("Authentication failure: email not found in the database with required flags: " + userId);
					return Promise.pure((SimpleResult)unauthorized(ControllerUtils.messageToJson("User not authorized")));
				}

				String generatedSignature = generateHmacSHA256Signature(salt
						+ email, SIGNATURE_KEY);

				if (generatedSignature.equals(signature)) {

					if (contactManager.isApprovedContact(userId)) {
						try {
							manager.saveSignature(signature);

							return delegate.call(ctx);
						} catch (Exception e) {
							e.printStackTrace();
							
							System.out.println("Authentication failure: Signature already used: " + signature); 
							return Promise.pure((SimpleResult)unauthorized(ControllerUtils.messageToJson("Signature already used")));
						}
					} else {
						System.out.println("Authentication failure: Contact not approved to use the app: " + userId);
						return Promise.pure((SimpleResult)unauthorized(ControllerUtils.messageToJson("Contact not approved")));
					}
				} else {
					return Promise.pure((SimpleResult)unauthorized(ControllerUtils.messageToJson("Authentication failure: Signatures don't match")));
				}
			} finally {
				manager.close();
				contactManager.close();
			}

		}
		//return delegate.call(ctx);
	}

	public static String generateHmacSHA256Signature(String data, String key)
			throws GeneralSecurityException {
		byte[] hmacData = null;

		try {
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"),
					"HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(secretKey);
			hmacData = mac.doFinal(data.getBytes("UTF-8"));
			return URLEncoder.encode(new BASE64Encoder().encode(hmacData),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new GeneralSecurityException(e);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(generateHmacSHA256Signature("thisisthesalt",
					SIGNATURE_KEY));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
}
