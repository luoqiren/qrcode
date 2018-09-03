package com.lqr.sevlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
		//»ñÈ¡logoÂ·¾¶
    	URL logoFileUrl = QrCodeCreateUtilWithLogo.class.getClassLoader().getResource("logo.jpg");
    	File logoFile = null;
    	if(logoFileUrl != null) {
    		logoFile = new File(logoFileUrl.getPath());
    	}
		
		ByteArrayOutputStream out = QrCodeCreateUtilWithLogo.encodeDrawLogoQRCode(logoFile, "Test2", "https://www.baidu.com/");
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
