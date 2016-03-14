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



</head>

<body>

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

        <h1 class="page-header">历史数据</h1>
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
            <div class="row">
                <div id="container" style="min-width:400px;height:400px"></div>
            </div>
        </div>
    </div>
</div>


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!--<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>-->
<script src="<%=request.getContextPath()%>/lib/jquery-1.11.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/lib/bootstrap_3_3/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/lib/Highstock-2.1.7/js/highstock.js"></script>
<script src="<%=request.getContextPath()%>/lib/Highstock-2.1.7/js/modules/exporting.js"></script>

<script>
    var page_code = "<%=request.getParameter("code")%>";

    $(function(){

        $("#accordion-971744").find("div.in").removeClass("in");
        $("#accordion-971744").find("li.active").removeClass("active");
        $("#accordion-971744").find("#accordion-element-247051").addClass("in");
        $("#accordion-971744").find("#menu_stock_dataday_history").addClass("active");

        $('#tool-example li a').tooltip();
        $('#pop_demo').popover();

        //$(document).ready(initial);
        $("button[data-toggle=popover]").popover();

//        $.getJSON('http://www.highcharts.com/samples/data/jsonp.php?filename=aapl-c.json&callback=?', function (data) {
        $.getJSON(app_ctx+'/service/exchangedata/day/history/candlestickMapData/'+page_code, function (data) {

            // Create the chart
            $('#container').highcharts('StockChart', {

                rangeSelector : {
                    enabled:true,
                    inputDateFormat:'%Y-%m-%d',
                    selected : 1
                },
                exporting:{enabled:false},
                title : {
                    text : '<%=request.getParameter("code")%> Stock Price'
                },
                tooltip:{
                    formatter:function() {

                        var s = '<b>' + Highcharts.dateFormat('%Y-%m-%d', this.x) + '</b>';

                        $.each(this.points, function () {
                            s += '<br/>' + this.y;
                        });

                        return s;
                    }
                },
                yAxis:{
                    plotLines : [{
                        value : -1,
                        color : 'green',
                        dashStyle : 'shortdash',
                        width : 2,
                        label : {
                            text : 'Last quarter minimum'
                        }
                    }, {
                        value : 20,
                        color : 'red',
                        dashStyle : 'shortdash',
                        width : 2,
                        label : {
                            text : 'Last quarter maximum'
                        }
                    }]
                },
                xAxis:{
                    plotLines : [{
                        value : 1431014400000,
                        color : 'green',
                        dashStyle : 'shortdash',
                        width : 2,
                        label : {
                            text : '下降趋势'
                        }
                    }, {
                        value : 1431446400000,
                        color : 'black',
                        dashStyle : 'shortdash',
                        width : 2,
                        label : {
                            text : '自然回撤'
                        }
                    }],
//                    labels:{
//                        formatter:function() {
//
//                            var newTime = new Date(this.value);
//
//                            return newTime.getFullYear()+"-"+(newTime.getMonth()+1)+"-"+newTime.getDate();
//                        }
//                    },
                    dateTimeLabelFormats: {
                        second: '%Y-%m-%d<br/>%H:%M:%S',
                        minute: '%Y-%m-%d<br/>%H:%M',
                        hour: '%Y-%m-%d<br/>%H:%M',
                        day: '%Y<br/>%m-%d',
                        week: '%Y<br/>%m-%d',
                        month: '%Y-%m',
                        year: '%Y'
                    }

                },
                series : [{
                    name : 'AAPL',
                    id:'dataseries',
                    data : data.items,
                    tooltip: {
                        valueDecimals: 2
                    }
                }, {
                    type : 'flags',
                    data : [{
                        x : Date.UTC(2011, 1, 22),
                        title : 'A',
                        text : 'Shape: "squarepin"'
                    }, {
                        x : Date.UTC(2011, 3, 28),
                        title : 'A',
                        text : 'Shape: "squarepin"'
                    }],
                    color : Highcharts.getOptions().colors[0],
                    onSeries : 'dataseries',
                    shape : 'squarepin',
                    width : 16
                }, {
                    type : 'flags',
                    data : [{
                        x : Date.UTC(2011, 2, 1),
                        title : 'B',
                        text : 'Shape: "circlepin"'
                    }, {
                        x : Date.UTC(2011, 3, 1),
                        title : 'B',
                        text : 'Shape: "circlepin"'
                    }],
                    shape : 'circlepin',
                    width : 16
                }]
            });
        });
    })

</script>
</body>
</html>
