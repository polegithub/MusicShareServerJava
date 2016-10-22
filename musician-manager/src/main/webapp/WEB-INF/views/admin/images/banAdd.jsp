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
</head>
<body>


<form action="http://upload.qiniu.com/" name="dataForm" method="post" enctype="multipart/form-data">
	<table rules="none"  width="1000px" height="" border="0" style="margin:0 auto;margin-top:10px;margin-left:100px;">
	 
	 
	  <tr style="color:#0193ff;height:50px">
	     <td height="" align="right">排序：</td>
	     <td height="" align="left"> <input type="text" name="sequence" id="sequence" value="1"/> </td>
	     <td height="" align="right" width="100px">文件：</td>
	     <td height="" align="left"><input type="file" name="file" id="file" value=""/></td> 
	  </tr>
	  
	   <tr style="color:#0193ff;height:50px">
	     <td height="" align="right" width="100px">图片链接 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="link" id="link" value="" style="width: 500px;" size="500" maxlength="500"/>
	     </td>
	     <td height="" align="right" width="100px">图片提示 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="alt" id="alt" value="" style="width: 150px;" size="50" maxlength="50"/>
	     </td> 
	  </tr>
	  
	  <tr height="50px" class="">
	     <td height="" align="center" width="200" colspan="4">
	     	<a  href="javascript:checkAddWel()" class="button" >确  认 </a>
	     	<input type="reset" value="重 置" class="button" />
	     </td>
	  </tr>
	  
	   <tr height="30px" class="">
	     <td height="" align="center" width="" colspan="4">
	     	<input name="key" type="hidden" value=""/>
	     	<input name="x:" type="hidden" value="" />
	     	<input name="token" type="hidden" value=""/>
	     	 <input name="crc32" type="hidden" />
  			<input name="accept" type="hidden" />
	     
	     </td>
	  </tr>
	  
	</table>
	
</form>	

</body>

</html>