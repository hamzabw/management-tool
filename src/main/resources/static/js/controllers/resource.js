app.controller('resourceController', function($rootScope, $scope, $http, dataShare) {
    this.init = function() {
    	$scope.resource = dataShare.get('resource')
    	
    	switch($scope.resource.status) {
    		case 'A': $scope.resource.status = "APPROVED"; break;
    		case 'R': $scope.resource.status = "REQUESTED"; break;
    		case 'S': $scope.resource.status = "ASSIGNED"; break;
    		case 'J': $scope.resource.status = "REJECTED"; break;
    	}
    }
});