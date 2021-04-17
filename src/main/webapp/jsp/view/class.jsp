<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="${path}/static/css/file.css" rel='stylesheet' type='text/css'/>
    <title>照片</title>
</head>
<body>
<table>
    <tr>
        <th></th>
        <th>文件名</th>
        <th>文件类型</th>
        <th>大小</th>
        <th>上传时间</th>
        <th>删除</th>
    </tr>
    <%--@elvariable id="fileList" type="java.util.List"--%>
    <c:forEach var="list" items="${fileList }"><%--@elvariable id="folderId" type=""--%>
        <tr>
            <td><a href="${path}/file/download?file=${list.id }&folder=${folderId}">
                <img src="${path}/static/images/download.png" alt="download"/></a></td>
            <td>${list.name}</td>
            <td>${list.fileType }</td>
            <td>${list.size } KB</td>
            <td>${list.uploadTime }</td>
            <td><a href="${path}/file/delete?file=${list.id }&folder=${folderId}">
                <img src="${path}/static/images/delete.png" alt="delete"/></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>