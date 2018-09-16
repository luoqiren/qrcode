package com.lqr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lqr.util.QrCodeCreateUtilWithLogo;
import com.lqr.util.URLMaker;

@Controller
@RequestMapping("/qrCodeDo")
public class ShowQRCodeAndDostController {
	
	@RequestMapping("/whoScanMes")
	public void readWhoScanMe(HttpServletRequest request, HttpServletResponse response) {
		String whoAmIAgent = request.getHeader("user-agent") ;//判断是否是微信、或者支付宝支付
		System.out.println("whoAmIAgent : "+whoAmIAgent);
		if(whoAmIAgent.toLowerCase().contains("micromessenger")){//微信
			System.out.println("I am 微信");
		}else if(whoAmIAgent.toLowerCase().contains("alipayclient")){//支付宝
			System.out.println("I am 支付宝");
		}else {
			System.out.println("I am unkonw");
		}
	}
	
	@RequestMapping("/writeMyQrCode")
	public void writeQrCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String targetUrl = URLMaker.makeURLByHttpSevletRequest(request, "whoScanMes");
		System.out.println("targetUrl:"+targetUrl);
		ByteArrayOutputStream out = QrCodeCreateUtilWithLogo.encodeDrawLogoQRCode(null, null, targetUrl);
		// 告诉所有浏览器不要缓存
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("image/png;charset=UTF-8");
		response.setContentLength(out.size());
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(out.toByteArray());
		outputStream.flush();
		outputStream.close();
	}
	
	@RequestMapping("/showMyQrCode")
	public String showQrCodePage() {
		System.out.println("showMyQrCode");
		return "showMyQrCode";
	}
}
