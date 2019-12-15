
/*$(function(){
	fillPage('/p/userPage')
})*/

function loadToken(app) {
	$.ajax({
        type:"get",
        url: "/token",
        success: function(data){
            if (data.code==401){
                window.location.href='/login';
                return;
            }
            if(data.code === 0){
				app.user=data.token;
            }
            else{
                alert('获取当前登录用户信息失败，请重新登录');
            }
        }
    });
}

var header_app = new Vue({
	el: "#header-app",
	data: {
		newMessages: {}
	},
	methods: {
		logout: function() {
			confirm('确定退出登录？', function(id){
				$.ajax({
					type:"get",
					url: "/logout",
					success: function(data){
						if(data.code === 0){
							window.location.href='/login';
                            return;
						}
						else{
							alert('系统异常，请联系管理员');
						}
					}
				});
			});
		},
		loadNewMessages: function() {
			var app = this;
			var m = {};
			$.ajax({
				type:"get",
				url: "/statics/data/inboxData.json",
				success: function(data){
                    if (data.code==401){
                        window.location.href='/login';
                        return;s
                    }
                    if(data.num){
 						app.newMessages = data;
 					}
				}
			});
		},
		timeFormat: function(ms){
			// 毫秒转日期时间
			return millisecondsToDateTime(ms);
		},
		titleFormat: function(msg){
			// 长度超过12，截取12个字符
			if(msg.length<=12){
				return msg;
			}				
			return msg.substr(0,11)+"···";
		},
		numberFormat: function(num){
			// 消息数量角标，大于99显示99+
			if(num<=99){
				return num;
			}
			return '&middot;&middot;';
		},
		helps:function(){
			sidebar_app.go('/p/helps','帮助');
		}
	},
	computed :{
		
	},
	created: function(){
		// 创建实例时获取未读消息
		this.loadNewMessages();
	}
})

var sidebar_app = new Vue({
	el: "#sidebar-app",
	data:{
		title: '主页',
		user: {}
	},
	methods: {
		profileEdit: function(){
			alert('修改信息成功')
		},
		go: function(p,tilte){
            $('#page-content').attr('src',p+'?_t='+new Date().getTime());
            //fillPage(p)
			this.title = tilte;
		}
	},
	created: function () {
		loadToken(this);
    }
})

var title_app = new Vue({
	el: "#title-app",
	data:{
	},
	computed:{
		title: function(){
			return sidebar_app.title;
		},
	},
	methods: {
		
	}
})

var fillPage = function(goal){
	$.ajax({
		method:"get",
        dataType: "html",
		url: goal,
		success: function(content) {
			$("#page-container").html(content)
		}
	});
}
