var util = util || {

	getLocalStorage: function(key){
		
		if (typeof(localStorage) != 'undefined' ) {
			
			var localData = localStorage.getItem(key);
			if(localData == null){
				return null;
			}else{
				var data = null;
				try{
				  data = JSON.parse(localData);
				  //data = localData;
				}
				catch(err){ 
				  data = null;
				  console.log('Error while parsing data for ' + key+'. Error: '+err);
				}
				return data;
			}
		}
	},

	setLocalStorage: function(key,data){
		var jsonData = JSON.stringify(data);
		if (typeof(localStorage) != 'undefined' ) {
			localStorage.setItem(key,jsonData);
			console.log('set LocalStorage data for ' + key);
		}
	}, 

	clearLocalStorage: function(key){
		if (typeof(localStorage) != 'undefined' ) {
			localStorage.setItem(key,null) ;
			console.log('cleared LocalStorage data for ' + key);
		}
	},

}
