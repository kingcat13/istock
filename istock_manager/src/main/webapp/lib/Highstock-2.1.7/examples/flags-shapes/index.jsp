<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highstock Example</title>

		<script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.2.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
$(function () {
    $.getJSON('http://www.highcharts.com/samples/data/jsonp.php?filename=usdeur.json&callback=?', function (data) {

        var year = new Date(data[data.length-1][0]).getFullYear(); // Get year of last data point

        // Create the chart
        $('#container').highcharts('StockChart', {


            rangeSelector : {
                selected : 1
            },

            title : {
                text : 'USD to EUR exchange rate'
            },

            yAxis : {
                title : {
                    text : 'Exchange rate'
                }
            },

            series : [{
                name : 'USD to EUR',
                data : data,
                id : 'dataseries',
                tooltip : {
                    valueDecimals: 4
                }
            }, {
                type : 'flags',
                data : [{
                    x : Date.UTC(year, 1, 22),
                    title : 'A',
                    text : 'Shape: "squarepin"'
                }, {
                    x : Date.UTC(year, 3, 28),
                    title : 'A',
                    text : 'Shape: "squarepin"'
                }],
                onSeries : 'dataseries',
                shape : 'squarepin',
                width : 16
            }, {
                type : 'flags',
                data : [{
                    x : Date.UTC(year, 2, 1),
                    title : 'B',
                    text : 'Shape: "circlepin"'
                }, {
                    x : Date.UTC(year, 3, 1),
                    title : 'B',
                    text : 'Shape: "circlepin"'
                }],
                shape : 'circlepin',
                width : 16
            }, {
                type : 'flags',
                data : [{
                    x : Date.UTC(year, 2, 10),
                    title : 'C',
                    text : 'Shape: "flag"'
                }, {
                    x : Date.UTC(year, 3, 11),
                    title : 'C',
                    text : 'Shape: "flag"'
                }],
                color : Highcharts.getOptions().colors[0], // same as onSeries
                fillColor : Highcharts.getOptions().colors[0],
                onSeries : 'dataseries',
                width : 16,
                style : {// text style
                    color : 'white'
                },
                states : {
                    hover : {
                        fillColor : '#395C84' // darker
                    }
                }
            }]
        });
    });
});

		</script>
	</head>
	<body>
<script src="../../js/highstock.js"></script>
<script src="../../js/modules/exporting.js"></script>


<div id="container" style="height: 400px"></div>
	</body>
</html>
