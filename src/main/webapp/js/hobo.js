var app = angular.module('hobo', ['ngRoute', 'ngAnimate', 'ui.bootstrap']).config(function ($routeProvider) {
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

app.controller('HoboCtrl', function ($scope, $http, $location, $route, $modal, $timeout) {
  $scope.deleting = {};
  $scope.errors = [];

  $scope.removeError=function($index){
    $scope.errors.splice($index,1);
  }

  $scope.getAllNodes = function () {
    $http.get('api/node/all').success(function (data) {
      $scope.nodes = data;
    });
  };

  $scope.getAllContainers = function () {
    $http.get('api/container/all').success(function (data) {
      $scope.containers = data;
    });
  };

  $scope.deleteNode = function (id) {
    $scope.deleting['node'+id] = true;
    $http.delete('api/node/' + id).success(function (data) {
      $scope.deleting['node'+id] = undefined;
      $scope.getAllNodes();
    }).error(function(data){
      $scope.deleting['node'+id] = undefined;
      $scope.errors.push(data);
    });
  };

  $scope.deleteContainer = function (id) {
    $scope.deleting['container'+id] = true;
    $http.delete('api/container/' + id).success(function (data) {
      $scope.deleting['container'+id] = undefined;
      $scope.getAllContainers();
    }).error(function(data){
      $scope.deleting['container'+id] = undefined;
      $scope.errors.push(data);
    });
  };

  $scope.openContainerModal = function () {
    var modalInstance = $modal.open({
      templateUrl: 'container-modal.html',
      controller: 'ContainerModal',
      size: 'sm'
    });
  };

  $scope.openNodeModal = function () {
    var modalInstance = $modal.open({
      templateUrl: 'node-modal.html',
      controller: 'NodeModal',
      size: 'sm'
    });
  };

});

app.controller('NodeModal', function ($scope, $modalInstance, $http, $route) {
  $scope.cancel = function () {
    $modalInstance.dismiss('cancel')
  };
  $scope.submit = function (node) {
    $http({
      url: 'api/node',
      method: 'PUT',
      data: node
    }).success(function (data) {
      $scope.error = undefined;
      $route.reload();
      $scope.cancel();
    }).error(function (data) {
      $scope.error = data;
    });
  };
});

app.controller('ContainerModal', function ($scope, $modalInstance, $http, $route, $timeout) {
  $scope.cancel = function () {
    $modalInstance.dismiss('cancel')
  };
  $scope.submit = function (container) {
    $http({
      url: 'api/container',
      method: 'PUT',
      data: container
    }).success(function (data) {
      $scope.error = undefined;
      $route.reload();
      $scope.cancel();
    }).error(function (data) {
      $scope.error = data;
    });
  };
});


