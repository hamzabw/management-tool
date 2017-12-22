app.service('login', function($rootScope,$http) {
	
	var authenticate = function(credentials, callback) {

		var headers = credentials ? {
			authorization : "Basic "
				+ btoa(credentials.username + ":" + credentials.password)
		} : {};

		$http.get('/user', {
			headers : headers,
			withCredentials : true
		}).success(function(data) {
			if (data.name) {
				$rootScope.loggedInUser = data.name;
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback();
		}).error(function() {
			$rootScope.authenticated = false;
			callback && callback();
		});

	}
	
	var logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			$rootScope.authenticated = false;
		});
	}
	
	return {
		authenticate : authenticate,
		logout : logout
	}
})