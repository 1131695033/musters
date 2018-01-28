<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>户号查询</title>
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
	                url: "${ctx}/ccons/cCons/getCConsData",
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
					    field: 'consNo',
					    title: '户号',
					    sortable: true
					   
					}
				,{
			        field: 'orgnConsNo',
			        title: '旧户号',
			        sortable: true
			    }
				,{
			        field: 'consName',
			        title: '户名',
			        sortable: true
			    }
				,{
			        field: 'consAddr',
			        title: '地址',
			        sortable: true
			    }
			    ,{
			        field: 'contChanlNo',
			        title: '电话',
			        sortable: true
			    }
			    ,{
			        field: 'mrSectIdName',
			        title: '抄表册',
			        sortable: true
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
				  //zTreeObj.cancelSelectedNode();
				  $('#table').bootstrapTable('refresh');
				});
			  
				$('#beginProduceDate').datetimepicker({
					 format: "YYYY-MM-DD"
				});
				$('#endProduceDate').datetimepicker({
					 format: "YYYY-MM-DD"
				});
				$('#DateInput').datetimepicker({
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
		            return row.custIdName
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
			<form:form id="searchForm" modelAttribute="CCons" class="form form-horizontal well clearfix">
				<div class="col-xs-12 col-sm-3 col-md-3">
					<label class="label-item single-overflow pull-left" title="户号：">户号：</label>
					<input type="text" name="consNo" class="form-control inline-block" placeholder="户号">
				</div>
				<div class="col-xs-12 col-sm-3 col-md-3">
					<label class="label-item single-overflow pull-left" title="户名：">户名：</label>
					<input type="text" name="consName" class="form-control inline-block" placeholder="户名">
				</div>
				<div class="col-xs-12 col-sm-3 col-md-3">
					<label class="label-item single-overflow pull-left" title="电话：">电话：</label>
					<input type="text" name="contChanlNo" class="form-control inline-block" placeholder="电话">
				</div>
				<div class="col-xs-12 col-sm-3 col-md-3">
					<label class="label-item single-overflow pull-left" title="地址：">地址：</label>
					<input type="text" name="consAddr" class="form-control inline-block" placeholder="地址">
				</div>
				 <div class="col-xs-12 col-sm-6 col-md-3">
					<div style="margin-top:36px">
					  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
					  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
					 </div>
			    </div>	
					<div style="clear: both;"></div>
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