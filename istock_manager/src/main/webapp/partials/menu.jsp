
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-group nav-sidebar" id="accordion-971744">
  <div class="panel panel-default">
    <div class="panel-heading ">
      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-971744" href="#accordion-element-247051">数据管理</a>
    </div>
    <div id="accordion-element-247051" class="panel-collapse collapse in">
      <div class="panel-body">
        <ul class="nav nav-pills nav-stacked">
          <li id="menu_stock_dataday_latest"><a href="<%=request.getContextPath()%>/partials/dataday/latest.jsp">最新日数据</a></li>
          <li id="menu_stock_dataday_history"><a href="<%=request.getContextPath()%>/partials/dataday/history.jsp">历史日数据</a></li>
          <li><a href="#">下载历史</a></li>
          <li><a href="#">Analytics</a></li>
          <li><a href="#">Export</a></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading ">
      <a class="accordion-toggle " data-toggle="collapse" data-parent="#accordion-971744" href="#accordion-element-871626">条件管理</a>
    </div>
    <div id="accordion-element-871626" class="panel-collapse collapse">
      <div class="panel-body">
        <ul class="nav nav-sidebar ">
          <li id="menu_selector_condition"><a href="<%=request.getContextPath()%>/partials/selector/index.jsp">条件集合定义</a></li>
          <li><a href="#">Reports</a></li>
          <li><a href="#">Analytics</a></li>
          <li><a href="#">Export</a></li>
        </ul>
      </div>
    </div>
  </div>

  <div class="panel panel-default">
    <div class="panel-heading ">
      <a class="accordion-toggle active" data-toggle="collapse" data-parent="#accordion-971744" href="#accordion-element-247052">指标设置</a>
    </div>
    <div id="accordion-element-247052" class="panel-collapse collapse">
      <div class="panel-body">
        <ul class="nav nav-pills nav-stacked" role="tablist">
          <li role="presentation" class="active"><a href="#">Home</a></li>
          <li role="presentation"><a href="#">Profile</a></li>
          <li role="presentation"><a href="#">Messages</a></li>
        </ul>
      </div>
    </div>
  </div>
</div>
