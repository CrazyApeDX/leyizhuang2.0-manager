package com.tencent.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.ui.ModelMap;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.interfaces.utils.InterfaceConfigure;

public class TdWXPay {
	
	private static String notify_url_str = InterfaceConfigure.WX_NOTIFY_RETURN_URL;
	
	private static String trade_type_str = "APP";
	
	private static ModelMap signMap = new ModelMap();
	
	/**
	 * 统一下单所需要的XML
	 * @param order
	 * @return
	 */
	public static String getUnifiedorderXML(TdOrder order)
	{
		/*
		 *  app调起WX支付必要参数如下：
		 * 
		 * appid         ：微信开放平台审核通过的应用APPID
		 * mch_id        ：微信支付分配的商户号
		 * nonce_str      ：随机字符串，不长于32位。
		 * body           ：商品或支付单简要描述
		 * out_trade_no   ：商户系统内部的订单号,32个字符内、可包含字母
		 * total_fee       ：订单总金额，单位为分
		 * spbill_create_ip ：用户端实际ip
		 * notify_url       ：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		 * trade_type      ：支付类型
		 * sign            ：签名
		 * 
		 * ------> end
		 */
		
		//统一下单的签名XML
		String body = "支付订单" + order.getOrderNumber();
		String out_trade_no = order.getOrderNumber();
		Long total_fee = Math.round(order.getTotalPrice() * 100);
		String nonce_str = RandomStringGenerator.getRandomStringByLength(32);
		
//		ModelMap signMap = new ModelMap();
		signMap.addAttribute("appid", Configure.getAppid());
		signMap.addAttribute("mch_id", Configure.getMchid());
		signMap.addAttribute("nonce_str",nonce_str);
		signMap.addAttribute("body", body);
		signMap.addAttribute("out_trade_no", out_trade_no);
		signMap.addAttribute("total_fee", total_fee);
		signMap.addAttribute("spbill_create_ip", Configure.getIP());
		signMap.addAttribute("notify_url", notify_url_str);
		signMap.addAttribute("trade_type", trade_type_str);
		signMap.addAttribute("device_info", "APP");

		String sign = Signature.getSign(signMap);

		String content = "<xml>\n"
					+ "<appid>" + Configure.getAppid() + "</appid>\n"
					+ "<mch_id>" + Configure.getMchid() + "</mch_id>\n"
					+ "<nonce_str>" + nonce_str + "</nonce_str>\n"
					+ "<body>" + body + "</body>\n"
					+ "<out_trade_no>" + out_trade_no + "</out_trade_no>\n"
					+ "<total_fee>" + total_fee + "</total_fee>\n"
					+ "<spbill_create_ip>"+ Configure.getIP() +"</spbill_create_ip>\n"
					+ "<notify_url>"+ notify_url_str +"</notify_url>\n"
					+ "<trade_type>"+ trade_type_str +"</trade_type>\n"
					+ "<device_info>"+ "APP" +"</device_info>\n"
					+ "<sign>" + sign + "</sign>\n"
					+ "</xml>\n";
//		System.out.print("MDJ: xml=" + content + "\n");
		return content;
	}
	
	
	/**
	 * 讲 map 转化为 XML
	 * @param map
	 * @return
	 */
	public static String mapToXML(ModelMap map) {
        Document document = DocumentHelper.createDocument();  
        Element nodeElement = document.addElement("node");  
        for (Object obj : map.keySet()) 
        {  
            Element keyElement = nodeElement.addElement("key");  
            keyElement.addAttribute("label", String.valueOf(obj));  
            keyElement.setText(String.valueOf(map.get(obj)));  
        }  
        return docToString(document);  
    } 
	
	
	/**
	 * 讲 Document转化为String
	 * @param document
	 * @return
	 */
	public static String docToString(Document document) {
        String s = "";  
        try {  
            // 使用输出流来进行转化  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            // 使用UTF-8编码  
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");  
            XMLWriter writer = new XMLWriter(out, format);  
            writer.write(document);  
            s = out.toString("UTF-8");  
        } catch (Exception ex) {
            ex.printStackTrace();  
        }  
        return s;  
    } 
	
	public static void addSignMap(String key, Object object)
	{
		signMap.addAttribute(key, object);
	}
	
	/**
	 * 统一下单请求获取 prepay_id
	 * 
	 * @param requestXML
	 */
	public static ModelMap sendUnifiedorderRequest(String requestXML)
	{
		
		String return_code = null;
		String prepay_id = null;
		String result_code = null;
		String line = null;
		
		if (requestXML == null)
		{
			return null;
		}
		try 
		{
			HttpsURLConnection urlConection = null;
			urlConection = (HttpsURLConnection) (new URL(Configure.getUNIFIED_ORDER_API())).openConnection();
			urlConection.setDoInput(true);
			urlConection.setDoOutput(true);
			urlConection.setRequestMethod("POST");
			urlConection.setRequestProperty("Content-Length",String.valueOf(requestXML.getBytes().length));
			urlConection.setUseCaches(false);
			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
			urlConection.getOutputStream().write(requestXML.getBytes("utf-8"));
			urlConection.getOutputStream().flush();
			urlConection.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConection.getInputStream()));

			while ((line = in.readLine()) != null)
			{
				System.out.println(": rline: " + line);
				if (line.contains("<return_code>"))
				{
					return_code = line.replaceAll("<xml><return_code><\\!\\[CDATA\\[", "").replaceAll("\\]\\]></return_code>", "");
				} 
				else if (line.contains("<prepay_id>")) 
				{
					prepay_id = line.replaceAll("<prepay_id><\\!\\[CDATA\\[","").replaceAll("\\]\\]></prepay_id>", "");
				}
				else if (line.contains("<result_code>"))
				{
					result_code = line.replaceAll("<result_code><\\!\\[CDATA\\[", "").replaceAll("\\]\\]></result_code>", "");
				}
			}

			//		System.out.println("MDJ: return_code: " + return_code + "\n");
			//		System.out.println("MDJ: prepay_id: " + prepay_id + "\n");
			//		System.out.println("MDJ: result_code: " + result_code + "\n");

			if ("SUCCESS".equalsIgnoreCase(return_code) && "SUCCESS".equalsIgnoreCase(result_code) && null != prepay_id)
			{
				String nonce_str = RandomStringGenerator.getRandomStringByLength(32);

				String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
				String packageString = "Sign=WXPay";
				String signType = "MD5";
				ModelMap returnsignmap = new ModelMap();
				returnsignmap.addAttribute("appid", Configure.getAppid());
				returnsignmap.addAttribute("noncestr", nonce_str);
				returnsignmap.addAttribute("package", packageString);
				returnsignmap.addAttribute("partnerid", Configure.getMchid());
				returnsignmap.addAttribute("timestamp", timeStamp);
				returnsignmap.addAttribute("prepayid", prepay_id);

				String returnsign = Signature.getSign(returnsignmap);
				requestXML = "<xml>\n" 
							+ "<appid>" + Configure.getAppid() + "</appid>\n"
							+ "<timeStamp>" + timeStamp + "</timeStamp>\n" 
							+ "<nonceStr>" + nonce_str + "</nonceStr>\n" 
							+ "<package>" + packageString + "</package>\n" 
							+ "<signType>" + signType + "</signType>\n"
							+ "<signType>" + returnsign + "</signType>\n"
							+ "</xml>\n";

//				System.out.print(": returnPayData xml=" + requestXML);
				returnsignmap.addAttribute("sign", returnsign);
				return returnsignmap;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	return null;
	}
	
	
	/**
	 * 解析微信返回的XML
	 * 
	 * @param request
	 * @return map 返回数据存放在map里面
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, Object> parseXml(HttpServletRequest request) throws IOException, DocumentException
	{
		// 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap<String, Object>();
  
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
  
        // 遍历所有子节点
        for (Element e : elementList)
        {
        	map.put(e.getName(), e.getText());
        }
  
        // 释放资源
        inputStream.close();
        inputStream = null;
		
		return map;
	}
	
	
	/**
	 * 向微信服务器返回信息，表明已收到微信服务器的信息
	 * 
	 * @param response
	 */
	public void responeToWXService(HttpServletResponse response)
	{
		
		String returnXML = "<xml>" 
							+ "return_code><![CDATA[SUCCESS]]></return_code>"
							+ "<return_msg><![CDATA[OK]]></return_msg>"
							+ "</xml>";
		try 
		{
			byte[] xmlData = returnXML.getBytes();

			response.setContentType("text/xml");
			response.setContentLength(xmlData.length);

			ServletOutputStream os = response.getOutputStream();
			os.write(xmlData);

			os.flush();
			os.close();
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
	}
	
}
