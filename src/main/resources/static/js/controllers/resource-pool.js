app.controller('resourcePoolController', function($rootScope, $scope, $http, $route) {
	this.init = function() {
		$scope.content = {
			resources : '/views/resource-pool.html'
		}
		
		$http.get('/resource/pool/' + $rootScope.loggedInUser).then(
			function(response) {
				$scope.resourcepool = response.data;
		});
		
		$scope.totalItems = $scope.resourcepool ? $scope.resourcepool.length : 0;
		$scope.currentPage = 1;
		$scope.itemsPerPage = 5;
		$scope.maxSize = 5;

		$scope.setPage = function(pageNo) {
			$scope.currentPage = pageNo;
		};

		$scope.pageChanged = function() {
			console.log('Page changed to: ' + $scope.currentPage);
		};

		$scope.setItemsPerPage = function(num) {
			$scope.itemsPerPage = num;
			$scope.currentPage = 1; // reset to first page
		}
	}
	
	this.requestResource = function() {
		var selectedFToRequest = _.filter($scope.resourcepool, { selected : true })
    	var idsToRequest = _.map(selectedFToRequest, 'id')
		$http.post('/resource/request/' + $rootScope.loggedInUser, JSON.stringify(idsToRequest))
		.success(function() {
			$route.reload();
		})
		.error(function() {

		})
	};

});