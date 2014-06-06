var app = angular.module('hobo', ['ngRoute']).config(function ($routeProvider) {
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

app.controller('NodeCtrl', function ($scope, $http) {
  var getAll = function () {
    $http.get('api/node/all').success(function (data) {
      $scope.nodes = data;
    });
  }
  getAll();
});

app.controller('ContainerCtrl', function ($scope, $http) {

});