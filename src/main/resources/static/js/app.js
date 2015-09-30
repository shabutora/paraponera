var app = angular.module("paraponera", ["ngResource"]);

app.controller("host_ctrl", function($scope, $location, $http, $filter, $log){
    $http({ method: 'GET', url: './hosts'})
        .success(function (hosts) {
            $scope.hosts = hosts;
        }).error(function (msg) {
            console.error(msg);
        });
});

app.controller("image_ctrl", function($scope, $location, $http, $filter, $log, $resource){
    $http({ method: 'GET', url: './images/' + $scope.host.name + "/flat", "Content-Type": 'application/json'})
        .success(function (images) {
            //console.log(images);
            $scope.images = images;
        }).error(function (msg) {
            console.error(msg);
        });
    $scope.delete = function (host, image, tag) {
        if (confirm("Delete image?")) {
            $http({method: "DELETE", url: './'})
        }
    }
});

app.controller("tag_ctrl", function ($scope) {
    $scope.tags = $scope.image.tag_list.sort();
});
