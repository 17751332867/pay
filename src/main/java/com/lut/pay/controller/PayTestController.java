package com.lut.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class PayTestController {
  String APP_ID = "2021000117646089";
  String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDFRhCNbAUGzqF6nQOCAdcvvxbh8Lxf6lm+uXG/zCyXfwbH2GPkpHHkBSW1W21W+Kf0n6ATHts1FEbrf+AWJkbXKMnsM+J7VH0IpR0Oe1qxk2to22/hzSIYmHkUPPS6PFFQt54u6Lnq98u1yEWcr34I3pDyxY6QZlO/NC2UuNQcgh4VeCJP2w0Zoq/Bbx+1Sxxt67IaTYfyxQLlWAHyssMkQPoVYgEqW/hzYCKj+grIH3I6DQGY88WqpY/8vuol5Nr+yR0a3ZTNVJ0xYu2xBpyatkZ8GZJAJt5EKcK9Erp1o6ZvtQJPgImRrMCg49zOFOLZci2fLD/naSXyk+FVBVd3AgMBAAECggEALJhFH6Mek8ZcLJqX82i1G18ILjU8cDZK6VmEhEYEgo69mqmpE0O1V0Vum8u6MOHvHdaD2i6sgRg/W/dBUeB/PR5EXzfvZ8/WoACrS93Ix8G6ky6f4Rx9K7A0FsYc0IjYHPYVNSd498viagqG6f+l7x1ZCPht3Oi039FMyITrtjGx3Y/tRrnUIYwXQiCQUD3jUBSs67nsKJTDPQZiOTMrCvLio4ZAGsxMUwdSMKM8e3gPOBBAYW8kUyv11waEbn5N8Z5Ha9md4Ms5z6XdlsLGlNmK0V2QLSlE/AKrWao3NantxIg7Z1/Z6hhqx04EJrBpXdxjKTgIxGWBelp4JPW3aQKBgQD1CCI0A4EB1PDZz8L4iwyp6nff/O6rkiIwbYV+jBbC9unkHXZFo+KkUDv+iJzEEYztjjuI3QHRTfv8XptOilVOD9NB+YfF/ouQHTcbI1IXRnVEt2UQ6GI24LM36K7cn/MBC20sP2x+wI+r5+kMzXoQXb0M6XUUKDuVeainDTI9MwKBgQDOGql20IXioxcdqyyNK8LV327TriSuivSgtLkJ4RlYE81JYtDD40tCNmWgrXUjNVPVJBg9772rih7fIXEm0DvtFFmYHfKb+xbD3dTz8cz3PnHYZvSn3T8uqshfhU7bQxps6dYIWpEpAWwRqzqD49dr43E8MQVLBAiAWkzMSY4UrQKBgQDZ3Jd8o7jqhgh3wTBbiFaEJULcVqlmxxG6+UX+VFRQmPUwq7ljBeoXqncNQy4PboNdHF92uvpIvQvVQdDYrkpWk9+EA3di5YX1ZuR+LpNoum694EnuFJButq7igPIom9aWETDKbMHsmQ2lDRe7GF71ekMC3fgJKhSAPKFllmga3QKBgDXHtUKgpN7/qY9/VA++RIfkVpn7zeyRq8WngsU2LJPUDvRASbNjwwaTBS/JM1CtoKN0WLMItNhnISkeI9V57rktXzeXUPAss8MGRoQlK1O0wqYyL1MCI3N88u4h7fK+7s1Osx/Y6hnmGhn+MTWrJVIstiZ7sCLj+YfsiC+iXeW9AoGALx3IGDcdOM7hP4wCkH5o8NL27zSD0oKR5pYg5TxfsPudfoCEx5fn4xsSLVBJghFKFjxYjchRHKH+f7+gvL6SEK0RnlACMbSzoymmenHNdARvpdbVy5tiPvg6nEaylqn7FGP3bSPKrCX7+KaP2o3REQR4L+LML3iWH73du203NeI=";
  String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxUYQjWwFBs6hep0DggHXL78W4fC8X+pZvrlxv8wsl38Gx9hj5KRx5AUltVttVvin9J+gEx7bNRRG63/gFiZG1yjJ7DPie1R9CKUdDntasZNraNtv4c0iGJh5FDz0ujxRULeeLui56vfLtchFnK9+CN6Q8sWOkGZTvzQtlLjUHIIeFXgiT9sNGaKvwW8ftUscbeuyGk2H8sUC5VgB8rLDJED6FWIBKlv4c2Aio/oKyB9yOg0BmPPFqqWP/L7qJeTa/skdGt2UzVSdMWLtsQacmrZGfBmSQCbeRCnCvRK6daOmb7UCT4CJkazAoOPczhTi2XItnyw/52kl8pPhVQVXdwIDAQAB";
  String CHARSET = "utf‐8";
  String serverUrl = "https://openapi.alipaydev.com/gateway.do";
  String sign_type="RSA2";//签名算法的类型
  @GetMapping("/alipaytest")
  public void alipaytest(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
    throws IOException {
    AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, APP_ID, APP_PRIVATE_KEY,
      "json", CHARSET, ALIPAY_PUBLIC_KEY, sign_type); //获得初始化的AlipayClient
    AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
//    alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
//    alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");
    alipayRequest.setBizContent("{" +
      " \"out_trade_no\":\"20150320010101002\"," +
      " \"total_amount\":\"88.88\"," +
      " \"subject\":\"Iphone6 16G\"," +
      " \"product_code\":\"QUICK_WAP_PAY\"" +
      " }");
    String form="";
    try {
      form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
  }
}
