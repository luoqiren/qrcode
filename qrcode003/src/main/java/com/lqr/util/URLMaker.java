package com.lqr.util;

import javax.servlet.http.HttpServletRequest;

public class URLMaker {

	public static String makeURLByHttpSevletRequest(HttpServletRequest request, String newActionName) {
		String servletPath = request.getServletPath();
		String serverName = request.getServerName();
		String contextPath = request.getContextPath();
		int serverPort = request.getServerPort();
		
		String urlResult = "http://" + serverName + ":" + serverPort + contextPath
				+ servletPath.substring(0, servletPath.lastIndexOf("/")) + "/" + newActionName;
		return urlResult;
	}

	public static void main(String[] args) {
		/*
		 * http://localhost:8080/qrcode003/qrCodeDo/showMyQrCode
		 * 
		 * getContextPath:/qrcode003 getServletPath:/qrCodeDo/writeMyQrCode
		 * getServerName:localhost getServerPort:8080
		 */

		String getServerName = "localhost";
		String getContextPath = "/qrcode003";
		String getServletPath = "/qrCodeDo/writeMyQrCode";
		String getServerPort = "8080";
		String newString = "whoScanMes";
		String urlResult = "http://" + getServerName + ":" + getServerPort + getContextPath
				+ getServletPath.substring(0, getServletPath.lastIndexOf("/")) + "/" + newString;

		System.out.println(urlResult);

	}

}
