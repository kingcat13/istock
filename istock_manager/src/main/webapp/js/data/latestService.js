angular.module('StockDataDayLatestService', ['ngResource'])
.factory('stockDataDayLatestService', function($resource){

        return $resource(app_ctx+'/service/exchangedataday/latest/:id', {id:'@id'}, {
            query: {
                method:'GET',
                isArray:false
            },
            update:{
                method:'PUT'
            }
        });
    });
