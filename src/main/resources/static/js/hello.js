angular.module('hello', [ 'ngRoute' ])
  .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).when('/home', {
		templateUrl : 'home.html',
		controller : 'home'
	}).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  })
  .controller('home', function($scope, $http) {
//    $http.get('/resource/').success(function(data) {
//      $scope.greeting = data;
//    })
	  $.ajaxPrefilter( function (options) {
		  if (options.crossDomain && jQuery.support.cors) {
			    var http = (window.location.protocol === 'http:' ? 'http:' : 'https:');
			    options.url = http + '//cors-anywhere.herokuapp.com/' + options.url;
			    //options.url = "http://cors.corsproxy.io/url=" + options.url;
			  }
			});

			$.get(
			    'http://cmdb.bfsec.bt.co.uk:8899/BT-Consumer/',
			    function (response) {
			        console.log("> ", response);
			        $("#dashboardContent").html(response);
			});
  })
  .controller('navigation', function($rootScope, $scope, $http, $location) {

	  var authenticate = function(credentials, callback) {

	    var headers = credentials ? {authorization : "Basic "
	        + btoa(credentials.username + ":" + credentials.password)
	    } : {};

	    $http.get('/user', {headers : headers, withCredentials: true}).success(function(data) {
	      if (data.name) {
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
	  $scope.login = function() {
	      authenticate($scope.credentials, function() {
	        if ($rootScope.authenticated) {
	          $location.path("/home");
	          $scope.error = false;
	        } else {
	          $location.path("/login");
	          $scope.error = true;
	        }
	      });
	  };
	  $scope.logout = function() {
		  $http.post('logout', {}).success(function() {
		    $rootScope.authenticated = false;
		    $location.path("/");
		  }).error(function(data) {
		    $rootScope.authenticated = false;
		  });
		};
});