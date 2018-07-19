java二维码生成-谷歌（Google.zxing）开源二维码生成的实例及介绍

这里我们使用比特矩阵(位矩阵)的QR码编码在缓冲图片上画出二维码
实例有以下一个传入参数
　OutputStream outputStream, 要存储的文件
　String content, 携带信息的内容
　int qrCodeSize, 图片大小
　String imageFormat 编码
-------------------------------------------------------
1.设置二维码的纠错级别参数
  //设置二维码纠错级别ＭＡＰ
  Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap 
  	= new Hashtable<EncodeHintType, ErrorCorrectionLevel>(); 
  hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // 矫错级别 矫错级别越高,二维码越高清
-------------------------------------------------------
2.创建比特矩阵
 QRCodeWriter qrCodeWriter = new QRCodeWriter(); 
 //创建比特矩阵(位矩阵)的QR码编码的字符串 
 BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap); 
 // 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
 int matrixWidth = byteMatrix.getWidth();
--------------------------------------------------------
3.开始在缓冲图片中画二维码
BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);  
	image.createGraphics();  
	Graphics2D graphics = (Graphics2D) image.getGraphics();  
	graphics.setColor(Color.WHITE);  
	graphics.fillRect(0, 0, matrixWidth, matrixWidth);  
	// 使用比特矩阵画并保存图像
	graphics.setColor(Color.BLACK);  
	for (int i = 0; i < matrixWidth; i++){
	    for (int j = 0; j < matrixWidth; j++){
	        if (byteMatrix.get(i, j)){
	            graphics.fillRect(i, j, 1, 1);  
	        }
	    }
	}
	ImageIO.write(image, imageFormat, outputStream);
-------------------------------------------------------------------



	


