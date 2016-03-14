/**
 * Created by gonghongrui on 14/12/2.
 */

var stockColorStyle = {"up": "#F74016", "down": "#64FF2C"};
//$locationProvider.html5Mode(true).hashPrefix('!');
var module_index = angular.module('index', ['ngResource','StockDataDayHistoryService'], angular.noop);
//StockGridCtrl StockSelectorController
function StockDataDayHistoryController($scope, stockDataDayHistoryService) {



    stockDataDayHistoryService.$scope= $scope

    var  store = new Store(10,stockDataDayHistoryService);
    store.searchCondition = {code:page_code};
    store.setPage(0);

    $scope.paging = store;
    // stockSelectorService.query({"start":0,"limit":10},function(result){
    //     $scope.stockSelectorConditions = result.items;
    //});

    $scope.getColor = function(n){
        if (n > 0) {
            //return stockColorStyle.up
            return "#F74016";
        } else {
            return stockColorStyle.down
        }
    }

}



module_index.controller('StockDataDayHistoryController', ['$scope','stockDataDayHistoryService',StockDataDayHistoryController]);
