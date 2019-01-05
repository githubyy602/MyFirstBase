//中间层content
var indexVue = new Vue({
    el : "#content",
    data : {
        //mvvm中的赋值名
        content:basePath+"/tourist/getContent.do"
    },
    mounted:function(){
        //渲染完成后调用
        $.ajax({
            url : basePath+"/tourist/checkLogin.do",
            data : {},
            type : "post",
            dataType : "json", //决定返回内容类型
            success : function (data) {
                if(data != null){
                    if(data.status == "success"){
                        $("#logo span").text(data.message);
                        $("#logo").show();
                        $("#login_exit").hide();
                    }
                    else{
                        $("#logo").hide();
                        $("#login_exit").show();
                    }
                }
                else {
                    layer.msg("获取用户信息异常，请联系管理员",{time:2000});
                }
            }
        });
    },
    created : function(){
        //vue初始化完成后执行
        //alert("created");
    },
    methods : {

    }
});

//header

var headerVue = new Vue({
    el : "#head",
    methods : {
        showLogin : function () {
            layer.open({
                content : basePath+"/tourist/goLoginPage.do",
                type : 2,
                title : "Login",
                area : ['600px','400px'],
                shadeClose : true,  //点击遮罩层是否关闭弹出页面
                shift : 1 //弹出动画类型：1为从上而下

            });
        },
        logout : function () {
            $.ajax({
                url : basePath+"/user/logout.do",
                data : {},
                type : "post",
                dataType : "json",
                success:function (data) {
                    debugger;
                    if(data != null){
                        if(data.status == "success"){
                            layer.msg("用户已注销",{time:2000,end:function () {
                                    window.location.href=basePath+"/tourist/index.do";
                                }});
                        }
                        else{
                            layer.alert("用户注销异常",{yes:function () {
                                    layer.close(layer.index);
                                }})
                        }
                    }
                    else{
                        layer.alert("用户注销异常",{yes:function () {
                                layer.close(layer.index);
                            }})
                    }
                }
                
            });
        },
        goHome : function () {
            window.location.href = basePath+"/tourist/index.do";
        },
        searchClick :function () {
            
        }
    }
});

//search
var flag = true;
var searchPartVue = new Vue({
    el : "#search",
    methods : {
        secondsKillClick : function () {
            var id = returnCitySN['cip'],name = returnCitySN['cname'];

            if(!flag) return;
            flag = false;

            $.ajax({
                url : basePath+"/tourist/secondsKillActive.do",
                data : {"id":id,"name":name},
                type : "POST",
                dataType:"json",
                success : function (data) {
                    debugger;
                    if(data.success){
                        layer.msg(data.msg,{time:1000});
                        flag = true;
                    }else{
                        layer.alert(data.msg,{btn:"确定"});
                        flag = true;
                        return;
                    }

                },
                error : function () {
                    layer.alert("发生异常",{btn:"确定"});
                    flag = true;
                    return;
                }
            });

        }
    }
});