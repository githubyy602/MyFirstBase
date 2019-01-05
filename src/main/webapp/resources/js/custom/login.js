/**
 * 登录界面
 * @type {Vue}
 */
var loginVue = new Vue({
    el : "#login",
    methods : {
        goRegist : function () {
            $("#login").hide();
            $("#register").show();
        },
        doLogin : function () {
            var userName = $("#username").val(),
                pwd = $("#pwd").val();

            if(userName.trim().length <=2){
                layer.msg("请输入正确的用户名",{time:2000});
                return;
            }

            if (pwd.trim().length == 0){
                layer.msg("请输入密码",{time:2000});
                return;
            }

            $.ajax({
                url : parent.basePath+"/tourist/login.do",
                data : {"username":userName,"pwd":$.md5(pwd)},
                type : 'post',
                dataType : "json",
                success : function(data) {
                    //debugger;
                    if(data != null){
                        if(data.status == "success"){
                            layer.msg(data.message,{time:1000,end:function(){
                                parent.window.location.href=parent.basePath+"/tourist/index.do";
                                }})
                        }
                        else{
                            layer.alert(data.message,{yes:function(){
                                    layer.close(layer.index);
                                }})
                        }
                    }
                    else{
                        layer.alert("登录异常",{yes:function(){
                            layer.close(layer.index);
                            }});
                    }
                }
            });

        }
    }
});


var registVue = new Vue({
    el : "#register",
    methods : {
        //返回
        goBack : function () {
            $("#login").show();
            $("#register").hide();
        },
        //注册
        doRegist : function () {
            var userName = $("#r_username").val(),
                pwd = $("#r_pwd").val(),
                pwd2 = $("#r_pwd2").val(),
                birth = $("#birthday").val(),
                sex = 0;

                $("input[name='sex']").each(function(){
                    if($(this).is(":checked")){
                        sex = $(this).val();
                    }
                });

                //console.log($.md5(pwd));

            $.ajax({
                url : parent.basePath+"/tourist/register.do",
                data : {"userName":userName,"pwd":$.md5(pwd),"pwd2":$.md5(pwd2),"birth":birth,"sex":sex},
                type : "post",
                dataType : "json",
                success : function (data) {

                    if(data != null){
                        if(data.status == "success"){
                            layer.msg("已注册成功,请登录",{time:2000,success:function () {
                                    $("#login").show();
                                    $("#register").hide();
                                }})
                        }
                        else{
                            layer.alert(data.message);
                        }
                    }
                    else{
                        layer.msg("注册异常",{time:2000});
                    }
                },
                error:function (res) {
                    console.log(res);
                }
            });

            // var url = parent.basePath+"/tourist/register.do";
            // var obj = {"userName":userName,"pwd":pwd,"pwd2":pwd2,"birth":birth,"sex":sex};
            //
            // this.$http.post(url,obj,{emulateJSON:true}).then(function (res) {
            //     debugger;
            //     if(res != null){
            //             if(res.status == "success"){
            //                 layer.msg("请登录",{time:2000,success:function () {
            //                         $("#login").show();
            //                         $("#register").hide();
            //                     }})
            //             }
            //             else{
            //                 layer.alert(res.message);
            //             }
            //         }
            //         else{
            //             layer.msg("注册异常",{time:2000});
            //         }
            // });
        }
    }
});