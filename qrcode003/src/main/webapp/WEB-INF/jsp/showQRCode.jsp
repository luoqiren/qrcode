<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test here</title>
</head>
<body>
<img alt="二维码" src="<c:url value="/qrCode/show4"/>" border="1"/><br/>
<img alt="二维码" src="<c:url value="/qrCode/show3"/>" border="1"/>
</body>
</html>