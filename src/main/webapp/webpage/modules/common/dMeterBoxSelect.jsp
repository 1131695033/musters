<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户选择</title>
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/anihead.jsp"%>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			
			
			//初始化表格
			  $('#table').bootstrapTable({
				  
				  //请求方法
	                method: 'get',
	                dataType: "json",
	                 //是否显示行间隔色
	                striped: true,
	                //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
	                cache: false,    
	                //是否显示分页（*）  
	                pagination: true, 
	                 //排序方式 
	                sortOrder: "asc",    
	                //初始化加载第一页，默认第一页
	                pageNumber:1,   
	                //每页的记录行数（*）   
	                pageSize: 5,  
	                //可供选择的每页的行数（*）    
	                pageList: [5, 10,  'ALL'],
	                //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
	               url: "${ctx}/dmeterbox/dMeterBox/dataZy?consId=" + $("#consId").val(),
	                //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
	                //queryParamsType:'',   
	                ////查询参数,每次调用是会带上这个参数，可自定义                         
	                queryParams : function(params) {
	                	var searchParam = $("#searchForm").serializeJSON();
	                	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
	                	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
	                	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
	                    return searchParam;
	                },
	                //分页方式：client客户端分页，server服务端分页（*）
	                sidePagination: "server",
	                onClickRow: function(row, $el){
	                },
	                columns: [{
	                	<c:if test="${isMultiSelect}">
	                		checkbox: true
	                	</c:if>
	                	<c:if test="${!isMultiSelect}">
	                		radio: true
	                	</c:if>
				    }
					,{
				        field: 'meterBoxNo',
				        title: '表箱编号',
				        sortable: true
				    }
					,{
				        field: 'company.name',
				        title: '管理单位',
				        sortable: true
				       
				    }
					,{
				        field: 'produceFact',
				        title: '生产厂家',
				        sortable: true
				        ,formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('box_fact'))}, value, "-");
				        }
				    }
					,{
				        field: 'meterBoxModel',
				        title: '表箱型号',
				        sortable: true
				        ,formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('box_model'))}, value, "-");
				        }
				    }
					,{
				        field: 'meterNum',
				        title: '水表户型',
				        sortable: true
				        ,formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('box_meter_num'))}, value, "-");
				        }
				    }
					,{
				        field: 'instMethod',
				        title: '水表方向',
				        sortable: true
				        ,formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('meter_inst_method'))}, value, "-");
				        }
				       
				    }			
					,{
				        field: 'mateType',
				        title: '材质',
				        sortable: true
				        ,formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('box_mate'))}, value, "-");
				        }
				    }
					,{
				        field: 'produceDate',
				        title: '生产日期',
				        sortable: true
				       
				    }
					
					,{
				        field: 'tt',
				        
				        title: '存放区/安装位置',
				        sortable: true
				        
				       
				    }
					
					,{
				        field: 'status',
				        title: '状态',
				        sortable: true,
				        formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('box_stat'))}, value, "-");
				        }
				       
				    }
				    ]
				
				});
			
			  
			  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，默认关闭tab
				  $('#table').bootstrapTable("toggleView");
				}
			  
			  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
		                'check-all.bs.table uncheck-all.bs.table', function () {
		            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
		            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
		        });
				    
			  $("#search").click("click", function() {// 绑定查询按扭
				  $('#table').bootstrapTable('refresh');
				});
			  $("#reset").click("click", function() {// 绑定查询按扭
				  $("#searchForm  input").val("");
				  $("#searchForm  select").val("");
				  $('#table').bootstrapTable('refresh');
				});
			  
				$('#beginProduceDate').datetimepicker({
					 format: "YYYY-MM-DD"
				});
				$('#endProduceDate').datetimepicker({
					 format: "YYYY-MM-DD"
				});
		});
		
		  function getIdSelections() {
		        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
		            return row.id
		        });
		    }
		  
		  function getNameSelections() {
		        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
		            return row.meterBoxNo
		        });
		    }
		  
		  function getSelections() {
		        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
		            return row
		        });
		    }
		  function slidex(){
			  $("#collapseTwo").slideToggle();
			  
		  }
		  
	</script>
</head>
<body class="bg-white">
	
	<div class="wrapper wrapper-content">
	<div class="row">
		
<div  class="col-sm-12 col-md-12 animated fadeInRight">
		<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
		<input id="consId" value="${consId }">
			<form:form id="searchForm" modelAttribute="DMeterBox" class="form form-horizontal well clearfix">
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="单位：">单位：</label>
					<sys:treeselect id="company" name="company.id" value="${DMeterBox.company.id}" labelName="company.name" labelValue="${DMeterBox.company.name}"
						title="公司" url="/sys/office/treeData?type=1" allowClear="true" cssClass="form-control required"/>
					
				
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="生产厂家：">生产厂家：</label>
					<form:select path="produceFact"  class="form-control m-b">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('box_fact')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="型号：">型号：</label>
					<form:select path="meterBoxModel"  class="form-control m-b">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('box_model')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-4">
					<label class="label-item single-overflow pull-left" title="水表户型：">水表户型：</label>
					<form:select path="meterNum"  class="form-control m-b">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('box_meter_num')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="水表方向：">水表方向：</label>
				<form:select path="instMethod"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('meter_inst_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="存放位置：">存放位置：</label>
				<sys:treeselect id="wh" name="wh.id" value="${DMeterBox.wh.id}" labelValue="${DMeterBox.wh.name }"
								title="库房" url="/wh/wh/treeData" cssClass=" form-control required" allowClear="true" notAllowSelectParent="true"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="出厂编号：">资产编号：</label>
				<form:input path="meterBoxNo" htmlEscape="false" maxlength="16"  class=" form-control"/>
			</div>				
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="状态：">状态：</label>
				<form:select path="status"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('box_stat')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
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
	<div >
	        	<a class="btn btn-default" name="sd" onclick="slidex()">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
	    <!-- 表格 -->
	    <table id="table"
	           data-toolbar="#toolbar"
	           data-minimum-count-columns="2"
	           data-id-field="id">
	    </table>
	
	</div>
	</div>
	</div>
</body>
</html>