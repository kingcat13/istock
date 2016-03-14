<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.HashMap"%>
<!DOCTYPE html>
<html lang="en" ng-app="index">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta charset="UTF-8">
    <link rel="icon" href="<%=request.getContextPath()%>/img/favicon.ico">

    <title>涨高高</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/lib/bootstrap_3_3/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/css/common.css" rel="stylesheet">

    <script src="<%=request.getContextPath()%>/js/global.jsp"></script>
    <script src="<%=request.getContextPath()%>/lib/angular-1.2.27/angular.min.js"></script>
    <script src="<%=request.getContextPath()%>/lib/angular-1.2.27/angular-resource.min.js"></script>

    <script src="<%=request.getContextPath()%>/js/commons/pagination.js"></script>
    <script src="<%=request.getContextPath()%>/js/data/latestService.js"></script>
    <script src="<%=request.getContextPath()%>/js/data/latest.angular.js"></script>


</head>

<body ng-controller="StockDataDayLatestController">

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">涨高高</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row" >

        <div class="col-sm-3 col-md-2 sidebar" title="功能列表">

            <jsp:include page="../menu.jsp"/>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" >

        <h1 class="page-header">最新数据</h1>
            <div class="row">
                <div class="btn-group col-lg-3">

                </div>
                <div class="col-lg-9 form-horizontal form-group">

                    <label for="inputEmail3" class="col-lg-2 control-label">股票代码:</label>
                    <div class="input-group col-lg-10">
                        <input type="text" class="form-control" id="inputEmail3" placeholder="请设置数值">
                        <%--<select id="inputEmail3" class="form-control" ng-model="searchCondition.period" ng-options="m.code as m.name for m in scopes" ng-init="currentCondition.period=1" placeholder="请起个名字吧">--%>

                        <%--</select>--%>
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" ng-click="search()" style="border-top-left-radius: 4px;border-bottom-left-radius: 4px"><span class="glyphicon glyphicon-search"></span></button>
                            <button class="btn btn-default" type="button" ng-click="clearSearchCondition()" ><span class="glyphicon glyphicon-remove"></span></button>
                        </span>

                    </div>

                </div>
            </div>
            <div class="table-responsive">
            <table class="table table-hover table-striped table-condensed ">
                <thead>
                <tr style="color: #808080">
                    <th>证券代码</th>
                    <th>证券名称</th>
                    <th>日期</th>
                    <th>收盘价（元）</th>
                    <th>涨跌幅（%）</th>
                    <th>涨跌值（元）</th>
                    <th style="width:100px"></th>
                </tr>
                </thead>
                <tbody>
                <!--<tr ng-repeat="stock in stocks | offset: paging.pageNo * paging.pageSize | limitTo: paging.pageSize" style="color: #ffff00">-->
                <tr ng-repeat="stock in paging.items" >
                    <td >{{stock.code}}</td>
                    <td >{{stock.name}}</td>
                    <td style="color: #808080" class="up" >{{stock.dataDate}}</td>
                    <td ng-style="{'color': getColor(stock.zhangdiezhi)}">{{stock.shoupanjia/100}}</td>
                    <td ng-style="{'color': getColor(stock.zhangdiezhi)}">{{stock.zhangdiefu/100}}</td>
                    <td ng-style="{'color': getColor(stock.zhangdiezhi)}">{{stock.zhangdiezhi/100}}</td>
                    <td>
                        <a class="btn btn-xs btn-warning" href="<%=request.getContextPath()%>/partials/dataday/history.jsp?code={{stock.code}}">查看详情</a>
                    </td>
                </tr>

                </tbody>
            </table>

            </div>
            <nav>
            <ul class="pager pull-left" >
                <li ng-class="paging.prevPageDisabled()" class="">
                    <a href ng-click="paging.setPage(0)" title="首页"> << </a>
                </li>
                <li ng-class="paging.prevPageDisabled()" >
                    <a href ng-click="paging.prevPage()"> < </a>
                </li>
                <li>
                    <a href="#" >第 {{paging.pageNo+1}} 页，共 {{paging.pageCount()}} 页</a>
                </li>
                <li ng-class="paging.nextPageDisabled()">
                    <a href ng-click="paging.nextPage()" title="下一页"> > </a>
                </li>
                <li ng-class="paging.nextPageDisabled()">
                    <a href ng-click="paging.setPage(paging.pageCount()-1)" title="末页"> >> </a>
                </li>
            </ul>
            <ul class="pager pull-right" >
                <li class="next ">
                    <a href="#">显示 {{paging.pageNo * paging.pageSize + 1}} - {{paging.pageNo * paging.pageSize + paging.items.length}} ，共 {{paging.total}} 条</a>
                </li>
            </ul>
            </nav>
        </div>
    </div>

</div>


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!--<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>-->
<script src="<%=request.getContextPath()%>/lib/jquery-1.11.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/lib/bootstrap_3_3/js/bootstrap.min.js"></script>

<script>

    $(function(){

        $("#accordion-971744").find("div.in").removeClass("in");
        $("#accordion-971744").find("li.active").removeClass("active");
        $("#accordion-971744").find("#accordion-element-247051").addClass("in");
        $("#accordion-971744").find("#menu_stock_dataday_latest").addClass("active");

        $('#tool-example li a').tooltip();
        $('#pop_demo').popover();

        //$(document).ready(initial);
        $("button[data-toggle=popover]").popover();
    })

</script>

</body>
</html>
