package com.ynyes.lyz.controller.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnMoneyRecordService;
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
	
	@Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;
	
	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;
	
	@Autowired 
	private TdOrderInfService tdOrderInfService;
	
	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	
	
	
	@RequestMapping(value = "/{number}", produces = "application/json;charset=utf8")
	@ResponseBody
	public String generation(@PathVariable String number) {
		TdOrder order = tdOrderService.findByOrderNumber(number);
		String result = generationService.generateOrderData(order);
		return result;
	} 
	
	@RequestMapping(value = "/order", produces = "application/json;charset=utf8")
	@ResponseBody
	public String generationOrderAll(String beginDate,String endDate) throws ParseException {
		
		if(null ==beginDate ){
			return "起始时间有误！";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<TdOrder> orders = tdOrderService.findMissedOrders(sdf.parse(beginDate),sdf.parse(endDate));
		for (TdOrder tdOrder : orders) {
			String result = generationService.generateOrderData(tdOrder);
			System.out.println(result);
		}
		String sb = "共处理"+orders.size()+"条数据!";
		return sb ;
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
			System.out.println("拒签退货共"+refuseNotes.size()+"条数据,正在处理第"+(++monitor)+"条数据！");
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
			System.out.println("正常退货共"+returnNotes.size()+"条数据,正在处理第"+(++monitor)+"条数据！");
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
	
	/**
	 * 生成收款接口表信息
	 * @param beginDate
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/cashreceipt", produces = "application/json;charset=utf8")
	@ResponseBody
	public String generateReceiptInfo(String beginDate) throws ParseException{
		if(null ==beginDate){
			return "起止日期格式错误!";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> missedOrderNumber = tdOwnMoneyRecordService.findMissedOrderNumbers(sdf.parse(beginDate));
		List<TdOwnMoneyRecord> owds = tdOwnMoneyRecordService.findByOrderNumberIn(missedOrderNumber);
		for (TdOwnMoneyRecord tdOwnMoneyRecord : owds) {
			this.initCashReciptByTdOwnMoneyRecord(tdOwnMoneyRecord);
		}
		
		
		return "共处理"+owds.size()+"条数据!";
	}
	
	
	public TdCashReciptInf initCashReciptByTdOwnMoneyRecord(TdOwnMoneyRecord ownMoneyRecord) {
		if (ownMoneyRecord == null) {
			return null;
		}
		List<TdOrder> subOrders = tdOrderService.findByMainOrderNumberIgnoreCase(ownMoneyRecord.getOrderNumber());
		if (subOrders == null || subOrders.size() < 1) {
			return null;
		}
		for (TdOrder tdOrder : subOrders) {
			if (tdOrder == null) {
				return null;
			}
			Double allTotalPay = tdOrder.getAllTotalPay() == null ? 0.00 : tdOrder.getAllTotalPay(); // 总单应支付金额
			if (allTotalPay == 0) {
				allTotalPay = 1d;
			}
			Double totalPrice = tdOrder.getTotalPrice() == null ? 0.00 : tdOrder.getTotalPrice(); // 分单应收金额

			// 新增：2016-09-13门店收取现金可能是负的
			Double money = ownMoneyRecord.getBackMoney();
			Double pos = ownMoneyRecord.getBackPos();
			Double backOther = ownMoneyRecord.getBackOther();
			if (null == money) {
				money = 0.00;
			}
			if (null == pos) {
				pos = 0.00;
			}
			if (null == backOther) {
				backOther = 0.00;
			}

			if (money < 0) {
				if (money + pos >= 0) {
					pos += money;
					money = 0.00;
				} else {
					backOther += (money + pos);
					money = 0.00;
					pos = 0.00;
				}
			}
			
			//接口表已生成的各项收款数据值
			Double intMoney=0.0;
			Double intPos=0.0;
			Double intBackMoney=0.0;
			Double intBackPos=0.0;
			Double intBackOther=0.0;
			List<TdCashReciptInf> receipts = tdCashReciptInfService.findByOrderNumber(tdOrder.getOrderNumber());
			for (TdCashReciptInf tdCashReciptInf : receipts) {
				if(tdCashReciptInf.getReceiptType().equalsIgnoreCase("配送现金")){
					intMoney=tdCashReciptInf.getAmount();
				}else if(tdCashReciptInf.getReceiptType().equalsIgnoreCase("配送POS")){
					intPos=tdCashReciptInf.getAmount();
				}else if(tdCashReciptInf.getReceiptType().equalsIgnoreCase("门店现金")){
					intBackMoney=tdCashReciptInf.getAmount();
				}else if(tdCashReciptInf.getReceiptType().equalsIgnoreCase("门店POS")){
					intBackPos=tdCashReciptInf.getAmount();
				}else if(tdCashReciptInf.getReceiptType().equalsIgnoreCase("门店其他")){
					intBackOther=tdCashReciptInf.getAmount();
				}
			}

			// 配送现金
			if (intMoney==0.0 && ownMoneyRecord.getMoney() > 0) {
				Double amount = totalPrice * ownMoneyRecord.getMoney() / (allTotalPay);
				if(amount>0.0){
					TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
							TdCashReciptInf.RECEIPT_TYPE_DELIVER_CASH, amount,ownMoneyRecord.getCreateTime());
					if (cashReciptInf != null) {
						tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					}
				}
				
			}
			// 配送pos
			if (intPos==0.0 && ownMoneyRecord.getPos() > 0) {
				Double amount = totalPrice * ownMoneyRecord.getPos() / allTotalPay;
				if(amount>0.0){
					TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
							TdCashReciptInf.RECEIPT_TYPE_DELIVER_POS, amount,ownMoneyRecord.getCreateTime());
					if (cashReciptInf != null) {
						tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					}
				}
				
			}
			// 门店现金
			if (intBackMoney==0.0 && money > 0) {
				Double amount = totalPrice * money / allTotalPay;
				if(amount>0.0){
					TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
							TdCashReciptInf.RECEIPT_TYPE_DIYSITE_CASH, amount,ownMoneyRecord.getCreateTime());
					if (cashReciptInf != null) {
						tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					}
				}
				
			}
			// 门店pos
			if (intBackPos==0.0 && pos > 0) {
				Double amount = totalPrice * pos / allTotalPay;
				if(amount>0.0){
					TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
							TdCashReciptInf.RECEIPT_TYPE_DIYSITE_POS, amount,ownMoneyRecord.getCreateTime());
					if (cashReciptInf != null) {
						tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					}
				}
				
			}
			// 新增：2016-08-25抛出门店其他还款金额
			if (intBackOther==0.0 && backOther > 0) {
				Double amount = totalPrice * backOther / allTotalPay;
				if(amount>0.0){
					TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
							TdCashReciptInf.RECEIPT_TYPE_DIYSITE_OHTER, amount,ownMoneyRecord.getCreateTime());
					if (cashReciptInf != null) {
						tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					}
				}
				
			}

		}

		return null;
	}
	
	public TdCashReciptInf initCashReceiptInfWithOrderAndReceiptTypeAndMoney(TdOrder tdOrder, String receiptTtpe,
			Double amount,Date ownTime) {
		TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
		if (orderInf == null) {
			return null;
		}

		TdDiySite diySite = tdDiySiteService.findOne(tdOrder.getDiySiteId());
		Long SobId = 0L;
		if (diySite != null) {
			SobId = diySite.getRegionId();
		}
		TdCashReciptInf cashReciptInf = new TdCashReciptInf();
		cashReciptInf.setSobId(SobId);
		cashReciptInf.setReceiptNumber(StringTools.getUniqueNoWithHeader("RC"));
		cashReciptInf.setUserid(tdOrder.getRealUserId());
		cashReciptInf.setUsername(tdOrder.getRealUserRealName());
		cashReciptInf.setUserphone(tdOrder.getRealUserUsername());
		cashReciptInf.setDiySiteCode(tdOrder.getDiySiteCode());
		cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(tdOrder.getIsCoupon()));
		cashReciptInf.setOrderHeaderId(orderInf.getHeaderId());
		cashReciptInf.setOrderNumber(tdOrder.getOrderNumber());
		cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(tdOrder.getOrderNumber()));
		cashReciptInf.setReceiptType(receiptTtpe);
		cashReciptInf.setReceiptDate(ownTime);
		cashReciptInf.setAmount(amount);
		return tdCashReciptInfService.save(cashReciptInf);
	}
	
}
