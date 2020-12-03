$(function() {
	var bindUrl = '/o2o/shop_admin/bindlocalauth';

	$('#submit').click(function() {
		var userName = $('#username').val();
		var password = $('#psw').val();
		var verifyCode = $('#j_captcha').val();
		var needVerify = false;
		if (!verifyCode) {
			$.toast('请输入验证码！');
			return;
		}
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCode : verifyCode
			},
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功！');
					window.location.href = '/o2o/shop_admin/shop_list';
				} else {
					$.toast('绑定失败！');
					$('#captcha_img').click();
				}
			}
		});
	});
});