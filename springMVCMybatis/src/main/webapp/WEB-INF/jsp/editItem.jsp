<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品信息</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	function save(){
	    var item = {"id":1,"name":"台式机"};
	    alert(JSON.stringify(item));
	    $.ajax({
			url:"${pageContext.request.contextPath}/updateItemAjax.action",
			type:"post",
			data:JSON.stringify(item),
			contentType:"application/json;charset=UTF-8",
			dataType:"json",
			success:function(data) {
                //data :{"success":true|false,"message":"操作成功"|“操作失败”}
				if(data.success){
					location.href = "${pageContext.request.contextPath }/list.action"
				}else{
                    alert(data.message);
                }
            }
		});
	}

    /**
     * springmvc 接收ajax提交的json字符串，里面包含多个对象
     * 后台通过List接收
     */
	function requestBodyTest(){
	    var item1 = {"id":1,"name":"台式机"};
        var item2 = {"id":2,"name":"台式机"};
        var item3 = {"id":3,"name":"台式机"};
        var items = [];
        items.push(item1);
        items.push(item2);
        items.push(item3);
        $.ajax({
            url:'${pageContext.request.contextPath}/requestBodyTest.action',
            type:'post',
            data:JSON.stringify(items),
            contentType:'application/json;charset=utf-8',
            dataType:'json',
            success:function(){

            }
        });

    }
</script>
</head>
<body> 
	<!-- 上传图片是需要指定属性 enctype="multipart/form-data" -->
	<!-- <form id="itemForm" action="" method="post" enctype="multipart/form-data"> -->
	<form id="itemForm"	action="${pageContext.request.contextPath }/updateitem.action" method="post" enctype="multipart/form-data">
		<input type="hidden" name="pic" value="${item.pic }" />
		<input type="hidden" name="id" value="${item.id }" /> 修改商品信息：
		<table width="100%" border=1>
			<tr>
				<td>商品名称</td>
				<td><input type="text" name="name" value="${item.name }" /></td>
			</tr>
			<tr>
				<td>商品价格</td>
				<td><input type="text" name="price" value="${item.price }" /></td>
			</tr>
			
			<tr>
				<td>商品生产日期</td>
				<td><input type="text" name="createtime"
					value="<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" /></td>
			</tr>

			<tr>
				<td>商品图片</td>
				<td>
					<c:if test="${item.pic !=null}">
						<img src="/pic/${item.pic}" width=100 height=100/>
						<br/>
					</c:if>
					<!-- 参数名称应该与controller中形参MultipartFile 一致-->
					<input type="file"  name="pictureFile"/> 
				</td>
			</tr>

			<tr>
				<td>商品简介</td>
				<td><textarea rows="3" cols="30" name="detail">${item.detail }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<%--<input type="submit" value="提交" />--%>
					<input type="button" onclick="save()" value="提交" />
                    <input type="button" onclick="requestBodyTest()" value="测试requestBody接收json数组" />
				</td>
			</tr>
		</table>

	</form>
</body>

</html>