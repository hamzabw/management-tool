app.factory('dataShare', function() {
	var data = [];
	
	var put = function(key, value) {
		data.push({key : key , value : value});
	}
	
	var get = function(key) {
		var value;
		data.forEach(function(item) {
			if(item.key === key)
				value = item.value;
		})
		
		return value;
	}
	
	return {
		put : put,
		get : get
	}
});