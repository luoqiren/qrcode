package com.lqr.sevlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lqr.util.QrCodeCreateUtilWithLogo;

/**
 * Servlet implementation class QrCodeSevlet
 */
public class QrCodeSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QrCodeSevlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡlogo·��
    	URL logoFileUrl = QrCodeCreateUtilWithLogo.class.getClassLoader().getResource("logo.jpg");
    	System.out.println("logoFileUrl:"+logoFileUrl.getPath());
    	System.out.println("URLDecoder:"+URLDecoder.decode(logoFileUrl.getPath(),"UTF-8"));
    	File logoFile = null;
    	if(logoFileUrl != null) {
    		logoFile = new File(URLDecoder.decode(logoFileUrl.getPath(),"UTF-8" ));
    	}
    	System.out.println("logoFile:"+logoFile.exists());
		
		ByteArrayOutputStream out = QrCodeCreateUtilWithLogo.encodeDrawLogoQRCode(logoFile, "Test2", "https://www.baidu.com/");
		
		System.out.println("ByteArrayOutputStream:"+out.toString());
		
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("image/png;charset=UTF-8");
	    response.setContentLength(out.size());
	    ServletOutputStream outputStream = response.getOutputStream();
	    outputStream.write(out.toByteArray());
	    outputStream.flush();
	    outputStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
