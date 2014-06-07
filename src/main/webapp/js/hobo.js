var app = angular.module('hobo', ['ngRoute', 'ui.bootstrap']).config(function ($routeProvider) {
  $routeProvider.when('/', {
    templateUrl: 'root.html'
  }).when('/node', {
    templateUrl: 'node.html'
  }).when('/container', {
    templateUrl: 'container.html'
  }).otherwise({
    redirectTo: '/'
  });
});

app.controller('HoboCtrl', function ($scope, $http, $location, $route, $modal) {
  $scope.getAllNodes = function () {
    $http.get('api/node/all').success(function (data) {
      $scope.nodes = data;
    });
  };

  $scope.deleteContainer = function (id) {
    $http.delete('api/container/' + id).success(function (data) {
      $route.reload();
    });
  };

  $scope.openContainerModal = function () {
    var modalInstance = $modal.open({
      templateUrl: 'container-modal.html',
      controller: 'ContainerModal',
      size: 'sm'
    });
  };

});

app.controller('NodeModal', function ($scope, $modalInstance, $http, $route) {
  $scope.cancel = function () {
    $modalInstance.dismiss('cancel')
  };
});

app.controller('ContainerModal', function ($scope, $modalInstance, $http, $route) {
  $scope.cancel = function () {
    $modalInstance.dismiss('cancel')
  };
  $scope.ok = function (container) {
    $http({
      url: 'api/container',
      method:'PUT',
      data: container
    }).success(function (data) {
      $route.reload();
    });
  };
});


