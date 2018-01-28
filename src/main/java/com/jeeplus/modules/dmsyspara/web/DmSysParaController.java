/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmsyspara.web;

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
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.dmsyspara.entity.DmSysPara;
import com.jeeplus.modules.dmsyspara.service.DmSysParaService;

/**
 * 系统参数维护Controller
 * @author qcz
 * @version 2017-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/dmsyspara/dmSysPara")
public class DmSysParaController extends BaseController {

	@Autowired
	private DmSysParaService dmSysParaService;
	
	@ModelAttribute
	public DmSysPara get(@RequestParam(required=false) String id) {
		DmSysPara entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmSysParaService.get(id);
		}
		if (entity == null){
			entity = new DmSysPara();
		}
		return entity;
	}
	
	/**
	 * 系统参数维护列表页面
	 */
	@RequiresPermissions("dmsyspara:dmSysPara:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/dmsyspara/dmSysParaList";
	}
	
		/**
	 * 系统参数维护列表数据
	 */
	@ResponseBody
	@RequiresPermissions("dmsyspara:dmSysPara:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DmSysPara dmSysPara, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmSysPara> page = dmSysParaService.findPage(new Page<DmSysPara>(request, response), dmSysPara); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑系统参数维护表单页面
	 */
	@RequiresPermissions(value={"dmsyspara:dmSysPara:view","dmsyspara:dmSysPara:add","dmsyspara:dmSysPara:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DmSysPara dmSysPara, Model model) {
		model.addAttribute("dmSysPara", dmSysPara);
		return "modules/dmsyspara/dmSysParaForm";
	}

	/**
	 * 保存系统参数维护
	 */
	@ResponseBody
	@RequiresPermissions(value={"dmsyspara:dmSysPara:add","dmsyspara:dmSysPara:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(DmSysPara dmSysPara, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, dmSysPara)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		dmSysParaService.save(dmSysPara);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存系统参数维护成功");
		return j;
	}
	
	/**
	 * 删除系统参数维护
	 */
	@ResponseBody
	@RequiresPermissions("dmsyspara:dmSysPara:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DmSysPara dmSysPara, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		dmSysParaService.delete(dmSysPara);
		j.setMsg("删除系统参数维护成功");
		return j;
	}
	
	/**
	 * 批量删除系统参数维护
	 */
	@ResponseBody
	@RequiresPermissions("dmsyspara:dmSysPara:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dmSysParaService.delete(dmSysParaService.get(id));
		}
		j.setMsg("删除系统参数维护成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("dmsyspara:dmSysPara:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DmSysPara dmSysPara, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "系统参数维护"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DmSysPara> page = dmSysParaService.findPage(new Page<DmSysPara>(request, response, -1), dmSysPara);
    		new ExportExcel("系统参数维护", DmSysPara.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出系统参数维护记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("dmsyspara:dmSysPara:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DmSysPara> list = ei.getDataList(DmSysPara.class);
			for (DmSysPara dmSysPara : list){
				try{
					dmSysParaService.save(dmSysPara);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条系统参数维护记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统参数维护记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统参数维护失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmsyspara/dmSysPara/?repage";
    }
	
	/**
	 * 下载导入系统参数维护数据模板
	 */
	@RequiresPermissions("dmsyspara:dmSysPara:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统参数维护数据导入模板.xlsx";
    		List<DmSysPara> list = Lists.newArrayList(); 
    		new ExportExcel("系统参数维护数据", DmSysPara.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmsyspara/dmSysPara/?repage";
    }

}