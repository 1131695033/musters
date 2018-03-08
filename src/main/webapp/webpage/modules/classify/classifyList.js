<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $classifyTreeTable=null;  
		$(document).ready(function() {
			$classifyTreeTable=$('#classifyTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/classify/classify/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#classifyTreeTableTpl").html();

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($classifyTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $classifyTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($classifyTreeTable, id) {   
		            },  
		            afterExpand : function($classifyTreeTable, id) {  
		            },  
		            beforeClose : function($classifyTreeTable, id) {    
		            	
		            }  
		        });
		        
		        $classifyTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除服务分类吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/classify/classify/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$classifyTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$classifyTreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="classifyTreeTableTpl">
			<td>
			<a  href="#" onclick="jp.openDialogView('查看服务分类', '${ctx}/classify/classify/form?id={{d.row.id}}','800px', '500px')">{{d.row.name === undefined ? "": d.row.name}}
			</a>
			</td>
			<td>
				{{d.row.typeName === undefined ? "": d.row.typeName}}
			</td>
			<td>
				{{d.row.sort === undefined ? "": d.row.sort}}
			</td>
			<td>
				<div class="btn-group">
			 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog"></i>
						<span class="fa fa-chevron-down"></span>
					</button>
				  <ul class="dropdown-menu" role="menu">
					<shiro:hasPermission name="classify:classify:view">
						<li><a href="#" onclick="jp.openDialogView('查看服务分类', '${ctx}/classify/classify/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="classify:classify:edit">
						<li><a href="#" onclick="jp.openDialog('修改服务分类', '${ctx}/classify/classify/form?id={{d.row.id}}','800px', '500px', $classifyTreeTable)"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="classify:classify:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="classify:classify:add">
						<li><a href="#" onclick="jp.openDialog('添加下级服务分类', '${ctx}/classify/classify/form?parent.id={{d.row.id}}','800px', '500px', $classifyTreeTable)"><i class="fa fa-plus"></i> 添加下级服务分类</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>