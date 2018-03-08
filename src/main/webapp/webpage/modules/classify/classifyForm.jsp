<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>服务分类管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $classifyTreeTable; // 父页面table表格id
		var $topIndex;//openDialog的 dialog index
		function doSubmit(treeTable, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $classifyTreeTable = treeTable;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
						jp.post("${ctx}/classify/classify/save",$('#inputForm').serialize(),function(data){
		                    if(data.success){
		                    	var current_id = data.body.classify.id;
		                    	var target = $classifyTreeTable.get(current_id);
		                    	var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
		                    	var current_parent_id = data.body.classify.parentId;
		                    	var current_parent_ids = data.body.classify.parentIds;
		                    	
		                    	if(old_parent_id == current_parent_id){
		                    		if(current_parent_id == '0'){
		                    			$classifyTreeTable.refreshPoint(-1);
		                    		}else{
		                    			$classifyTreeTable.refreshPoint(current_parent_id);
		                    		}
		                    	}else{
		                    		$classifyTreeTable.del(current_id);//刷新删除旧节点
		                    		$classifyTreeTable.initParents(current_parent_ids, "0");
		                    	}
		                    	
		                    	jp.success(data.msg);
		                    }else {
	            	  			jp.error(data.msg);
		                    }
		                    
		                        jp.close($topIndex);//关闭dialog
		            });;
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="classify" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">父级id：</label></td>
					<td class="width-35">
							<sys:treeselect id="parent" name="parent.id" value="${classify.parent.id}" labelName="parent.name" labelValue="${classify.parent.name}"
						title="父级编号" url="/classify/classify/treeData" extId="${classify.id}" cssClass="form-control " allowClear="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">类型：</label></td>
					<td class="width-35">
					<form:select path="type" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('service_classify')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
		</form:form>
</body>
</html>