/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.seller.web;

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
import com.jeeplus.modules.seller.entity.Seller;
import com.jeeplus.modules.seller.service.SellerService;

/**
 * 商铺信息Controller
 * @author zyh
 * @version 2018-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/seller/seller")
public class SellerController extends BaseController {

	@Autowired
	private SellerService sellerService;
	
	@ModelAttribute
	public Seller get(@RequestParam(required=false) String id) {
		Seller entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sellerService.get(id);
		}
		if (entity == null){
			entity = new Seller();
		}
		return entity;
	}
	
	/**
	 * 商铺信息列表页面
	 */
	@RequiresPermissions("seller:seller:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/seller/sellerList";
	}
	
		/**
	 * 商铺信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("seller:seller:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Seller seller, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Seller> page = sellerService.findPage(new Page<Seller>(request, response), seller); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商铺信息表单页面
	 */
	@RequiresPermissions(value={"seller:seller:view","seller:seller:add","seller:seller:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Seller seller, Model model) {
		model.addAttribute("seller", seller);
		return "modules/seller/sellerForm";
	}

	/**
	 * 保存商铺信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"seller:seller:add","seller:seller:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Seller seller, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, seller)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		sellerService.save(seller);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商铺信息成功");
		return j;
	}
	
	/**
	 * 删除商铺信息
	 */
	@ResponseBody
	@RequiresPermissions("seller:seller:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Seller seller, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		sellerService.delete(seller);
		j.setMsg("删除商铺信息成功");
		return j;
	}
	
	/**
	 * 批量删除商铺信息
	 */
	@ResponseBody
	@RequiresPermissions("seller:seller:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sellerService.delete(sellerService.get(id));
		}
		j.setMsg("删除商铺信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("seller:seller:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Seller seller, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商铺信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Seller> page = sellerService.findPage(new Page<Seller>(request, response, -1), seller);
    		new ExportExcel("商铺信息", Seller.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商铺信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("seller:seller:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Seller> list = ei.getDataList(Seller.class);
			for (Seller seller : list){
				try{
					sellerService.save(seller);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商铺信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商铺信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商铺信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/seller/seller/?repage";
    }
	
	/**
	 * 下载导入商铺信息数据模板
	 */
	@RequiresPermissions("seller:seller:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商铺信息数据导入模板.xlsx";
    		List<Seller> list = Lists.newArrayList(); 
    		new ExportExcel("商铺信息数据", Seller.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/seller/seller/?repage";
    }

}