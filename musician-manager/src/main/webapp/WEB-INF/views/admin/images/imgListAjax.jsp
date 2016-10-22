<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>

<table rules="none"  class="image-table">
   <tr class="tb_title">
     <td  align="center" width="60px">编号</td>
     <td  align="center" width="90px">显示排序</td>
     <td  align="center" width="200px">备注</td>
     <td  align="center" width="150px">图片</td>
     <td  align="center" width="300px">链接</td>
     <td  align="center" width="200px">操作</td>
  </tr>
  <c:forEach var="list" items="${data }">
	 <tr height="200px" class="tb-img-content">
	     <td align="center" width="80px">${list.id }</td>
	     <td align="center" width="80px">${list.sequence }</td>
	     <td align="center" width="200px">${list.alt }</td>
	     <td align="center" width="150px" height="200px" ><img style="width:100px;height:100px" src="${list.file }" /></td>
	     <td align="center" width="300px">${list.link }</td>
		 <td align="center" width="200px">
			<c:if test="${list.state=='0' }">
				<input class="sj_btn" type="button" onclick="frozeenImg(${list.id },'1')" value="冻结" />
			</c:if>
			<c:if test="${list.state=='1' }">
				<input class="sj_btn" type="button" onclick="frozeenImg(${list.id },'0')" value="显示" />
			</c:if>
			<input class="bj_btn" type="button" onclick="editImg(${list.id })" value="编辑" />
			<input class="del_btn" type="button" onclick="delImg(${list.id })" value="删除" />
		</td>
	  </tr>
  </c:forEach>
  <tr height="30px" class="">
    
  </tr>
</table>

 <div class="">
     <input type="hidden" id="num" name="num" value="${toatl}">
          共${num}条记录，当前${page}页 
     <a href="javascript:turnPage(-1)">上一页</a> <a href="javascript:turnPage(0)">下一页</a> 跳至&nbsp;<input type="text" name="page" id="page" class="" size="10" style="width:50px;margin-bottom: 0px" value=""/>&nbsp;<input type="button" class="btn" value="跳" onclick="skipPage()">
 </div>

