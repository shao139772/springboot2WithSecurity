//输入用户名处获得焦点
function userIdOnfocus(obj){
    if(obj.value == obj.defaultValue){//当用户名的值是初始值（请输入用户名）时
        obj.value = "";//将用户名的value变成空
        obj.className="noHint";//将样式变成黑色
    }
}
//输入用户处失去焦点
function userIdOnblur(obj){
    var userId = $("#userLoginName").val();
    if(userId == ""){//如果用户名的值是空
        console.log(obj.defaultValue)
        $("#userLoginName").val(obj.defaultValue);//将用户名的值变成初始值（请输入用户名）
        $("#userLoginName").removeClass().addClass("prompt");//将样式变成灰色
    }//否则不变成初始值
}
//输入密码处获得焦点
function userPasswdOnfocus(obj){
    if(obj.value == obj.defaultValue){//当密码的值是初始值（请输入密码）时
        $('#showPwd').hide();//隐藏密码提示窗口
        $('#userLoginPassWord').show().focus();//显示密码输入窗口
    }
}
//输入密码处失去焦点
function userPasswdOnblur(obj){
    if(obj.value == ""){//如果密码的值是空
        $('#userLoginPassWord').hide();//隐藏密码输入窗口
        $('#showPwd').show();//显示密码提示窗口
    }
}

function login(){
    var userLoginName = $("#userLoginName").val();
    var userLoginPassWord = $("#userLoginPassWord").val();
    $.ajax({
        url : '/sys/login/userLogin',
        type : 'post',
        data:{userLoginName : userLoginName, userLoginPassWord : userLoginPassWord},
        success : function(data) {
           console.log(data.code);
           if(data.code == 0){//登录成功，跳转至首页
               sessionStorage.setItem('name', userLoginName);
               window.location.href = "/sys/login/index";
           }else{//登录失败，输出失败原因
               alert(data.msg);
           }
        }
    });
}