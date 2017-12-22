app.controller('teamLeadController', function($rootScope, $scope, $http, $location, viewResolver) {
	this.init = function() {
		$scope.content = {
			resources : '/views/lead-resources.html',
			resourcepool : '/views/resource-pool.html',
			statistics : '/views/lead-statistics.html'
		}
			
		var resource = viewResolver.data();
			
		$scope.fixedResources = resource.fixedResources;
		$scope.pooledResources = resource.pooledResources;
		
		if($scope.fixedResources) {
			$scope.frtp = {
					totalItems : $scope.fixedResources ? $scope.fixedResources.length : 0,
					currentPage : 1,
					itemsPerPage : 2,
					maxSize : 5
			}
		}
		
		if($scope.pooledResources) {
			$scope.prtp = {
					totalItems : $scope.pooledResources ? $scope.pooledResources.length : 0,
					currentPage : 1,
					itemsPerPage : 5,
					maxSize : 5
			}
		}
	}

	this.releaseResource = function() {
		var selectedForRelease = _.filter($scope.fixedResources, { selected : true })
    	var idsForRelease = _.map(selectedForRelease, 'id')
		$http.post('/resource/release/' + $rootScope.loggedInUser, JSON.stringify(idsForRelease))
		.success( function() {
			viewResolver.refresh();
		})
		.error(function() {})
	}
	
});