<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<style type="text/css">
			body {
			    font-family:"Lucida Grande", "Lucida Sans Unicode", Verdana, Arial, Helvetica, sans-serif;
			    font-size:12px;
			    
			}
			p, h1, form, button {
			    border:0;
			    margin:0;
			    padding:0;
			}
			.spacer {
			    clear:both;
			    height:1px;
			}
			.myform {
			    margin:100px auto;
			   
			    width:400px;
			    padding:40px;
			}
			#stylized {
			    border:solid 2px #b7ddf2;
			    background:#ebf4fb;
			}
			#stylized h1 {
			    font-size:14px;
			    font-weight:bold;
			    margin-bottom:8px;
			}
			#stylized p {
			    font-size:11px;
			    color:#666666;
			    margin-bottom:20px;
			    border-bottom:solid 1px #b7ddf2;
			    padding-bottom:10px;
			}
			#stylized label {
			    display:block;
			    font-weight:bold;
			    text-align:right;
			    width:140px;
			    float:left;
			}
			#stylized .small {
			    color:#666666;
			    display:block;
			    font-size:11px;
			    font-weight:normal;
			    text-align:right;
			    width:140px;
			}
			#stylized input {
			    float:left;
			    font-size:10px;
			    padding:0px 0px;
			    border:solid 1px #aacfe4;
			    width:50px;
			    margin:0 auto;
			}
			#stylized button {
			    clear:both;
			    margin-left:150px;
			    width:125px;
			    height:31px;
			    background:#666666 url(images/button.png) no-repeat;
			    text-align:center;
			    line-height:31px;
			    color:#FFFFFF;
			    font-size:11px;
			    font-weight:bold;
			}
			input:focus, button:focus { outline: thick solid #b7ddf2 }
			input:active, button:active  { outline: thick solid #aaa }
			</style>
	</head>
	<body>
		<div id="stylized" class="myform">
		    <form id="form1" name="form1" method="post" action="">
		        
		        <p>提交失败</p>
		        <label>失败代码 ：<span class="small"></span> </label>&nbsp;${code}<br>
		        
		        <label>失败原因 ：<span class="small"></span> </label>&nbsp;${message}<br>
		        
		        <label >&nbsp;<span class="small"></span></label>  <input style="margin:10px auto;" type="button" value="返回"  onclick="history.go(-1)" /> 

		        
		    </form>
		</div>
	</body>
</html>