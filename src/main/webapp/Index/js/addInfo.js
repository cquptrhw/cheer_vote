window.onload = function () {
    let academy = [
        "通信与信息工程学院",
        "计算机科学与技术学院",
        "自动化学院",
        "先进制造工程学院",
        "光电工程学院/国际半导体学院",
        "软件工程学院",
        "生物信息学院",
        "理学院",
        "经济管理学院",
        "传媒艺术学院",
        "外国语学院",
        "国际学院",
        "网络空间安全与信息法学院",
        "马克思学院",
        "体育学院"
    ];
    let classId;
    classId = UrlSearch();
    let teamName = document.querySelector('.teamName');
    let slogan = document.querySelector('#slogan');
    let introduce = document.querySelector('#introduce');
    let imgIndex = document.querySelector('#img-index');
    let video = document.querySelector('#video');
    let imgSlider1 = document.querySelector('#img-slider1');
    let imgSlider2 = document.querySelector('#img-slider2');
    let imgSlider3 = document.querySelector('#img-slider3');
    let submit = document.querySelector('#submit');
    let prefirst = document.querySelector('.prefirst');
    let prevideo = document.querySelector('.prevideo');
    let preslider1 = document.querySelector('.preslider1');
    let preslider2 = document.querySelector('.preslider2');
    let preslider3 = document.querySelector('.preslider3');
    let addGirlsInfo = document.querySelector('.addGirlsInfo');
    let viewGirlsInfo = document.querySelector('.viewGirlsInfo');
    let subGirlsInfo = document.querySelector('.subGirlsInfo');
    let girlsImg1 = document.querySelector('.girlsImg1');
    let addGirl = document.querySelector('.addGirl');
    let girlsImg2 = document.querySelector('.girlsImg2');
    let girlImg = document.querySelector('#girlImg');
    let girlName = document.querySelector('#girlName');
    let girlInfo = document.querySelector('#girlInfo');
    let close = document.querySelector('.close');
    let myurl = "";

    for (let i = 0; i < academy.length; i++) {
        if ((classId - 1) == i) {
            teamName.innerHTML = academy[i];
        }
    }

    imgIndex.addEventListener('change', function (event) {
        previewImage(event.target, '.prefirst');
    });

    imgSlider1.addEventListener('change', function () {
        previewImage(event.target, '.preslider1');
    });

    imgSlider2.addEventListener('change', function () {
        previewImage(event.target, '.preslider2');        
    });

    imgSlider3.addEventListener('change', function () {    
        previewImage(event.target, '.preslider3');
    });

    girlImg.addEventListener('change', function () {
        previewImage(event.target, '.girlsImg1');
        previewImage(event.target, '.girlsImg2');
    });

    addGirlsInfo.addEventListener('click', function (event) {
        addGirl.className = 'addGirl';
    });

    viewGirlsInfo.addEventListener('click', function (event) {
        addGirl.className = 'addGirl';
    });

    close.addEventListener('click', function (event) {
        addGirl.className = 'addGirl no';
    });

    function UrlSearch() {
        let name, value;
        let str = location.href; //取得整个地址栏
        let num = str.indexOf("?")
        str = str.substr(num + 1); //取得所有参数
        let arr = str.split("&"); //各个参数放到数组里
        
        for(let i = 0; i < arr.length; i++) {
            num = arr[i].indexOf("=");
            if (num > 0) {
                name = arr[i].substring(0, num);
                value = arr[i].substr(num + 1);
                this[name] = value;
            }
        }
        return value;
    }

    function previewImage(file, prvid) { 
        // file：file控件 
        // prvid: 图片预览容器 
        let tip = "不是图片"; // 设定提示信息 
        let filters = { 
            "jpeg" : "/9j/4", 
            "gif" : "R0lGOD", 
            "png" : "iVBORw" 
        };
        let prvbox = document.querySelector(prvid); 
        prvbox.innerHTML = ""; 
        if (window.FileReader) { // html5方案 
            for (let i = 0, f; f = file.files[i]; i++) { 
                let fr = new FileReader(); 
                fr.onload = function(e) { 
                    let src = e.target.result; 
                    if (!validateImg(src)) { 
                        alert(tip);
                    } else { 
                        showPrvImg(src);
                    } 
                } 
                fr.readAsDataURL(f); 
            } 
        } else { // 降级处理
            if ( !/\.jpg$|\.png$|\.gif$/i.test(file.value) ) { 
                alert(tip); 
            } else { 
                showPrvImg(file.value); 
            } 
        } 

        function validateImg(data) { 
            let pos = data.indexOf(",") + 1; 
            for (let e in filters) { 
                if (data.indexOf(filters[e]) === pos) { 
                    return e; 
                } 
            } 
            return null; 
        } 

        function showPrvImg(src) { 
            prvbox.src = src;
        }
    }

    submit.addEventListener('click', function (e) {
        let slogan1 = document.querySelector('#slogan').value;
        let introduce1 = document.querySelector('#introduce').value;
        let firstImg1 = imgIndex.files[0];
        let video1 = document.querySelector('#video').value;
        let playImg11 = imgSlider1.files[0];
        let playImg21 = imgSlider2.files[0];
        let playImg31 = imgSlider3.files[0];

        let form = new FormData();
        form.append("slogan", slogan1);
        form.append("introduce", introduce1);
        form.append("firstImg", firstImg1);
        form.append("video", video1);
        form.append("playImg1", playImg11);
        form.append("playImg2", playImg21);
        form.append("playImg3", playImg31);
        form.append("classId", classId);

        //上传上半部分
        let xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.open("post", myurl + '/vote/admin/cheer_info', true);
        //http://evchq8.natappfree.cc/

        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                alert("上传完成!");
            }
        };

        xhr.send(form);
    });

    subGirlsInfo.addEventListener('click', function () {
        let pic = girlImg.files[0];
        let name = girlName.value;
        let info = girlInfo.value;

        let form = new FormData();
        form.append('ImgUrl', pic);
        form.append('playerName', name);
        form.append('introduce', info);
        form.append('classId', classId);

        //上传下半部分
        let xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.open("post", myurl + '/vote/admin/cheer_player', true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                girlImg.outerHTML = girlImg.outerHTML;
                girlName.value = "";
                girlInfo.value = "";
                girlsImg1.src = "../images/tu.png";
                girlsImg2.src = "../images/tu.png";
            }
        }

        xhr.send(form);
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

