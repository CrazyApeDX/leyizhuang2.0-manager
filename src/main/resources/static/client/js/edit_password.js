//angulrjs初始化模块
var app = angular.module("editPwd", []);
// angularjs初始化控制器
var ctrl = app.controller("ctrl", function($scope, $http, $location) {
	// 创建临时变量用于存储几个密码
	$scope.param = {
		old_password : "",
		new_password : "",
		re_password : ""
	}
	// 提交方法（提交表单修改密码）
	$scope.submitForm = function() {
		if (this.param.re_password != this.param.new_password) {
			warning("亲，您两次输入的密码不一致");
			return;
		}
		// 开启等待图标
		wait();
		// 发送异步请求
		$.ajax({
			url : "/user/edit/password/save",
			type : "post",
			timeout : 10000,
			data : {
				oldPassword : this.param.old_password,
				newPassword : this.param.new_password
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				close(1);
				warning("亲，您的网速不给力啊");
				return;
			},
			success : function(res) {
				// 未登陆的情况下直接跳转到登陆页面
				if (-2 == res.status) {
					window.location.href = "/login";
					return;
				}

				// 关闭等待图标
				close(1);
				if (-1 == res.status) {
					warning(res.message);
					return;
				}

				warning("密码修改成功");
				setTimeout(function() {
					window.location.href = "/user/info";
				}, 1000);
			}
		});
	}
});
