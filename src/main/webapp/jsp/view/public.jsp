<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="${path}/static/css/file.css" rel='stylesheet' type='text/css'/>
    <title>文件分享区</title>
</head>
<body>
<table>
    <tr>
        <th></th>
        <th>文件名</th>
        <th>文件类型</th>
        <th>大小</th>
        <th>上传时间</th>
        <th>上传用户</th>
    </tr>
    <%--@elvariable id="fileList" type="java.util.List"--%>
    <c:forEach items="${fileList} " var="c" >
        <tr>
            <td><a href="${path}/file/download?file=${c.id}&folder=${c.fatherFolder.id}">
                <img src="${path}/static/images/download.png" alt="download"/></a></td>
            <td>${c.name }</td>
            <td>${c.fileType }</td>
            <td>${c.size } KB</td>
            <td>${c.uploadTime }</td>
            <td>${c.uploadUser.username }</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>