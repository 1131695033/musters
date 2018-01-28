<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>收费明细管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="payChargeDetailsList.js" %>
	<style type="text/css">
		.form-horizontal{
			margin: 0;
		}
		.input-group-btn{
			height: 33.5px;
		}
		.btn.btn-primary  {
			height: 33.5px;
		}
		#orgNoName{
			height: 33.5px;
		}
		#cashierName{
			height: 33.5px;
		}
		#cashierId{
			height: 33.5px;
		}
		.tableTitle{
			margin-bottom: 0;
		}
	</style>
	<script type="text/javascript">
		 $(function(){
			  $('#rcvedDate').datetimepicker({
				 format: "YYYY-MM-DD"
		     });
		      $('#endDate').datetimepicker({
				 format: "YYYY-MM-DD"
		     });
		 });
		
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">收费明细列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="payChargeDetails" class="form form-horizontal well clearfix">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="管理单位：">管理单位：</label>
				<sys:treeselect id="orgNo" name="orgNo" labelName="orgNoName"  title="公司" url="/sys/office/treeData?type=1" allowClear="true" cssClass="form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
			 	<label class="label-item single-overflow" title="收费日期：">起始收费日期：</label>
			 	<div class="input-group date" >
	                  <form:input path="rcvedDate" value="${currentTime}" htmlEscape="false" style="height: 33.5px;" maxlength="8"  class=" form-control"/>
	                   <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		               </span>
	             </div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow" title="收费日期：">结束收费日期：</label>
			 	<div class="input-group date"  >
	                  <form:input path="endDate" value="${currentTime}" htmlEscape="false" style="height:33.5px;" maxlength="8"  class=" form-control"/>
	                   <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		               </span>
	             </div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="收费人员：">收费人员：</label>
				<sys:userselect id="cashier" name="cashier" value="${fns:getUser()}" labelName="cashierName" labelValue="${usetName}" cssClass="form-control"/>	
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="收费类型：">收费类型：</label>
				<form:select path="typeCode"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('fee_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="缴费方式：">缴费方式：</label>
				<form:select path="chargeMode"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="结算方式：">结算方式：</label>
				<form:select path="settleMode"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('settle_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<%--  <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="用户编号：">用户编号：</label>
				<sys:consselect id="consNo" name="consNo" labelName="consNoName" cssClass="form-control"/>	
			</div> --%>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:36px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<%-- <shiro:hasPermission name="paychargedetails:payChargeDetails:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="paychargedetails:payChargeDetails:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="paychargedetails:payChargeDetails:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="paychargedetails:payChargeDetails:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/paychargedetails/payChargeDetails/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
						</form>
				</div>
			</shiro:hasPermission> --%>
				 <button class="btn btn-success" onclick="getSelectionsData();" >
	            	<i class="glyphicon glyphicon-print"></i> 打印收费日报
	        	</button>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<div class="tableTitle">
		<h4>费用合计信息</h4>
	</div>
	<table id="payChargeDetailsTable1"   data-toolbar="#toolbar"></table>
	<div class="tableTitle">
		<h4>费用明细信息</h4>
	</div>
	<table id="payChargeDetailsTable"  ></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="paychargedetails:payChargeDetails:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="paychargedetails:payChargeDetails:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>