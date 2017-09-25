(function(angular) {
	var template = angular.module('template', []);
	/* 登录页面指令 */
	template.directive('login', [function() {
		return {
			scope: {
				mobile: '',
				password: ''
			},
			restrict: 'E',
			templateUrl: '/fitment/html/login.html',
			controller: function($scope) {
				$scope.login = function() {
					
				}
			}
		}
	}]);
	
	/* 商品列表页面指令 */
	template.directive('list', [function() {
		return {
			scope: {
				selected: []
			},
			restrict: 'E',
			templateUrl: '/fitment/html/list.html',
			controller: function($scope) {
				$scope.getGoods = function(categoryId) {
					
				}
				$scope.addCart = function() {
					
				}
			}
		}
	}]);
})(angular)