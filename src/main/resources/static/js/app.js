var app = angular.module('app', ['ngRoute','ngResource','ui.bootstrap']);

app.config(function($routeProvider, $httpProvider){
    $routeProvider
        .when('/login',{
            templateUrl: '/views/login.html',
            controller: 'loginController'
        })
        .when('/resource',{
            templateUrl: '/views/resource.html',
            controller: 'resourceController'
        })
        .when('/lead',{
            templateUrl: '/views/teamlead.html',
            controller: 'teamLeadController'
        })
        .when('/manager',{
            templateUrl: '/views/resourceMgr.html',
            controller: 'resourceMgrController'
        })
        .otherwise(
            { redirectTo: '/login'}
        );
    
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})

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

app.factory('viewResolver', function($rootScope, $http, $location, $route) {
	var model = {};
	
	var loadModel = function(callback) {
				
		$http.get('/resource/details/' + $rootScope.loggedInUser)
			.success(function(data) {
				if(data.wrDetails) {
					model.data = data.wrDetails;
					model.route = "/resource"
				}
				else if(data.tlDetails) {
					model.data = data.tlDetails;
					model.route ="/lead";
				}
				else if(data.rmDetails) {
					model.data = data.rmDetails;
					model.route = "/manager";
				}
				
				callback(model);
			})
			.error(function() {})
	}
	
	var data = function() {
		return model.data;
	}
	
	var show = function(user) {
		loadModel(function(model) {
			$location.path(model.route);
		})
	}
	
	var refresh = function(callback) {
		loadModel(function(model) {
			$route.reload();
		})
	}
	
	return {
		show : show,
		refresh : refresh,
		data : data
	}
});