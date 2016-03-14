/**
 * Created by gonghongrui on 14/12/2.
 */

//var stockColorStyle = {"up": "#F74016", "down": "#64FF2C"};
var stockColorStyle = {"up": "black", "down": "black"};

var module_index = angular.module('index', ['ngResource','StockDataDayLatestService'], angular.noop);
//StockGridCtrl StockSelectorController
function StockDataDayLatestController($scope, stockDataDayLatestService) {



    stockDataDayLatestService.$scope= $scope

    var  store = new Store(10,stockDataDayLatestService);
    store.setPage(0);

    $scope.paging = store;
    // stockSelectorService.query({"start":0,"limit":10},function(result){
    //     $scope.stockSelectorConditions = result.items;
    //});

    $scope.getColor = function(n){
        if (n > 0) {
            return stockColorStyle.up
        } else {
            return stockColorStyle.down
        }
    }



}



module_index.controller('StockDataDayLatestController', ['$scope','stockDataDayLatestService',StockDataDayLatestController]);
