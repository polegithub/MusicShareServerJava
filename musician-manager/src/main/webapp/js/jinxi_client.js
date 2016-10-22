var domain = getRootPath() ;

function getRootPath(){
	   var currentPagepath=location.href;
	   var pathName = window.document.location.pathname;
	   var pos = currentPagepath.indexOf(pathName);
	   var localhostPath = currentPagepath.substring(0,pos);
	   var projectName = pathName.substring(0,pathName.substr(1).indexOf("/")+1);
	   return localhostPath+projectName;
}


var callfn = function(call1,call2){
	call1.call(this);
	call2.call(this);
}

var domenu2=function(menu,link,callback2,arg2){
	doMenu.call(this,menu,link);
	callback2.call(this,arg2);
}

var domenu=function(callback1,menu,link,callback2,arg2){
	callback1.call(this,menu,link);
	callback2.call(this,arg2);
}


function subForm(form,callback){
	var subform = $('form[name='+form+']');
	
	var param = {};
	subform.find('input').each(function(){
		param[$(this).attr("name")] = $(this).val();
	});
	
	subform.find('select').each(function(){
		param[$(this).attr("name")] = $(this).val();
	});
	
	subform.find('textarea').each(function(){
		param[$(this).attr("name")] = $(this).val();
	});
	
	$.ajax( {
		type : "post",
		url : subform.attr("action"),
		dataType : 'json',
		data : param,
		success : function(json) {
			if(json.code=="00000"){
				alert("操作成功");
				callback();
			}else{
				alert(json.message);
			}
		}
	});
}


function doFnMenu(menu,link,callback) {
	
	$("#menu-title").text(menu);
	$(".current").text(menu);
	$.ajax({
		type : "post",
		url : link,
		dataType : 'html',
		data : '',
		success : function(html) {
		//	alert(html);
			$("#container-div").html(html);
			callback();
		}
	});
	
}

function doMenu(menu, link) {
	
	$("#menu-title").text(menu);
	$(".current").text(menu);
	$.ajax({
		type : "post",
		url : link,
		dataType : 'html',
		data : '',
		success : function(html) {
		//	alert(html);
			$("#container-div").html(html);
		}
	});
}

function approve(recordId,proName) {
	$('#fade').show();
	$('#light').show();
	$('#proId').val(recordId);
	$('#sproId').text(proName);
	
	$('#fade').click(function(){
		$('#fade').hide();
		$('#light').hide();
	});
}


var proListPage = 1;
var proListPageSize = 25;
function listWelImgAjax() {
	var state = $("#state").val();
		$.ajax( {
			type : "post",
			url : domain+"/admin/home/welcome/list",
			dataType : 'html',
			data : 'state='+state+'&page='+proListPage+'&pageSize='+proListPageSize,
			success : function(html) {
				document.getElementById("resultDiv").innerHTML=html;
			}
		});
}

function turnPropage(index){
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
	listWelImgAjax();
}

function skipProPage(){
	var index_ = document.getElementById("page").value;
	if(index_==""){
		return;
	}
	turnPropage(index_);
}

function delBananer(recordId) {
	if(!confirm("确认删除该图片吗?")){
		return;
	}
	$.ajax( {
		type : "post",
		url : domain+"/admin/home/delete",
		dataType : 'json',
		data : 'id='+recordId,
		success : function(json) {
			if(json.code=="0"){
				listWelImgAjax();
			}else{
				alert(json.message);
			}
		}
	});
}


function queryPro(recordId){
	$.ajax( {
		type : "post",
		url : domain+"/admin/project/query",
		dataType : 'html',
		data : 'recordId='+recordId,
		success : function(html) {
			$("#container-div").html(html);
		}
	});
}
function updatePro(){
	var pid = $("#pid").val();
	var name = $("#name").val();
	var builder = $("#builder").val();
	var contact = $("#contact").val();
	var mobile = $("#mobile").val();
	var designer = $("#designer").val();
	var construct = $("#construct").val();
	var inspect = $("#inspect").val();
	
	var vdesigner = $("#showdesigner").text();
	var vconstruct = $("#showconstruct").text();
	var vinspect = $("#showinspect").text();
	
	if(designer==""){
		designer = vdesigner;
	}
	
	if(construct==""){
		construct = vconstruct;
	}
	
	if(inspect==""){
		inspect = vinspect;
	}
	
	if(name==""){
		alert("请输入工程名称");
		return;
	}
	if(builder==""){
		alert("请输入建设单位");
		return;
	}
	if(contact==""){
		alert("请输入联系人");
		return;
	}
	if(mobile==""){
		alert("请输入手机号码");
		return;
	}
	
	if(designer=="" || designer == undefined){
		alert("请输入设计单位");
		return;
	}
	if(construct==""  || construct == undefined){
		alert("请输入施工单位");
		return;
	}
	if(inspect==""  || inspect == undefined){
		alert("请输入检测单位");
		return;
	}
	
	
	$.ajax( {
		type : "post",
		url : domain+"/admin/project/update",
		dataType : 'json',
		data : 'pid='+pid+'&name='+name+'&builder='+builder+'&contact='+contact+'&mobile='+mobile+'&designer='+designer+'&construct='+construct+'&inspect='+inspect,
		success : function(json) {
			if(json.code=="00000"){
				alert("修改成功");
				doMenu("项目管理",domain+"/admin/project/init");
				listWelImgAjax();
			}else{
				alert(json.message);
			}
		}
	});
}
function checkAddWel() {
	var name = $("#name").val();
	var builder = $("#builder").val();
	var contact = $("#contact").val();
	var mobile = $("#mobile").val();
	var designer = $("#designer").val();
	var construct = $("#construct").val();
	var inspect = $("#inspect").val();
	
	var vdesigner = $("#showdesigner").text();
	var vconstruct = $("#showconstruct").text();
	var vinspect = $("#showinspect").text();
	
	if(designer==""){
		designer = vdesigner;
	}
	
	if(construct==""){
		construct = vconstruct;
	}
	
	if(inspect==""){
		inspect = vinspect;
	}
	
	if(name==""){
		alert("请输入工程名称");
		return;
	}
	if(builder==""){
		alert("请输入建设单位");
		return;
	}
	if(contact==""){
		alert("请输入联系人");
		return;
	}
	if(mobile==""){
		alert("请输入手机号码");
		return;
	}
	if(designer=="" || designer == undefined){
		alert("请输入设计单位");
		return;
	}
	if(construct==""  || construct == undefined){
		alert("请输入施工单位");
		return;
	}
	if(inspect==""  || inspect == undefined){
		alert("请输入检测单位");
		return;
	}
	
	$.ajax( {
		type : "post",
		url : domain+"/admin/project/add",
		dataType : 'json',
		data : 'name='+name+'&builder='+builder+'&contact='+contact+'&mobile='+mobile+'&designer='+designer+'&construct='+construct+'&inspect='+inspect,
		success : function(json) {
			if(json.code=="00000"){
				alert("添加成功");
				doMenu("项目管理",domain+"/admin/project/init");
				listWelImgAjax();
			}else{
				alert(json.message);
			}
		}
	});
}


function frozeenWelImg(recordId,state) {
	$.ajax( {
		type : "post",
		url : domain+"/admin/home/frozeen",
		dataType : 'json',
		data : 'id='+recordId+'&state='+state,
		success : function(json) {
			if(json.code=="0"){
				listWelImgAjax();
			}else{
				alert(json.message);
			}
		}
	});
}



function updateAdminSecret(){
	var cstid = $("#cstid").val();
	var soldsecret = $("#soldsecret").val();
	var oldsecret = $("#oldsecret").val();
	var secret = $("#secret").val();
	var ssecret = $("#ssecret").val();
	if(oldsecret==""){
		alert("请输入旧密码");
		return;
	}
	if(soldsecret != oldsecret){
		alert("旧密码输入错误");
		return;
	}
	if(secret==""){
		alert("请输入新密码");
		return;
	}
	if(ssecret==""){
		alert("请确认新密码");
		return;
	}
	
	if(ssecret!=secret){
		alert("密码和确认密码不一致");
		return;
	}
	
	$.ajax( {
		type : "post",
		url : domain+"/admin/project/admin/update",
		dataType : 'json',
		data : 'cstid='+cstid+'&secret='+secret+'&ssecret='+ssecret+'&oldsecret='+oldsecret,
		success : function(json) {
			if(json.code=="00000"){
				alert("修改成功");
				doMenu("密码管理",domain+"/admin/menu/adminInfo");
			}else{
				alert(json.message);
			}
		}
	});
	
}


function checkAddAdmin(){
	
	var username = $("#username").val();
	var name = $("#name").val();
	var secret = $("#secret").val();
	var ssecret = $("#ssecret").val();
	var department = $("#department").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var email = $("#email").val();
	
	
	if(username==""){
		alert("请输入登陆用户名");
		return;
	}
	if(name==""){
		alert("请输入姓名");
		return;
	}
	if(secret==""){
		alert("请输入登陆密码");
		return;
	}
	if(ssecret==""){
		alert("请输入确认密码");
		return;
	}
	if(secret!=ssecret){
		alert("登陆密码和确认密码不一致");
		return;
	}
	
	$.ajax( {
		type : "post",
		url : domain+"/admin/user/add",
		dataType : 'json',
		data : '&username='+username+'&name='+name+'&secret='+secret+'&ssecret='+ssecret+'&department='+department+'&phone='+phone+'&address='+address+'&email='+email,
		success : function(json) {
			if(json.code=="00000"){
				alert("添加成功");
				doMenu("系统用户管理",domain+"/admin/project/init");
			}else{
				alert(json.message);
			}
		}
	});
	
}

var adminListPage = 1;
var adminListPageSize = 25;
function listAdminAjax() {
	var username = $("#username").val();
	var name = $("#name").val();
		$.ajax( {
			type : "post",
			url : domain+"/admin/user/list",
			dataType : 'html',
			data : 'username='+username+'&name='+name+'&page='+adminListPage+'&pageSize='+adminListPageSize,
			success : function(html) {
				document.getElementById("resultDiv").innerHTML=html;
			}
		});
}

function turnAdminPage(index){
	var num = document.getElementById("num").value;
	if(index == 0){
		if(adminListPage<num){
			adminListPage = adminListPage+1;
		}else{
			adminListPage = num;
		}
	}else if(index == -1){
		if(adminListPage>1){
			adminListPage = adminListPage-1;
		}else{
			adminListPage = 1;
		}
	}else{
		adminListPage = index;
	}
	listAdminAjax();
}

function skipAdminPage(){
	var index_ = document.getElementById("page").value;
	if(index_==""){
		return;
	}
	turnAdminPage(index_);
}


function deleteAdmin(recordId) {
	if(!confirm("确认删除该管理员吗?")){
		return;
	}
	$.ajax( {
		type : "post",
		url : domain+"/admin/user/delete",
		dataType : 'json',
		data : 'recordId='+recordId,
		success : function(json) {
			if(json.code=="00000"){
				listAdminAjax();
			}else{
				alert(json.message);
			}
		}
	});
}

function frozeenAdmin(recordId,state) {
	$.ajax( {
		type : "post",
		url : domain+"/admin/user/frozeen",
		dataType : 'json',
		data : 'recordId='+recordId+'&state='+state,
		success : function(json) {
			if(json.code=="00000"){
				listAdminAjax();
			}else{
				alert(json.message);
			}
		}
	});
}






