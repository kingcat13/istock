angular.module('StockDataDayHistoryService', ['ngResource'])
.factory('stockDataDayHistoryService', function($resource){

        return $resource(app_ctx+'/service/exchangedata/day/history/:id', {id:'@id'}, {
            query: {
                method:'GET',
                isArray:false
            },
            update:{
                method:'PUT'
            }
        });
    });
