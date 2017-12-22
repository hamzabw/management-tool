app.controller('resourceMgrController', function($rootScope, $scope, $http, $location, viewResolver) {
    this.init = function() {
    	$scope.content = {
        		domain : '/views/mgr-domain.html',
        		requests : '/views/mgr-requests.html',
        		statistics : '/views/mgr-statistics.html'
        }
    }
    	
    this.initMgrDomain = function() {	
    	var resource = viewResolver.data();
    	
    	$scope.domains = _.sortBy(resource.domainDetails, ['domain.name'])
    	//$scope.groupByDomain = _.groupBy(domains, 'domain.name')
    		
    	$scope.statisticsUrl = "http://cmdb.bfsec.bt.co.uk:8899/BT-Consumer/";
    }
    
    this.initMgrRequests = function() {	
    	var resource = viewResolver.data();
    	
    	$scope.pooledResources = _.sortBy(resource.pooledResources, ['name']);
    	//$scope.pooledResources = _.filter($scope.pooledResources, { status : 'R', status : 'A'})
    	$scope.pooledResources = _.map($scope.pooledResources, (resource) => {
    		switch(resource.status) {
    		case 'A' : resource.statusName = 'AVAILABLE'; resource.available = true; break;
    		case 'R' : resource.statusName = 'REQUESTED'; resource.requested = true; break;
    		case 'S' : resource.statusName = 'ASSIGNED'; resource.assigned = true; break;
    		case 'J' : resource.statusName = 'REJECTED'; resource.rejected = true; break;
    		}
    		
    		return resource;
    	})
    	
    	$scope.currentPage = 1;
    	$scope.itemsPerPage = 5;
    }
    
    this.approve = function() {
    	var selectedForApproval = _.filter($scope.pooledResources, { selected : true })
    	var idsForApproval = _.map(selectedForApproval, 'id')
    	$http.post('/resource/approve/' + $rootScope.loggedInUser, JSON.stringify(idsForApproval))
    	.success(function(){
    		viewResolver.refresh();
    	})
    	.error(function(){
    			
    	});
    }
    
    this.reject = function() {
    	var selectedForRejection = _.filter($scope.pooledResources, { selected : true })
    	var idsForRejection = _.map(selectedForRejection, 'id')
    	$http.post('/resource/reject/' + $rootScope.loggedInUser, JSON.stringify(idsForRejection))
		.success(function(){
			viewResolver.refresh();
		})
		.error(function(){
			
		});
    }
});