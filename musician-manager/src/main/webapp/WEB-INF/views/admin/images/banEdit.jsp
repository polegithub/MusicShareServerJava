<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<form action="${pageContext.request.contextPath}/admin/ad/add" name="dataForm" method="post" enctype="">
	<table rules="none"  width="1000px" height="" border="0" style="margin:0 auto;margin-top:10px;margin-left:100px;">
	  <tr style="color:#0193ff;height:50px">
	     <td height="" align="right">排序：</td>
	     <td height="" align="left"> <input type="text" name="sequence1" id="sequence1" value="1"/> </td>
	     <td height="" align="right" width="100px">文件：</td>
	     <td height="" align="left"><input type="file" name="file1" id="file1" value=""/></td> 
	  </tr>
	  
	   <tr style="color:#0193ff;height:50px">
	     <td height="" align="right" width="100px">图片链接 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="link1" id="link1" value="" style="width: 500px;" size="500" maxlength="500"/>
	     </td>
	     <td height="" align="right" width="100px">图片提示 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="alt1" id="alt1" value="" style="width: 150px;" size="50" maxlength="50"/>
	     </td> 
	  </tr>
	  
	   <tr style="color:#0193ff;height:50px">
	     <td height="" align="right">排序：</td>
	     <td height="" align="left"> <input type="text" name="sequence2" id="sequence2" value="2"/> </td>
	     <td height="" align="right" width="100px">文件：</td>
	     <td height="" align="left"><input type="file" name="file2" id="file2" value=""/></td> 
	  </tr>
	  
	   <tr style="color:#0193ff;height:50px">
	     <td height="" align="right" width="100px">图片链接 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="link2" id="link2" value="" style="width: 500px;" size="500" maxlength="500"/>
	     </td>
	     <td height="" align="right" width="100px">图片提示 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="alt2" id="alt2" value="" style="width: 150px;" size="50" maxlength="50"/>
	     </td> 
	  </tr>
	  
	   <tr style="color:#0193ff;height:50px">
	     <td height="" align="right">排序：</td>
	     <td height="" align="left"> <input type="text" name="sequence3" id="sequence3" value="3"/> </td>
	     <td height="" align="right" width="100px">文件：</td>
	     <td height="" align="left"><input type="file" name="file3" id="file3" value=""/></td> 
	  </tr>
	  
	   <tr style="color:#0193ff;height:50px">
	     <td height="" align="right" width="100px">图片链接 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="link3" id="link3" value="" style="width: 500px;" size="500" maxlength="500"/>
	     </td>
	     <td height="" align="right" width="100px">图片提示 ：</td>
	     <td height="" align="left" colspan="3">
	     	<input type="text" name="alt3" id="alt3" value="" style="width: 150px;" size="50" maxlength="50"/>
	     </td> 
	  </tr>
	   
	  <tr height="50px" class="">
	     <td height="" align="center" width="200" colspan="4">
	     	<a  href="javascript:checkAddWel()" class="button" >确  认 </a>
	     	<a  href="javascript:document.dataForm.reset();" class="button" >重 置 </a>
	     </td>
	  </tr>
	  
	   <tr height="30px" class="">
	     <td height="" align="center" width="" colspan="4"></td>
	  </tr>
	  
	</table>
	
</form>	