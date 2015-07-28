package controllers;

import play.mvc.*;
import java.io.File;

import play.Play;
import views.html.*;

public class Application extends Controller {
    public static Result index() {
      return ok(index.render());
    }
}