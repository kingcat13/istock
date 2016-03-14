angular.module('StockScanTaskService', ['ngResource'])
.factory('stockScanTaskService', function($resource){
        return $resource(app_ctx+'/service/stockscan/stockScanTask/:id', {id:'@id'}, {
            query: {
                method:'GET',
                isArray:false
            },
            update:{
                method:'PUT'
            }
        });
    });
