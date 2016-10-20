/**
 * Created by gonghongrui on 14/12/3.
 */
function Store(pageSize,service){
    this.name = 'Paging';
    this.pageNo = 0;
    this.pageSize = pageSize;
    this.service = service;
    this.total = 0;
    this.items = {};
    this.searchCondition ;

}

Store.prototype.pageCount = function(){
    return Math.ceil(this.total / this.pageSize) ;
}

Store.prototype.pageItems=function(){
    var pageItems = []
    for(var i =0;i<this.pageCount();i++){
        pageItems.push(i);
    }
    return pageItems;
}


Store.prototype.setPage = function(pageNo) {
    var me = this;
    ;
    var queryCondition = {
        "start":pageNo * this.pageSize,
        "limit":this.pageSize
    }

    if(me.searchCondition){
        queryCondition.condition = me.searchCondition;
        //alert("queryCondition.period:"+queryCondition.condition.period);
    }


    if ((pageNo < this.pageCount() && pageNo >=0)||pageNo == 0) {

        this.service.query(queryCondition, function(result){
            me.total = result.total;
            me.items = result.items;
            me.pageNo= pageNo;
        });
    }
}

Store.prototype.nextPage = function () {
    if (this.pageNo < this.pageCount()-1) {
        this.setPage(this.pageNo+1);
    }
}

Store.prototype.prevPage = function () {
    if (this.pageNo > 0) {
        this.setPage(this.pageNo-1);
    }
}
Store.prototype.prevPageDisabled = function () {
    return this.pageNo === 0 ? "disabled" : "";
    ;
}

Store.prototype.nextPageDisabled = function () {
    return this.pageNo === this.pageCount()-1 ? "disabled" : "";
}
//Store.prototype.load = function (pageNo) {
//    var me = this;
//    //this.service.query({
//    //    "start":0,
//    //    "limit":this.pageSize
//    //},function(result){
//    //    me.total = result.total;
//    //    me.items = result.items;
//    //    me.pageNo= 0;
//    //
//    //});
//    this.service.query({
//        "start":pageNo * this.pageSize,
//        "limit":this.pageSize
//    },function(result){
//        me.total = result.total;
//        me.items = result.items;
//        me.pageNo= pageNo;
//    });
//}






