<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highstock Example</title>

        <script src="<%=request.getContextPath()%>/lib/jquery-1.11.1/jquery.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
$(function () {

    $.getJSON('http://www.highcharts.com/samples/data/jsonp.php?filename=aapl-c.json&callback=?', function (data) {
        // Create the chart
        $('#container').highcharts('StockChart', {


            rangeSelector : {
                selected : 1
            },

            title : {
                text : 'AAPL Stock Price'
            },

            series : [{
                name : 'AAPL',
                data : data,
                tooltip: {
                    valueDecimals: 2
                }
            }]
        });
    });

});

		</script>
	</head>
	<body>

<script src="<%=request.getContextPath()%>/lib/Highstock-2.1.7/js/highstock.js"></script>
<script src="<%=request.getContextPath()%>/lib/Highstock-2.1.7/js/modules/exporting.js"></script>

<div id="container" style="height: 400px; min-width: 310px"></div>
	</body>
</html>
