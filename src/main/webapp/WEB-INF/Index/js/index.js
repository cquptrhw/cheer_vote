window.onload = function () {
    let login = document.querySelector('.login');
    let myurl = "";

    login.addEventListener('click', function (e) {
        let user = document.querySelector('#username').value;
        let pwd = document.querySelector('#password').value;
        
        ajax({
            url: myurl + "/vote/admin/login",    //替换为vote/admin/login
            method: "POST",
            data: {
                username: user,
                password: pwd
            },
            success: function (json) {
                let data = JSON.parse(json);
                if (data.username) {
                    window.document.cookie = "username=" + data.username;
                    window.document.cookie = "role=" + data.role;
                    window.location.href = "./html/admin.html";
                } else {
                    alert("用户名或密码错误！");
                }
            }
        });
    });

    function getCookie(Name) {  
        var search = Name + "=" ;
        if (document.cookie.length > 0) { 
            offset = document.cookie.indexOf(search);
            if(offset != -1) {  
                offset += search.length;
                end = document.cookie.indexOf(";", offset);  
                if(end == -1) end = document.cookie.length;  
                return unescape(document.cookie.substring(offset, end));
            } 
            else return ""; 
        }  
    }
}