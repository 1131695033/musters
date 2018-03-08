/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.classify.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.config.Global;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.classify.entity.Classify;
import com.jeeplus.modules.classify.service.ClassifyService;

/**
 * 服务分类Controller
 * @author zyh
 * @version 2018-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/classify/classify")
public class ClassifyController extends BaseController {

	@Autowired
	private ClassifyService classifyService;
	
	@ModelAttribute
	public Classify get(@RequestParam(required=false) String id) {
		Classify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = classifyService.get(id);
		}
		if (entity == null){
			entity = new Classify();
		}
		return entity;
	}
	
	/**
	 * 服务分类列表页面
	 */
	@RequiresPermissions("classify:classify:list")
	@RequestMapping(value = {"list", ""})
	public String list(Classify classify,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/classify/classifyList";
	}

	/**
	 * 查看，增加，编辑服务分类表单页面
	 */
	@RequiresPermissions(value={"classify:classify:view","classify:classify:add","classify:classify:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Classify classify, Model model) {
		if (classify.getParent()!=null && StringUtils.isNotBlank(classify.getParent().getId())){
			classify.setParent(classifyService.get(classify.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(classify.getId())){
				Classify classifyChild = new Classify();
				classifyChild.setParent(new Classify(classify.getParent().getId()));
				List<Classify> list = classifyService.findList(classify); 
				if (list.size() > 0){
					classify.setSort(list.get(list.size()-1).getSort());
					if (classify.getSort() != null){
						classify.setSort(classify.getSort() + 30);
					}
				}
			}
		}
		if (classify.getSort() == null){
			classify.setSort(30);
		}
		model.addAttribute("classify", classify);
		return "modules/classify/classifyForm";
	}

	/**
	 * 保存服务分类
	 */
	@ResponseBody
	@RequiresPermissions(value={"classify:classify:add","classify:classify:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Classify classify, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, classify)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		//新增或编辑表单保存
		classifyService.save(classify);//保存
		j.setSuccess(true);
		j.put("classify", classify);
		j.setMsg("保存服务分类成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Classify> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return classifyService.getChildren(parentId);
	}
	
	/**
	 * 删除服务分类
	 */
	@ResponseBody
	@RequiresPermissions("classify:classify:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Classify classify, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		classifyService.delete(classify);
		j.setSuccess(true);
		j.setMsg("删除服务分类成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Classify> list = classifyService.findList(new Classify());
		for (int i=0; i<list.size(); i++){
			Classify e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				map.put("typeName", e.getTypeName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}