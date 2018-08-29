package com.lqr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lqr.util.QrCodeCreateUtilWithLogo;

@Controller
@RequestMapping("/qrCode")
public class ShowQRCodeController {

	@RequestMapping("/show1")
	public void createQRcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("show1111");
		ByteArrayOutputStream out = QrCodeCreateUtilWithLogo.encodeDrawLogoQRCode(null, "Test",
				"https://www.baidu.com/");
		// 告诉所有浏览器不要缓存
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("image/jpeg;charset=UTF-8");
		response.setContentLength(out.size());
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(out.toByteArray());
		outputStream.flush();
		outputStream.close();
	}

	@RequestMapping("/show")
	public String showQrCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("show");
		return "showQRCode";
	}

	@RequestMapping("/show3")
	public void showQrCode3(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keycode = "100000000";
		ServletOutputStream stream = null;
		try {
			int size = 300;
			String msize = null;
			if (msize != null && !"".equals(msize.trim())) {
				try {
					size = Integer.valueOf(msize);
				} catch (NumberFormatException e) {
				}
			}
			stream = response.getOutputStream();
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix m = writer.encode(keycode, BarcodeFormat.QR_CODE, size, size);
			MatrixToImageWriter.writeToStream(m, "JPEG", stream);
		} catch (WriterException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				stream.flush();
				stream.close();
			}
		}
	}
}
