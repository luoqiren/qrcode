package com.lqr.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 画制定logo和制定描述的二维码
 * 
 * 这个比较好用
 * 解决文字描述换行问题
 */
public class QrCodeCreateUtilWithLogo {
    private static final int QRCOLOR = 0x00000000; // 默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色

    private static int QR_WIDTH = 300; // 二维码宽
    private static int QR_HEIGHT = 300; // 二维码高

    private static int NOTE_EACH_LINE_HEIGTH = 50; //中文描述每行的高度
    private static int NOTE_WIDTH = QR_WIDTH;//中文描述的宽度， 默认与二维码宽度一致
    private static int NOTE_HEIGHT = QR_HEIGHT + NOTE_EACH_LINE_HEIGTH;//中文描述的高度， 在二维码高度基础上加50
    private static int NOTE_BEGIN_POSITION = 10 ;//中文描述的开始位置，横坐标
    // 用于设置QR二维码参数
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;
        {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
            put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
            put(EncodeHintType.MARGIN, 1);
        }
    };
    /**
     * 生成带logo的二维码图片 
     * @param logoFile LOGO缩略图, 如果不需要请传空
     * @param note 对二维码的描述, 如果不需要请穿空
     * @param qrUrl 要访问的web链接
     * @return image 返回二维码的缓冲图片
     */
    public static BufferedImage drawLogoQRCode(File logoFile, String note, String qrUrl) {
    	BufferedImage image = null;
    	try {
    		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    		// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
    		BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
    		image = new BufferedImage(QR_WIDTH, QR_HEIGHT, BufferedImage.TYPE_INT_RGB);
            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < QR_WIDTH; x++) {
                for (int y = 0; y < QR_HEIGHT; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
            //二维码居中的小图片
            if (Objects.nonNull(logoFile) && logoFile.exists()) {
                // 构建绘图对象
                Graphics2D g = image.createGraphics();
                // 读取Logo图片
                BufferedImage logo = ImageIO.read(logoFile);
                // 开始绘制logo图片
                g.drawImage(logo, QR_WIDTH * 2 / 5, QR_HEIGHT * 2 / 5, QR_WIDTH * 2 / 10, QR_HEIGHT * 2 / 10, null);
                g.dispose();
                logo.flush();
            }
            // 自定义文本描述
            if (StringUtils.isNotEmpty(note)) {
                // 新的图片，把带logo的二维码下面加上文字
                BufferedImage outImage = new BufferedImage(NOTE_WIDTH, NOTE_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                // 画二维码到新的面板
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                // 画文字到新的面板
                outg.setColor(Color.BLACK);
                outg.setFont(new Font("楷体", Font.BOLD, 30)); // 字体、字型、字号
                int strWidth = outg.getFontMetrics().stringWidth(note);//为了拿到像素长度
                //文字长度换行模式
                if (strWidth > NOTE_WIDTH) {
                    // //长度过长就截取前面部分
                	int lines = strWidth/NOTE_WIDTH+1; //获取到要转换的行数
                	int noteCurrentPosition = 0;
                	int noteLinesLength = note.length()/lines;
                	String cutNote = "";
                	BufferedImage outImage2 = null;
                	for(int i=0; i<=lines && noteCurrentPosition<note.length(); i++) {
                		if(noteCurrentPosition + noteLinesLength >= note.length()) {
                			cutNote = note.substring(noteCurrentPosition, note.length());
                		}else {
                			cutNote = note.substring(noteCurrentPosition, noteCurrentPosition + noteLinesLength);
                		}
                		noteCurrentPosition = noteCurrentPosition + noteLinesLength;
                		
                		if(i==0) {
                			outg.drawString(cutNote, NOTE_BEGIN_POSITION, QR_HEIGHT+(NOTE_HEIGHT-QR_HEIGHT) / 2 + 5);
                			NOTE_HEIGHT = NOTE_HEIGHT + NOTE_EACH_LINE_HEIGTH;
                		}else {
                			outImage2 = new BufferedImage(NOTE_WIDTH, NOTE_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
                			Graphics2D outg2 = outImage2.createGraphics();
                			outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                			outg2.setColor(Color.BLACK);
                			outg2.setFont(new Font("楷体", Font.BOLD, 30)); // 字体、字型、字号
                			outg2.drawString(cutNote, NOTE_BEGIN_POSITION, QR_HEIGHT+(NOTE_HEIGHT-QR_HEIGHT) / 2 + 5);
                			outg2.dispose();
                			outImage2.flush();
                			System.out.println("NOTE_HEIGHT:"+NOTE_HEIGHT + " _ "+cutNote);
                			NOTE_HEIGHT = NOTE_HEIGHT + NOTE_EACH_LINE_HEIGTH;
                			outImage = outImage2==null?outImage:outImage2;
                		}
                	}
                } else {
                    outg.drawString(note, NOTE_BEGIN_POSITION, QR_HEIGHT+(NOTE_HEIGHT-QR_HEIGHT)/2 + 12); // 画文字
                }
                outg.dispose();
                outImage.flush();
                image = outImage;
            }

            image.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	return image;
    }
    
    
    /**20180829
     * 获取生成二维码的图片流 用以返回前端页面
     * @param logoFile
     * @param note
     * @param qrUrl
     * @return
     * @throws IOException 
     */
    public static ByteArrayOutputStream encodeDrawLogoQRCode(File logoFile, String note, String qrUrl) throws IOException  {
        BufferedImage image = QrCodeCreateUtilWithLogo.drawLogoQRCode(logoFile, note, qrUrl);
        //创建储存图片二进制流的输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //将二进制数据写入ByteArrayOutputStream
        ImageIO.write(image, "png", baos);
        return baos;
    }
    
    /**
     * 生成本地带logo的二维码图片 
     * @param logoFile
     * @param qrCodeFile
     * @param qrUrl
     * @param note
     */
    public static void drawLogoQRCodeToLocalFile(File logoFile, File qrCodeFile, String qrUrl, String note) {
    	BufferedImage image = drawLogoQRCode(logoFile, note, qrUrl);
    	try {
			ImageIO.write(image, "png", qrCodeFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    public static void main(String[] args) throws WriterException {
    	//获取logo路径
    	URL logoFileUrl = QrCodeCreateUtilWithLogo.class.getClassLoader().getResource("logo.jpg");
    	File logoFile = null;
    	if(logoFileUrl != null) {
    		System.out.println(logoFileUrl.getPath());
    		logoFile = new File(logoFileUrl.getPath());
    	}
    	
        File QrCodeFile = new File("D:\\06.png");
        String url = "https://www.baidu.com/";
        String note = "访问百度连接很长很长的文字描述怎么办呢？你说要换几行来测试呀？？等等等的等等等";
        drawLogoQRCodeToLocalFile(logoFile, QrCodeFile, url, note);
    }
}