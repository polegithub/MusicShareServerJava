<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>音乐人管理系统后台</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" type="text/css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tendina.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>

</head>
<body>
    <!--顶部-->
    <div class="top">
            <div style="float: left"><span style="font-size: 16px;line-height: 45px;padding-left: 20px;color: #fff">音乐人管理系统管理中心</h1></span></div>
            <div id="ad_setting" class="ad_setting">
                <a class="ad_setting_a" href="javascript:; ">${sessionScope.session_admin.name }</a>
                <ul class="dropdown-menu-uu" style="display: none" id="ad_setting_ul">
                    <li class="ad_setting_ul_li"> <a href="javascript:;"><i class="icon-user glyph-icon"></i>个人中心</a> </li>
                    <li class="ad_setting_ul_li"> <a href="javascript:;"><i class="icon-cog glyph-icon"></i>设置</a> </li>
                    <li class="ad_setting_ul_li"> <a href="${pageContext.request.contextPath}/exist"><i class="icon-signout glyph-icon"></i> <span class="font-bold">注销</span> </a> </li>
                </ul>
                <img class="use_xl" src="${pageContext.request.contextPath}/images/right_menu.png" />
            </div>
    </div>
    <!--顶部结束-->
    <!--菜单-->
    <div class="left-menu">
        <ul id="menu">
            <li class="menu-list">
               <a style="cursor:pointer" class="firsta"><i  class="glyph-icon xlcd"></i>图片管理<s class="sz"></s></a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin/home/welcome/init" target="menuFrame"><i class="glyph-icon icon-chevron-right1"></i>导航管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/home/bananer/init" target="menuFrame"><i class="glyph-icon icon-chevron-right2"></i>首页bananer管理</a></li>
                    <li><a href="#" target="menuFrame"><i class="glyph-icon icon-chevron-right3"></i>其他图片</a></li>
                </ul>
            </li>
            <li class="menu-list">
               <a style="cursor:pointer" class="firsta"><i  class="glyph-icon xlcd"></i>统计信息<s class="sz"></s></a>
                <ul>
                    <li><a href="#" target="menuFrame"><i class="glyph-icon icon-chevron-right1"></i>统计用户</a></li>
                    <li><a href="#" target="menuFrame"><i class="glyph-icon icon-chevron-right2"></i>统计商品</a></li>
                    <li><a href="#" target="menuFrame"><i class="glyph-icon icon-chevron-right3"></i>统计业绩</a></li>
                </ul>
            </li>
            <li class="menu-list">
               <a style="cursor:pointer" class="firsta"><i  class="glyph-icon xlcd"></i>系统管理<s class="sz"></s></a>
                <ul>
                    <li><a href="#" target="menuFrame"><i class="glyph-icon icon-chevron-right1"></i>修改密码</a></li>
                    <li><a href="#" target="menuFrame"><i class="glyph-icon icon-chevron-right2"></i>管理员</a></li>
                    <li><a href="#" target="menuFrame"><i class="glyph-icon icon-chevron-right3"></i>权限管理</a></li>
                </ul>
            </li>
        </ul>
    </div>
    
    <!--菜单右边的iframe页面-->
    <div id="right-content" class="right-content">
        <div class="content">
            <div id="page_content">
                <iframe id="menuFrame" name="menuFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
            </div>
        </div>
    </div>
</body>
</html>