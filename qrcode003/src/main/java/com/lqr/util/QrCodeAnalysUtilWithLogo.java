package com.lqr.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QrCodeAnalysUtilWithLogo {

		
	
	public static String readQrCodeFile(String qrCodePath) {
		String qrCodeText = "";
		File qrCodeFile = new File(qrCodePath);
		if (qrCodeFile.exists()) {
			try {
				BufferedImage image = ImageIO.read(qrCodeFile);
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				Binarizer binarizer = new HybridBinarizer(source);
				BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer); 
				Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>(); 
				hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
				Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
				System.out.println(result.getText());
			} catch (NotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		return qrCodeText;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		String filePath = "D://07.jpg";
		
		System.out.println(readQrCodeFile(filePath));
	}

}
