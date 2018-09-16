package com.lqr.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
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

	
	
	
	public static void main(String[] args) throws IOException {
		String filePath = "D://07.jpg";
		BufferedImage image;

		File qrCodeFile = new File(filePath);
		if (qrCodeFile.exists()) {
			try {
				image = ImageIO.read(qrCodeFile);
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				Binarizer binarizer = new HybridBinarizer(source);
				BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer); 
				Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>(); 
				hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
				Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
				System.out.println(result.getText());
//				JSONObject content = JSONObject.parseObject(result.getText());  
//				System.out.println("图片中内容：  ");  
//	            System.out.println("author： " + content.getString("author"));  
//	            System.out.println("zxing：  " + content.getString("zxing"));  
//	            System.out.println("图片中格式：  ");  
//	            System.out.println("encode： " + result.getBarcodeFormat()); 
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
