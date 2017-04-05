package com.ynyes.lyz.controller.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.basic.generation.IGenerationService;

@Controller
@RequestMapping(value = "/generation")
public class TdGenerationController {
	
	@Autowired
	private TdOrderService tdOrderService;
	
	@Autowired
	private IGenerationService generationService;
	
	@Autowired
	private TdReturnNoteService tdReturnNoteService;
	
	@Autowired
	private TdInterfaceService tdInterfaceService;
	
	
	@RequestMapping(value = "/{number}", produces = "application/json;charset=utf8")
	@ResponseBody
	public String generation(@PathVariable String number) {
		TdOrder order = tdOrderService.findByOrderNumber(number);
		String result = generationService.generateOrderData(order);
		return result;
	} 
	
	/**
	 * 生成EBS退货单信息
	 * @param beginDate 起始时间，表示检索数据范围从哪一天开始
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/return", produces = "application/json;charset=utf8")
	@ResponseBody
	public String generateCancelReturnOrderInf(String beginDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null == beginDate){
			return "起始时间有误!";
		}
		//开始处理取消订单未生成的退单数据
		List<TdReturnNote> notes = tdReturnNoteService.findMissedReturnOrder(sdf.parse(beginDate));
		System.out.println("开始处理取消订单未生成的退货单,共"+notes.size()+"条数据！");
		int monitor = 0;
		for (TdReturnNote tdReturnNote : notes) {
			System.out.println("取消订单共"+notes.size()+"条数据,正在处理第"+(++monitor)+"条数据！");
			TdOrder order = tdOrderService.findByOrderNumber(tdReturnNote.getOrderNumber());
			tdInterfaceService.initReturnOrder(tdReturnNote, INFConstants.INF_RETURN_ORDER_CANCEL_INT);
			tdInterfaceService.initReturnCouponInfByOrder(order, INFConstants.INF_RETURN_ORDER_CANCEL_INT);
			tdInterfaceService.sendReturnOrderByAsyn(tdReturnNote);
			System.out.println("取消订单第"+monitor+"条数据处理成功!");
		}
		System.out.println("取消订单未生成的退货单已处理完毕!");
		monitor = 0;
		
		//开始处理拒签退货的数据
		List<TdReturnNote> refuseNotes = tdReturnNoteService.findRefusedReturnOrder(sdf.parse(beginDate));
		System.out.println("开始处理拒签退货未生成的退货单，共"+refuseNotes.size()+"条数据!");
		for (TdReturnNote tdReturnNote : refuseNotes) {
			System.out.println("拒签退货共"+notes.size()+"条数据,正在处理第"+(++monitor)+"条数据！");
			TdOrder order = tdOrderService.findByOrderNumber(tdReturnNote.getOrderNumber());
			tdInterfaceService.initReturnOrder(tdReturnNote, INFConstants.INF_RETURN_ORDER_SUB_INT);
			tdInterfaceService.initReturnCouponInfByOrder(order, INFConstants.INF_RETURN_ORDER_CANCEL_INT);
			tdInterfaceService.sendReturnOrderByAsyn(tdReturnNote);
			System.out.println("拒签退货第"+monitor+"条数据处理成功!");
		}
		System.out.println("拒签退货未生成的退货单已处理完毕!");
		monitor = 0;
		
		//处理正常退货单的数据
		List<TdReturnNote> returnNotes = tdReturnNoteService.findNormalReturnOrder(sdf.parse(beginDate));
		System.out.println("开始处理正常退货未生成的退货单,共"+returnNotes.size()+"条数据!");
		for (TdReturnNote tdReturnNote : returnNotes) {
			System.out.println("正常退货共"+notes.size()+"条数据,正在处理第"+(++monitor)+"条数据！");
			TdOrder order = tdOrderService.findByOrderNumber(tdReturnNote.getOrderNumber());
			tdInterfaceService.initReturnOrder(tdReturnNote, INFConstants.INF_RETURN_ORDER_SUB_INT);
			tdInterfaceService.initReturnCouponInfByOrder(order, INFConstants.INF_RETURN_ORDER_SUB_INT);
			tdInterfaceService.sendReturnOrderByAsyn(tdReturnNote);
			System.out.println("正常退货第"+monitor+"条数据处理成功!");
			
		}
		System.out.println("正常退货未生成的退货单已处理完毕！");
		
		String sb = "共处理了"+(notes.size()+refuseNotes.size()+returnNotes.size())+"条退货未生成数据";
		return sb;
		
	}
	
}
