$(function(){
	
	$('.button_blue').click(function(){
		var username = $('#username').val();
		var password = $('#password').val();
		if(username==""){
			$('#userCue').show();
			$('#userCue').html("<font color='red'><b>×用户名不能为空</b></font>");
			return false;
		}
		if(password==""){
			$('#userCue').show();
			$('#userCue').html("<font color='red'><b>×密码不能为空</b></font>");
			return false;
		}
		
		$('#login_form').submit();
	});
	

	});
