/**
 * Created by gonghongrui on 14/12/4.
 */
function Store(http,config){
    this.name = 'Store';
    this.http = http;
    this.items = [];
    this.total = 0;
    this.config = config;
    this.extraParams = {"a":"aa"};
}

Store.prototype.load = function(params){
    me = this;

    for(var a in me.extraParams ){
        params[a] = me.extraParams[a];
    }

    me.http({
        method: me.config.method,
        url: me.config.url,
        params:params
    })
    .success(function (response, status, headers, config) {
        me.total = response.total;
        me.items = response.items;
    })
}