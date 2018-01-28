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
	                url: "${ctx}/sect/sect/data",
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
				        field: 'mrSectNo',
				        title: '抄表册编号',
				        sortable: true
				        ,formatter:function(value, row , index){
				        	return '<a  href="#" onclick="jp.openDialogView(\'查看抄表册维护\', \'${ctx}/sect/sect/form?id='+row.id+'\',\'800px\', \'500px\')">'+value+'</a>'
				         }
				       
				    }
					,{
				        field: 'mrSectName',
				        title: '抄表册名称',
				        sortable: true
				       
				    }
					,{
				        field: 'mrType',
				        title: '抄表册类型',
				        sortable: true,
				        formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('mr_type'))}, value, "-");
				        }
				       
				    }
					,{
				        field: 'company.name',
				        title: '管理单位',
				        sortable: true
				       
				    }
					,{
				        field: 'mrMode',
				        title: '抄表方式',
				        sortable: true,
				        formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('mr_mode'))}, value, "-");
				        }
				       
				    }
					,{
				        field: 'tuser.name',
				        title: '抄表人员',
				        sortable: true
				       
				    }
					,{
				        field: 'periodCode',
				        title: '抄表周期',
				        sortable: true,
				        formatter:function(value, row , index){
				        	return jp.getDictLabel(${fns:toJson(fns:getDictList('mr_period'))}, value, "-");
				        }
				       
				    },{
				        field: 'remarks',
				        title: '备注',
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
				 // zTreeObj.cancelSelectedNode();
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
		            return row.mrSectName
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
			<form:form id="searchForm" modelAttribute="sect" class="form form-horizontal well clearfix">
				<div class="col-xs-12 col-sm-4 col-md-3">
					<label style="float: left;">抄表册编号：</label>
					<input type="text" name="mrSectNo" class="form-control inline-block" placeholder="抄表册编号">	
				</div>
				<div class="col-xs-12 col-sm-4 col-md-3">
					<label class="label-item single-overflow pull-left" title="抄表册名称：">抄表册名称：</label>
					<input type="text" name="mrSectName" class="form-control inline-block" placeholder="抄表册名称">
				</div>
				<div class="col-xs-12 col-sm-4 col-md-3">
					<label class="label-item single-overflow pull-left" title="抄表方式：">抄表方式：</label>
					<form:select path="mrMode"  name="prcNarure" class="form-control m-b inline-block">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('mr_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select> 
				</div>
				<div class="col-xs-12 col-sm-4 col-md-3">
					<label class="label-item single-overflow pull-left" title="单位：">单位：</label>
					 <sys:treeselect id="company" name="company.id" value="${sect.company.id}" labelName="company.name" labelValue="${sect.company.name}"
										title="公司" url="/sys/office/treeData?type=1" allowClear="true" cssClass="form-control required"/>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-3">
					<label class="label-item single-overflow pull-left" title="抄表人员：">抄表人员：</label>
					<sys:userselect id="searchKpy" name="tuser.id" value="${sect.tuser.id}" labelName="tuser.name" labelValue="${sect.tuser.name}"
							    cssClass="form-control required"/>
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