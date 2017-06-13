package com.ynyes.lyz.interfaces.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdReturnNoteService;

@Controller
@RequestMapping(value = "/wms")
public class TdWmsResendController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TdWmsResendController.class); 
	
	@Autowired
	TdReturnNoteService returnNoteService;
	
	@Autowired
	TdCommonService tdCommonService;
	
	/**
	 * 正常退货重传WMS
	 * @param returnNumber
	 * @return
	 */
	@RequestMapping(value="/returnNote",produces = "application/json;charset=utf8")
	@ResponseBody
	public String resendReturnNote(@RequestParam String returnNumber){
		LOGGER.info("重传退货单: {} ",returnNumber);
		TdReturnNote returnNote = returnNoteService.findByReturnNumber(returnNumber);
		if(null != returnNote && returnNote.getTurnType() == 2){
			if(!returnNote.getRemarkInfo().equals("用户取消订单，退货") && !returnNote.equals("拒签退货")){
				if(returnNote.getStatusId() != null && returnNote.getStatusId() == 1){
					tdCommonService.sendBackToWMS(returnNote);
					if (returnNote.getStatusId() == 1L) {
						returnNote.setStatusId(2L);
					}
					returnNoteService.save(returnNote);
					LOGGER.info("{}重传成功",returnNumber);
					return returnNote.getReturnNumber()+"重传成功!";
				}
				return "该订单已传输！";
			}
			return "该单不是正常销售退货，请选择正确的重传方法!";//该单是拒签退货或取消订单
		}
		LOGGER.error("未找到该退货单:{},无法重传",returnNumber);
		return "未找到该退单或该退单不是物流取货！";
	}
	
	/**
	 * 取消订单重传WMS
	 * @param returnNumber
	 * @return
	 */
	@RequestMapping(value="/cancelOrder",produces = "application/json;charset=utf8")
	@ResponseBody
	public String resendCancelOrder(@RequestParam  String returnNumber){
		LOGGER.info("重传退货单:{}",returnNumber);
		if(null == returnNumber){
			LOGGER.info("退货单: {} 不存在",returnNumber);
			return "该退单号不存在!";
		}
		TdReturnNote returnNote = returnNoteService.findByReturnNumber(returnNumber);
		if(null == returnNote){
			LOGGER.info("退货单: {} 不存在",returnNumber);
			return "该退货单不存在!";
		}
		if(null != returnNote.getRemarkInfo() && returnNote.getRemarkInfo().equals("用户取消订单，退货")){
			if(null != returnNote.getStatusId() && returnNote.getStatusId() == 5){
				//TODO
				tdCommonService.sendBackMsgToWMS(returnNote);
				LOGGER.info("退单 {}重传成功！",returnNumber);
				return returnNumber+"重传成功！";
			}else{
				LOGGER.error("退货单: {} 退单状态异常，请联系管理员查看!",returnNumber);
				return "退单状态异常，请联系管理员查看！";
			}
		}else{
			LOGGER.error("退货单：{} 不是取消订单产生的退单！ ",returnNumber);
			return "该退货单不是取消订单产生的退货单！";
		}	
	}
	
	/**
	 * 拒签退货重传WMS
	 * @param returnNumber
	 * @return
	 */
	@RequestMapping(value="/refusalReturn",produces = "application/json;charset=utf8")
	@ResponseBody
	public String resendRefusalReturn(@RequestParam  String returnNumber){
		LOGGER.info("重传退货单:{}",returnNumber);
		if(null == returnNumber){
			LOGGER.info("退货单: {} 不存在",returnNumber);
			return "该退单号不存在!";
		}
		TdReturnNote returnNote = returnNoteService.findByReturnNumber(returnNumber);
		if(null == returnNote){
			LOGGER.info("退货单: {} 不存在",returnNumber);
			return "该退货单不存在!";
		}
		if(null != returnNote.getRemarkInfo() && returnNote.getRemarkInfo().equals("拒签退货")){
			if(null != returnNote.getStatusId() && returnNote.getStatusId() == 5){
				//TODO
				tdCommonService.sendBackMsgToWMS(returnNote);
				LOGGER.info("退单 {}重传成功！",returnNumber);
				return returnNumber+"重传成功！";
			}else{
				LOGGER.error("退货单: {} 退单状态异常，请联系管理员查看!",returnNumber);
				return "退单状态异常，请联系管理员查看！";
			}
		}else{
			LOGGER.error("退货单：{} 不是拒签退货产生的退单！ ",returnNumber);
			return "该退货单不是拒签退货产生的退单！";
		}	
	}
	
	

}
