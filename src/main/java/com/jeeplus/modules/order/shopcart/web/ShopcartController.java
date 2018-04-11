/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.order.shopcart.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.order.shopcart.entity.Shopcart;
import com.jeeplus.modules.order.shopcart.service.ShopcartService;

/**
 * 购物车信息Controller
 * @author zyh
 * @version 2018-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/order/shopcart")
public class ShopcartController extends BaseController {

	@Autowired
	private ShopcartService ShopcartService;
	
	@ModelAttribute
	public Shopcart get(@RequestParam(required=false) String id) {
		Shopcart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ShopcartService.get(id);
		}
		if (entity == null){
			entity = new Shopcart();
		}
		return entity;
	}
	
	/**
	 * 购物车信息列表页面
	 */
	@RequiresPermissions("shopcart:shopcart:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/order/shopcart/shopcartList";
	}
	
		/**
	 * 购物车信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shopcart:shopcart:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Shopcart Shopcart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Shopcart> page = ShopcartService.findPage(new Page<Shopcart>(request, response), Shopcart); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑购物车信息表单页面
	 */
	@RequiresPermissions(value={"shopcart:shopcart:view","shopcart:shopcart:add","shopcart:shopcart:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Shopcart Shopcart, Model model) {
		model.addAttribute("Shopcart", Shopcart);
		return "modules/order/shopcart/shopcartForm";
	}

	/**
	 * 保存购物车信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"shopcart:shopcart:add","shopcart:shopcart:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Shopcart Shopcart, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, Shopcart)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		ShopcartService.save(Shopcart);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存购物车信息成功");
		return j;
	}
	
	/**
	 * 删除购物车信息
	 */
	@ResponseBody
	@RequiresPermissions("shopcart:shopcart:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Shopcart Shopcart, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		ShopcartService.delete(Shopcart);
		j.setMsg("删除购物车信息成功");
		return j;
	}
	
	/**
	 * 批量删除购物车信息
	 */
	@ResponseBody
	@RequiresPermissions("shopcart:shopcart:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			ShopcartService.delete(ShopcartService.get(id));
		}
		j.setMsg("删除购物车信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shopcart:shopcart:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Shopcart Shopcart, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "购物车信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Shopcart> page = ShopcartService.findPage(new Page<Shopcart>(request, response, -1), Shopcart);
    		new ExportExcel("购物车信息", Shopcart.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出购物车信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shopcart:shopcart:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Shopcart> list = ei.getDataList(Shopcart.class);
			for (Shopcart Shopcart : list){
				try{
					ShopcartService.save(Shopcart);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条购物车信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条购物车信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入购物车信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/Shopcart/Shopcart/?repage";
    }
	
	/**
	 * 下载导入购物车信息数据模板
	 */
	@RequiresPermissions("shopcart:shopcart:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "购物车信息数据导入模板.xlsx";
    		List<Shopcart> list = Lists.newArrayList(); 
    		new ExportExcel("购物车信息数据", Shopcart.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/Shopcart/Shopcart/?repage";
    }

}