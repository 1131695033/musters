/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.order.orders.web;

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
import com.jeeplus.modules.order.orders.entity.OrderDetails;
import com.jeeplus.modules.order.orders.entity.Orders;
import com.jeeplus.modules.order.orders.service.OrderDetailsService;
import com.jeeplus.modules.order.orders.service.OrdersService;

/**
 * 订单信息Controller
 * @author zyh
 * @version 2018-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orders")
public class OrdersController extends BaseController {

	@Autowired
	private OrdersService orderService;
	@Autowired
	private OrderDetailsService orderDetailsService;
	
	@ModelAttribute
	public Orders get(@RequestParam(required=false) String id) {
		Orders entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderService.get(id);
		}
		if (entity == null){
			entity = new Orders();
		}
		return entity;
	}
	
	/**
	 * 购物车信息列表页面
	 */
	@RequiresPermissions("order:orders:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/order/orders/ordersList";
	}
	
		/**
	 * 购物车信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("order:orders:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Orders Order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Orders> page = orderService.findPage(new Page<Orders>(request, response), Order); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑购物车信息表单页面
	 */
	@RequiresPermissions(value={"order:orders:view","order:orders:add","order:orders:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Orders Order, Model model) {
		model.addAttribute("order", Order);
		return "modules/order/orders/ordersForm";
	}

	/**
	 * 保存购物车信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"order:orders:add","order:orders:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Orders Order, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, Order)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		orderService.save(Order);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存购物车信息成功");
		return j;
	}
	
	/**
	 * 删除购物车信息
	 */
	@ResponseBody
	@RequiresPermissions("order:orders:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Orders Order, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderService.delete(Order);
		j.setMsg("删除购物车信息成功");
		return j;
	}
	
	/**
	 * 批量删除购物车信息
	 */
	@ResponseBody
	@RequiresPermissions("order:orders:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderService.delete(orderService.get(id));
		}
		j.setMsg("删除购物车信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("order:orders:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Orders Order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "购物车信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Orders> page = orderService.findPage(new Page<Orders>(request, response, -1), Order);
    		new ExportExcel("购物车信息", Orders.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("order:orders:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Orders> list = ei.getDataList(Orders.class);
			for (Orders Order : list){
				try{
					orderService.save(Order);
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
		return "redirect:"+Global.getAdminPath()+"/Order/Order/?repage";
    }
	
	/**
	 * 下载导入购物车信息数据模板
	 */
	@RequiresPermissions("order:orders:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "购物车信息数据导入模板.xlsx";
    		List<Orders> list = Lists.newArrayList(); 
    		new ExportExcel("购物车信息数据", Orders.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/Order/Order/?repage";
    }

	@ResponseBody
    @RequestMapping(value = "detail")
	public Orders detail(String id) {
		return orderService.get(id);
	}
}