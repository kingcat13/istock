<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <title>Dashboard Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/lib/bootstrap_3_3/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/css/common.css" rel="stylesheet">

    <script src="<%=request.getContextPath()%>/js/global.jsp"></script>
    <script src="<%=request.getContextPath()%>/lib/angular-1.2.27/angular.min.js"></script>
    <script src="<%=request.getContextPath()%>/lib/angular-1.2.27/angular-resource.min.js"></script>

    <script src="<%=request.getContextPath()%>/js/commons/pagination.js"></script>
    <script src="<%=request.getContextPath()%>/js/selector/selectorService.js"></script>
    <script src="<%=request.getContextPath()%>/js/selector/stockScanTaskService.js"></script>
    <script src="<%=request.getContextPath()%>/js/selector/index.angular.js"></script>


</head>

<body ng-controller="StockSelectorController">

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

        <h1 class="page-header">下面是您的选择条件</h1>
            <div class="row">
                <div class="btn-group col-lg-3">
                    <button type="button" class="btn btn-default"  ng-click="addCondition()">
                        <span class="glyphicon glyphicon-plus"></span> 新增
                    </button>
                    <button type="button" class="btn btn-default" >
                        <span class="glyphicon glyphicon-remove"></span> 删除
                    </button>
                </div>
                <div class="col-lg-9 form-horizontal form-group">

                    <label for="inputEmail3" class="col-lg-2 control-label">周期:</label>
                    <div class="input-group col-lg-10">
                        <!--<input type="number" class="form-control" id="inputEmail3" placeholder="请设置数值">-->
                        <select id="inputEmail3" class="form-control" ng-model="searchCondition.period" ng-options="m.code as m.name for m in scopes" ng-init="currentCondition.period=1" placeholder="请起个名字吧">

                        </select>
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
                <tr>
                    <th class="col-lg-2">#</th>
                    <th class="col-lg-2">周期</th>
                    <th class="col-lg-2">名称</th>
                    <th class="col-lg-6">说明</th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="condition in paging.items">
                    <td class="text-left">
                        <!--<div class="btn-group">-->
                        <button type="button" class="btn btn-xs btn-default" ng-click="editCondition($index)" >
                            <span class="glyphicon glyphicon-pencil" ></span>
                        </button>
                        <button type="button" class="btn btn-xs btn-default" ng-click="removeCondition($index)">
                            <span class="glyphicon glyphicon-minus"></span>
                        </button>
                        <button type="button" id="" class="btn btn-xs btn-warning" ng-click="runSelector(condition.id)"
                                data-toggle="popover" data-content="And here's some amazing content. It's very engaging. Right?" >
                            执行
                        </button>


                        <!--</div>-->

                    </td>
                    <td>{{condition.period}}</td>
                    <td>{{condition.name}}</td>
                    <td>
                        {{condition.description}}
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

<!--详细表单-->
<div class="modal fade in text-center " id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" >
    <div class="modal-dialog " style="display: inline-block; width: auto;">
        <div class="modal-content">
                <!--<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>-->

                <!--<h4 class="modal-title" id="myModalLabel">Modal title</h4>-->

            <div class="container" >
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>

                <h1 class="page-header text-left">筛选条件</h1>
                <form role="form" name="detailForm" >
                    <fieldset >
                        <div class="col-lg-6">
                            <div class="form-group" ng-class="{ 'has-error' : detailForm.condition_period.$invalid }" style="text-align:left">
                                <label for="condition.period" class="control-label has-success">时间范围:</label>
                                <select id="condition.period" name="condition_period" class="form-control" ng-change="changePeriod()" ng-options="m.code as m.name for m in scopes" ng-model="currentCondition.period" required ng-init="currentCondition.period=1" placeholder="请起个名字吧" >

                                </select>
                                <p class="help-block">请选择时间范围.</p>
                            </div>
                        </div>
                        <div class="col-lg-6" >
                            <div class="form-group has-feedback" ng-class="{ 'has-error' : detailForm.condition_name.$invalid && !detailForm.condition_name.$pristine }"  style="text-align:left">
                                <label for="condition.name" class="control-label">条件名称:</label>
                                <input id="condition.name" name="condition_name" ng-model="currentCondition.name" ng-minlength="2" class="form-control has-success" required placeholder="请起个名字吧">
                                <p class="help-block">条件名称至少需要两位.</p>
                                <span ng-show="detailForm.condition_name.$invalid" class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="form-group" style="text-align:left">
                                <label class="control-label" for="condition.description" >条件描述:</label>
                                <textarea  class="form-control" ng-model="currentCondition.description" id="condition.description"></textarea>
                            </div>
                        </div>
                   </fieldset>
                    <div class="sub-header row col-lg-12 text-left">
                        <div class="row">
                            <label class="control-label col-sm-2" style="font-size: 20px">规则</label>
                            <div class="form-horizontal col-sm-10" >

                                <div class="form-group ">
                                    <div class=" pull-right  btn-group-xs">
                                        <button type="button" class="btn btn-xs btn-primary ">添加筛选趋势</button>
                                        <button type="button" class="btn btn-xs btn-primary " ng-click="addRule()">添加筛选指标</button>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="table-responsive row col-lg-12 form-group">
                        <table class="table table-hover ">
                            <thead>
                            <tr style="color: #808080">
                                <th class="text-left">#</th>
                                <th>指标</th>
                                <th>时间范围</th>
                                <th>计算方式</th>
                                <th>比较类型</th>
                                <th>数量</th>
                                <th style="width:100px"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <!--<tr ng-repeat="stock in stocks | offset: paging.pageNo * paging.pageSize | limitTo: paging.pageSize" style="color: #ffff00">-->
                            <tr ng-repeat="rule in currentCondition.stockSelectorRules">
                                <td class="text-left">
                                    <div class="checkbox">

                                    </div>
                                </td>
                                <td >
                                    <select ng-model="rule.specification" ng-options="m.code as m.name for m in specifications" class="form-control">
                                    </select>
                                </td>
                                <td>
                                    <select ng-model="rule.period" required class="form-control" ng-options="m.code as m.name for m in scopes">
                                    </select>
                                </td>
                                <td >
                                    <select ng-model="rule.calType" ng-options="m.code as m.name for m in calTypes" class="form-control">
                                    </select>
                                </td>
                                <td >
                                    <select ng-model="rule.compareType" ng-options="m.code as m.name for m in compareTypes" class="form-control">
                                    </select>
                                </td>
                                <td >
                                    <div ng-class="{ 'has-error' : true }">
                                    <input type="number"  ng-model="rule.amount" class="form-control" required value="{{rule.amount}}"  placeholder="请设置数值123">
                                    </div>

                                </td>
                                <td>
                                    <button type="button" class="btn btn-xs btn-warning" ng-click="removeRule($index)">删除</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                    </div>
                </form>
                <div class="form-horizontal row col-lg-12 form-group" role="form">
                    <button type="button" class="btn btn-xs btn-primary form-control" ng-click="saveStockSelectorCondition(detailForm.$valid)" ng-disabled="!detailForm.$valid">保存指标</button>
                    <div class="col-lg-12"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--进度框-->
<div class="modal fade" id="loadingModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">任务处理中</h4>
            </div>
            <div class="modal-body">
                <p>请等待...&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <!--<button type="button" class="btn btn-primary">Save changes</button>-->
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!--<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>-->
<script src="<%=request.getContextPath()%>/lib/jquery-1.11.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/lib/bootstrap_3_3/js/bootstrap.min.js"></script>

<script>

    $(function(){

        $('#tool-example li a').tooltip();
        $('#pop_demo').popover();

        //$(document).ready(initial);
        $("button[data-toggle=popover]").popover();


        $("#accordion-971744").find("div.in").removeClass("in");
        $("#accordion-971744").find("li.active").removeClass("active");
        $("#accordion-971744").find("#accordion-element-871626").addClass("in");
        $("#accordion-971744").find("#menu_selector_condition").addClass("active");
    })
</script>

</body>
</html>
