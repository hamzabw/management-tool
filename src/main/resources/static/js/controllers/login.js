app.controller('loginController', function($rootScope, $scope, $http, $location, viewResolver) {

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
	
	authenticate();
	$scope.credentials = {};
	
	this.login = function() {
		authenticate($scope.credentials, function() {
			if ($rootScope.authenticated) {
				markAttendance();
				$scope.error = false;
			} else {
				$location.path("/login");
				$scope.error = true;
				$scope.errorMessage = "Invalid User name or password"
			}
		});
	};
	
	this.logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			$rootScope.authenticated = false;
		});
	};

	this.reset = function() {
		$scope.credentials.username = '';
		$scope.credentials.password = '';
		$scope.error = false;
	}
	
	var markAttendance = function() {
		$http.post('/resource/attendance/' + $rootScope.loggedInUser)
			.success(function() {
				viewResolver.show();
			})
			.error(function() {
				$scope.error = true;
				$scope.errorMessage = "Unable to mark attendance at this time"
			})
	}
});