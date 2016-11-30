package com.ynyes.lyz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdSales;
import com.ynyes.lyz.entity.TdSalesForActiveUser;
import com.ynyes.lyz.repository.TdSalesForActiveUserRepo;
import com.ynyes.lyz.repository.TdSalesRepo;

/**
 * 欠款报表 服务类
 * 
 * @author
 *
 */

@Service
@Transactional
@Component
public class TdSalesForActiveUserService {

	@Autowired
	TdSalesForActiveUserRepo repository;

	@Autowired
	TdSalesRepo saleRepository;
	
	private static Log myLog = LogFactory.getLog(TdSalesForActiveUser.class);
	
	@Scheduled(cron="0 0 1 * * ?")
	public void insertDataToSales() {
		
		myLog.info("开始执行销量抓取定时任务:"+new Date());
		List<TdSalesForActiveUser> activeSaleList = new ArrayList<TdSalesForActiveUser>();
		
		
		// 清空表中数据
		repository.deleteSalesForActiveUser();
		List<TdSales> salesList = querySalesList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:sss");
		
		for (int i = 0; i < salesList.size(); i++) {
			TdSales sale = salesList.get(i);
			TdSalesForActiveUser saleForAcitveUser = new TdSalesForActiveUser();
			//城市名称
			if(null!=sale.getCityName()&& !"".equalsIgnoreCase(sale.getCityName())){
				saleForAcitveUser.setCityName(sale.getCityName());
			}else{
				saleForAcitveUser.setCityName("");
			}
			
			//品牌
			if(null!=sale.getOrderNumber()&& !"".equalsIgnoreCase(sale.getOrderNumber())){
				if (sale.getOrderNumber().contains("HR")) {
					saleForAcitveUser.setBrand("华润");
				} else if (sale.getOrderNumber().contains("YR")) {
					saleForAcitveUser.setBrand("莹润");
				} else if (sale.getOrderNumber().contains("LYZ")) {
					saleForAcitveUser.setBrand("乐易装");
				}
			}else{
				saleForAcitveUser.setOrderNumber("");
			}
			
			//门店名称
			if(null!=sale.getDiySiteName()&& !"".equalsIgnoreCase(sale.getDiySiteName())){
				saleForAcitveUser.setDiySiteName(sale.getDiySiteName());
			}else{
				saleForAcitveUser.setDiySiteName("");
			}
			
			//销量类型
			if(null!=sale.getStype()&& !"".equalsIgnoreCase(sale.getStype())){
				saleForAcitveUser.setSalesType(sale.getStype());
			}else{
				saleForAcitveUser.setSalesType("");
			}
			
			//主单号
			if(null!=sale.getMainOrderNumber()&& !"".equalsIgnoreCase(sale.getMainOrderNumber())){
				saleForAcitveUser.setMainOrderNumber(sale.getMainOrderNumber());
			}else{
				saleForAcitveUser.setMainOrderNumber("");
			}
			
			
			//分单号
			if(null!=sale.getOrderNumber()&& !"".equalsIgnoreCase(sale.getOrderNumber())){
				saleForAcitveUser.setOrderNumber(sale.getOrderNumber());
			}else{
				saleForAcitveUser.setOrderNumber("");
			}
			
			
			//订单时间
			if(null!=sale.getOrderTime()){
				saleForAcitveUser.setOrderTime(sale.getOrderTime());
			}else{
				try {
					saleForAcitveUser.setOrderTime(format.parse("0000-00-00 00:00:00"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			//会员电话
			if(null!=sale.getUsername()&& !"".equalsIgnoreCase(sale.getUsername())){
				saleForAcitveUser.setUsername(sale.getUsername());
			}else{
				saleForAcitveUser.setUsername("");
			}
			//会员姓名
			if(null!=sale.getRealName()&& !"".equalsIgnoreCase(sale.getRealName())){
				saleForAcitveUser.setRealName(sale.getRealName());
			}else{
				saleForAcitveUser.setRealName("");
			}
			
			//导购电话
			if(null!=sale.getSellerUsername()&& !"".equalsIgnoreCase(sale.getSellerUsername())){
				saleForAcitveUser.setSellerUsername(sale.getSellerUsername());
			}else{
				saleForAcitveUser.setSellerUsername("");
			}
			
			//导购真实姓名
			if(null!=sale.getSellerRealName()&& !"".equalsIgnoreCase(sale.getSellerRealName())){
				saleForAcitveUser.setSellerRealName(sale.getSellerRealName());
			}else{
				saleForAcitveUser.setSellerRealName("");
			}
			//
			if(null!=sale.getSku()&& !"".equalsIgnoreCase(sale.getSku())){
				saleForAcitveUser.setSku(sale.getSku());
			}else{
				saleForAcitveUser.setSku("");
			}
			
			if(null!=sale.getGoodsTitle()&& !"".equalsIgnoreCase(sale.getGoodsTitle())){
				saleForAcitveUser.setGoodsTitle(sale.getGoodsTitle());
			}else{
				saleForAcitveUser.setGoodsTitle("");
			}
			
			// 赠品标识
			if (sale.getIsGift().equalsIgnoreCase("N")) {
				saleForAcitveUser.setIsGift("否");
			} else {
				saleForAcitveUser.setIsGift("是");
			}

			if (null == sale.getGoodsPrice()) {
				
				myLog.error("商品价格一栏为空，请检查错误单号:"+sale.getOrderNumber());
				continue;
				//saleForAcitveUser.setGoodsPrice(0.0);
				
			} else {
				saleForAcitveUser.setGoodsPrice(sale.getGoodsPrice());
			}

			if (null == sale.getGoodsQuantity()) {
				
				myLog.error("商品数量一栏为空，请检查错误单号:"+sale.getOrderNumber());
				continue;
				//saleForAcitveUser.setGoodsQuantity(0L);
			} else {
				saleForAcitveUser.setGoodsQuantity(sale.getGoodsQuantity());
			}

			saleForAcitveUser
					.setGoodsTotalPrice(saleForAcitveUser.getGoodsPrice() * saleForAcitveUser.getGoodsQuantity());

			if (null == sale.getCashCouponPrice()) {
				saleForAcitveUser.setCashCouponPrice(0.0);
			} else {
				saleForAcitveUser.setCashCouponPrice(sale.getCashCouponPrice());
			}

			if (null == sale.getCashCouponQuantity()) {
				saleForAcitveUser.setCashCouponQuantity(0L);
			} else {
				saleForAcitveUser.setCashCouponQuantity(sale.getCashCouponQuantity());
			}

			saleForAcitveUser.setCashCouponTotalPrice(
					saleForAcitveUser.getCashCouponPrice() * saleForAcitveUser.getCashCouponQuantity());

			if (null == sale.getProductCouponPrice()) {
				saleForAcitveUser.setProductCouponPrice(0.0);
			} else {
				saleForAcitveUser.setProductCouponPrice(sale.getProductCouponPrice());
			}

			if (null == sale.getProductCouponQuantity()) {
				saleForAcitveUser.setProductCouponQuantity(0L);

			} else {
				saleForAcitveUser.setProductCouponQuantity(sale.getProductCouponQuantity());
			}

			saleForAcitveUser.setProductCouponTotalPrice(
					saleForAcitveUser.getProductCouponPrice() * saleForAcitveUser.getProductCouponQuantity());

			saleForAcitveUser.setSalesSummary(saleForAcitveUser.getGoodsPrice()
					* (saleForAcitveUser.getGoodsQuantity() - saleForAcitveUser.getProductCouponQuantity())
					- saleForAcitveUser.getCashCouponPrice() * saleForAcitveUser.getCashCouponQuantity());

			activeSaleList.add(saleForAcitveUser);
		}

		for (int i = 0; i < activeSaleList.size(); i++) {
			repository.save(activeSaleList.get(i));
		}
		
		myLog.info("销量抓取任务完成:"+ new Date());

	}

	public List<TdSales> querySalesList() {
		String beginDateString = "2016-09-01 00:00:00";
		Date beginDate = stringToDate(beginDateString, null);
		Date endDate = new Date();
		List<TdSales> list = saleRepository.getSalesList(beginDate, endDate);
		return list;

	}

	/**
	 * 字符串转日期
	 * 
	 * @param time
	 * @param dateFormat
	 * @return
	 */
	public Date stringToDate(String time, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		if (StringUtils.isNotBlank(time)) {
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

}
