<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>服务分类管理</title>
	<meta name="decorator" content="ani"/>
	<%@include file="classifyList.js" %>
</head>
<body>

	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">服务分类列表  </h3>
			</div>
			
			<div class="panel-body">
			<sys:message content="${message}"/>
	
			<!-- 工具栏 -->
			<div class="row">
			<div class="col-sm-12">
				<div class="pull-left treetable-bar">
					<shiro:hasPermission name="classify:classify:add">
						<a id="add" class="btn btn-primary" onclick="jp.openDialog('新建服务分类', '${ctx}/classify/classify/form','800px', '500px', $classifyTreeTable)"><i class="glyphicon glyphicon-plus"></i> 新建</a><!-- 增加按钮 -->
					</shiro:hasPermission>
			       <button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				
				</div>
			</div>
			</div>
			<table id="classifyTreeTable" class="table table-hover">
				<thead>
					<tr>
						<th>名称</th>
						<th>类型</th>
						<th>排序</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="classifyTreeTableList"></tbody>
			</table>
			<br/>
			</div>
			</div>
	</div>
</body>
</html>