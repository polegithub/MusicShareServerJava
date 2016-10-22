<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导航管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Iframe.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jinxi.css" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css" type="text/css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" language="javascript">
	
var proListPage = 1;
var proListPageSize = 25;

$(function(){
	
	listImgAjax();
	
});

function listImgAjax() {
	var state = $("#state").val();
		$.ajax( {
			type : "post",
			url : "${pageContext.request.contextPath}/admin/home/welcome/list",
			dataType : 'html',
			data : 'state='+state+'&page='+proListPage+'&pageSize='+proListPageSize,
			success : function(html) {
				document.getElementById("resultDiv").innerHTML=html;
			}
	});
}

function turnPage(index){
	var num = document.getElementById("num").value;
	if(index == 0){
		if(proListPage<num){
			proListPage = proListPage+1;
		}else{
			proListPage = num;
		}
	}else if(index == -1){
		if(proListPage>1){
			proListPage = proListPage-1;
		}else{
			proListPage = 1;
		}
	}else{
		proListPage = index;
	}
	listImgAjax();
}

function skipPage(){
	var index_ = document.getElementById("page").value;
	if(index_==""){
		return;
	}
	turnPage(index_);
}

function delImg(recordId) {
	if(!confirm("确认删除该图片吗?")){
		return;
	}
	$.ajax( {
		type : "post",
		url : "${pageContext.request.contextPath}/admin/home/delete",
		dataType : 'json',
		data : 'id='+recordId,
		success : function(json) {
			if(json.code=="0"){
				listImgAjax();
			}else{
				alert(json.message);
			}
		}
	});
}


function frozeenImg(recordId,state) {
	$.ajax( {
		type : "post",
		url : "${pageContext.request.contextPath}/admin/home/frozeen",
		dataType : 'json',
		data : 'id='+recordId+'&state='+state,
		success : function(json) {
			if(json.code=="0"){
				listImgAjax();
			}else{
				alert(json.message);
			}
		}
	});
}

function editImg(id){
	var url = "${pageContext.request.contextPath}/admin/home/image/query?id="+id;
	location.href = url;
}

</script>

</head>

<body>
	<span class="cp_title">导航管理</span>
	<div class="add_cp">
		<a href="${pageContext.request.contextPath}/page/admin/images/welAdd">+添加新导航图片</a><a href="${pageContext.request.contextPath}/admin/home/welcome/addPage">+添加新导航图片</a>
	</div>
	<table  border="0" style="" width="100%">
	   <tr align="center">
	    <td  align="center"  >查询条件:
			 状态: <select name="state" id="state" onchange="listImgAjax()">
			 			<option value="0" selected="selected">当前显示</option>
			 			<option value="1">已过期</option>
			 			<option value="">全部</option>
			 	  </select>
			<input type="button" class="btn" value="搜素" onclick="listImgAjax()"/>
		</td>
	  </tr>
	</table>
	<div class="table_con" id="resultDiv">
		
	</div>
</body>
</html>
