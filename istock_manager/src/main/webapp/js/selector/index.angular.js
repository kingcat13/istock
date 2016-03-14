/**
 * Created by gonghongrui on 14/12/2.
 */

var module_index = angular.module('index', ['ngResource','StockSelectorService','StockScanTaskService'], angular.noop);
//StockGridCtrl StockSelectorController
function StockSelectorController($scope, stockSelectorService, stockScanTaskService) {

    $scope.scopes = [
        {"code":1,"name":"1天"}
        ,{"code":2,"name":"2天"}
        ,{"code":3,"name":"3天"}
        ,{"code":5,"name":"5天"}
        ,{"code":10,"name":"10天"}
        ,{"code":30,"name":"30天"}
    ];
    $scope.specifications = [
        {"code":"zhangdiezhi","name":"涨跌值(分)"}
        ,{"code":"zhangdiefu","name":"涨跌幅(%)"}
        ,{"code":"liangbi","name":"量比(%)"}
    ];

    $scope.compareTypes = [{
        "code":"equal","name":"等于"}
        ,{"code":"more","name":"大于"}
        ,{"code":"less","name":"小于"}
        ,{"code":"moreEqual","name":"大于或等于"}
        ,{"code":"lessEqual","name":"小于或等于"}
    ];


    $scope.calTypes = [
        {"code":"average","name":"平均"}
        ,{"code":"total","name":"总共"}
        ,{"code":"single","name":"每天"}
    ];
    stockSelectorService.$scope= $scope

    var  store = new Store(10,stockSelectorService);
    store.setPage(0);

    $scope.paging = store;
    // stockSelectorService.query({"start":0,"limit":10},function(result){
    //     $scope.stockSelectorConditions = result.items;
    //});


    //$scope.stockselector ={"condition":{"period":1,"name":"测试","rules":[{"specification":"liangbi","calType":"total","compareType":"moreEqual"}]}};

    $scope.stockselector = {"condition":{"stockSelectorRules":[]}};

    $scope.searchCondition = {};
    $scope.search = function(){

        JSON.parse(JSON.stringify($scope.searchCondition))
        store.searchCondition = JSON.parse(JSON.stringify($scope.searchCondition));
        //alert("store.searchCondition.period:"+store.searchCondition.period)
        store.setPage(0);

    }

    $scope.changePeriod = function(){

        if($scope.currentCondition.stockSelectorRules){

            for(var i in $scope.currentCondition.stockSelectorRules){

                $scope.currentCondition.stockSelectorRules[i].period = $scope.currentCondition.period
                //$scope.currentCondition.stockSelectorRules[i].period = parseInt($scope.currentCondition.period);
            }
        }
    }

    $scope.clearSearchCondition= function(){

        $scope.searchCondition = {};
        store.searchCondition = null;
        //alert("store.searchCondition.period:"+store.searchCondition.period)

        closeLoadingMask()
    }
    $scope.addRule = function(){
        if(!$scope.currentCondition.stockSelectorRules){
            $scope.currentCondition.stockSelectorRules = [];
        }
        $scope.currentCondition.stockSelectorRules.push({"calType":"total","specification":"zhangdiefu","compareType":"more","amount":10,"period":$scope.currentCondition.period});
        //$scope.currentCondition.stockSelectorRules.push({});
    }

    $scope.removeRule = function(index){
        $scope.currentCondition.stockSelectorRules.splice(index,1)
    }

    $scope.addCondition = function(index){
        $scope.currentCondition = {"stockSelectorRules":[]}
        $('#myModal').modal('show')

    }
    $scope.editCondition = function(index){

        $scope.currentCondition = store.items[index]
        $('#myModal').modal('show')

    }
    $scope.removeCondition = function(index){
        openLoadingMask()
        stockSelectorService.remove(store.items[index],function(result){
            store.setPage(store.pageNo);
            closeLoadingMask()
        });

    }

    $scope.saveStockSelectorCondition = function(isValid){

        if(isValid){
            openLoadingMask()
            if($scope.currentCondition.id){
                for(var i in $scope.currentCondition.stockSelectorRules){
                    alert($scope.currentCondition.stockSelectorRules[i].period)

                }
                stockSelectorService.update($scope.currentCondition,function(result){
                    store.setPage(store.pageNo);
                    closeLoadingMask()
                });
            }
            else{
                stockSelectorService.save($scope.currentCondition,function(result){
                    store.setPage(store.pageNo);
                    closeLoadingMask()
                });
            }
            //store.setPage(store.pageNo);
            $('#myModal').modal('hide')
            $scope.currentCondition = {"stockSelectorRules":[]};
        }
    }

    $scope.runSelector = function(id){

        stockScanTaskService.save({"conditionId":id})

    }
    $('#myModal').modal('show')
    $('#myModal').modal('hide')

    function openLoadingMask(){
        $('#loadingModal').modal('show')
    }
    function closeLoadingMask(){
        $('#loadingModal').modal('hide')
    }
    //$('#loadingModal').modal('show')
    $("button[data-toggle=popover]").popover();

}



module_index.controller('StockSelectorController', ['$scope','stockSelectorService','stockScanTaskService',StockSelectorController]);
