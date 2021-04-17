<%--@elvariable id="folderId" type=""--%>
<%--@elvariable id="sysMsg" type=""--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="${path}/static/css/file.css" rel='stylesheet' type='text/css'/>
    <link href="${path}/static/css/buttons.css" rel='stylesheet' type='text/css'/>
    <title>文件目录</title>
</head>
<body>
<script>
    var sysMsg = "${sysMsg}";
    if (sysMsg !== "")
        alert(sysMsg);
</script>

<table>
    <tr>
        <th></th>
        <th>文件名</th>
        <th>文件类型</th>
        <th>大小</th>
        <th>上传时间</th>
        <th>权限</th>
        <th>删除</th>
    </tr>
    <%--@elvariable id="fatherFolder" type="org.springframework.data.elasticsearch.core.EntityOperations.Entity"--%>
    <c:if test="${fatherFolder.id == null }">
        <tr>
            <td>
                <a href="${path}/view/file?dir=${fatherFolder.id }">
                    <img src="${path}/static/images/folder.png" alt="folder"/></a>
            </td>
            <td>上级文件夹</td>
            <td>文件夹</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </c:if>
    <%--@elvariable id="folderList" type="java.util.List"--%>
    <c:forEach var="list" items="${folderList }">
    <tr>
        <td><a href="${path}/view/file?dir=${list.id}"><img src="${path}/static/images/folder.png" alt="folder"/></a>
        </td>
        <td>${list.name }</td>
        <td>文件夹</td>
        <td></td>
        <td></td>
        <td></td>
        <td>
            <a href="${path}/file/deleteDir?folder=${list.id}&fatherFolder=${fatherFolder.id}">
                <img src="${path}/static/images/delete.png" alt="delete"/>
            </a>
        </td>
    <tr>
        </c:forEach>
        <%--@elvariable id="fileList" type="java.util.List"--%>
        <c:forEach var="list" items="${fileList}"><%--@elvariable id="folderId" type=""--%>
    <tr>
        <td><a href="download?file=${list.id }&folder=${folderId}">
            <img src="${path}/static/images/download.png" alt="download"/></a></td>
        <td>${list.name}</td>
        <td>${list.fileType }</td>
        <td>${list.size } KB</td>
        <td>${list.uploadTime }</td>
        <c:if test="${list.status == 0}">
            <td>
                <a href="setAccess?file=${list.id }&folder=${folderId}"><img
                        src="${path}/static/images/private.png" alt="status"/></a>
            </td>
        </c:if>
        <c:if test="${list.status == 1}">
            <td>
                <a href="setAccess?file=${list.id }&folder=${folderId}"><img src="${path}/static/images/public.png"
                                                                             alt="status"/></a>
            </td>
        </c:if>
        <td><a href="delete?file=${list.id }&folder=${folderId}"><img src="${path}/static/images/delete.png"
                                                                      alt="delete"/></a></td>
    </tr>
    </c:forEach>
</table>
<div id="fileForm">
    <div id="upload">
        <br/>
        <h2>文件上传</h2>
        <form class="form-control" action="${path}/file/upload" method="post" enctype="multipart/form-data"
              accept-charset="utf-8">
            选择文件：<input type="file" name="dataList" multiple/><br/>
            <input type="hidden" name="path" value="${folderId}"/>
            <input type="submit" value="上传" class="button button-3d button-primary button-rounded"/>
        </form>
    </div>
    <div id="folder">
        <br/>
        <h2>新建文件夹</h2>
        <form class="form-control" action="${path}/file/folderAdd" method="POST" accept-charset="utf-8">
            文件夹名：<input type="text" name="folderName"/><br/>
            <input type="hidden" name="path" value="${folderId}"/>
            <input type="submit" value="新建" class="button button-3d button-primary button-rounded"/>
        </form>
    </div>
</div>
</body>
</html>