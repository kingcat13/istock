angular.module('StockSelectorService', ['ngResource'])
.factory('stockSelectorService', function($resource){
        return $resource(app_ctx+'/service/stockselector/stockSelectorCondition/:id', {id:'@id'}, {
            query: {
                method:'GET',
                isArray:false
            },
            update:{
                method:'PUT'
            }
        });
    });
