window.onload = function () {
    let academy = {
        "1": "通信与信息工程学院",
        "2": "计算机科学与技术学院",
        "3": "自动化学院",
        "4": "先进制造工程学院",
        "5": "光电工程学院/国际半导体学院",
        "6": "软件工程学院",
        "7": "生物信息学院",
        "8": "理学院",
        "9": "经济管理学院",
        "10": "传媒艺术学院",
        "11": "外国语学院",
        "12": "国际学院",
        "13": "网络空间安全与信息法学院",
        "14": "马克思学院",
        "15": "体育学院",
    }
    let xinxi = document.querySelector('#xinxi');
    let timu = document.querySelector('#timu');
    let detailXinxi = document.querySelector('.detail-xinxi');
    let detailTimu = document.querySelector('.detail-timu');
    let add = document.querySelectorAll('.add');
    let submitQ = document.querySelector('#submitQ');
    let addQuestion = document.querySelector('#addQuestion');
    let xingji  = document.querySelector('.xingji');
    let answerA = document.querySelectorAll('.answer')[0];
    let answerB = document.querySelectorAll('.answer')[1];
    let answerC = document.querySelectorAll('.answer')[2];
    let answerD = document.querySelectorAll('.answer')[3];
    let star1 = document.querySelector('.star1');
    let star2 = document.querySelector('.star2');
    let star3 = document.querySelector('.star3');
    let star4 = document.querySelector('.star4');
    let star5 = document.querySelector('.star5');
    let right = "A";
    let kind = 1;
    let role = getCookie('role');
    let exist = document.querySelector('#exist');
    let myurl = "";

    //获取学院的信息
    ajax({      
        url: myurl + "/vote/admin/cheer_result",    //替换为vote/admin/cheer_result
        method: "GET",
        success: function (json) {
            let data = JSON.parse(json);
            for (var i = 0; i < data.length; i++) {
                let ul = document.querySelector('.ul-xinxi');
                let name = academy[i + 1];
                let li = document.createElement("li");
                let classId = data[i].classId;
                if (data[i].result) {
                    li.innerHTML = "<span class='span-xinxi tclass'>" + name + "</span>";
                } else {
                    li.innerHTML = "<span class='span-xinxi'>" + name + "</span>";
                }
                ul.appendChild(li);
                li.addEventListener('click', function () {
                    window.location.href = "./addInfo.html?classId=" + classId;
                });
            }
        }
    });

    timu.addEventListener('click', function (event) {
        xinxi.className = "";
        timu.className = "now";
        detailTimu.className = "detail-timu show";
        detailXinxi.className = "detail-xinxi no";

        //获取每个星级的题目数量
        ajax({
            url: myurl + "/vote/admin/question_num",    //替换为vote/admin/question_num
            method: "get",
            success: function (json) {
                let data = JSON.parse(json);
                let num = document.querySelectorAll('.num');
                for (var i = 0; i < data.length; i++) {
                    num[i].innerHTML = data[i].total + "题"; 
                    let kind = i + 1;
                    if (role == 1) {
                        num[i].addEventListener('click', function () {
                            window.location.href = "./viewQuestion.html?kind=" + kind;
                        });
                    }
                }
                for (let i = 0; i < num.length; i++) {
                    num[i].addEventListener('click', function () {
                        let kind = i + 1;
                        let data = {
                            sessionId: sessionId,
                            kind: kind
                        };
                    });
                }
            }
        });
    });

    xinxi.addEventListener('click', function (event) {
        xinxi.className = "now";
        timu.className = "";
        detailXinxi.className = "detail-xinxi show";
        detailTimu.className = "detail-timu no";

        //更新学院信息
        ajax({
            url: myurl + "/vote/admin/cheer_result",    //替换为vote/admin/cheer_result
            method: "GET",
            success: function (json) {
                let data = JSON.parse(json);

                for (var i = 0; i < data.length; i++) {
                    let span = document.querySelectorAll('.span-xinxi');
                    let classId = data[i].classId;
                    if (data[i].result) {
                        span[i].className = "span-xinxi tclass";
                    } else {
                        span[i].className = "span-xinxi";
                    }
                }
            }
        });
    });

    for (let i = 0; i < add.length; i++) {
        add[i].addEventListener('click', function (event) {
            switch (i) {
                case 0: kind = 1;
                        star1.src = "../images/bstar.png";
                        star2.src = "../images/star.png";
                        star3.src = "../images/star.png";
                        star4.src = "../images/star.png";
                        star5.src = "../images/star.png";
                        break;
                case 1: kind = 2;
                        star1.src = "../images/star.png";
                        star2.src = "../images/bstar.png";
                        star3.src = "../images/star.png";
                        star4.src = "../images/star.png";
                        star5.src = "../images/star.png";
                        break;
                case 2: kind = 3;
                        star1.src = "../images/star.png";
                        star2.src = "../images/star.png";
                        star3.src = "../images/bstar.png";
                        star4.src = "../images/star.png";
                        star5.src = "../images/star.png";
                        break;
                case 3: kind = 4;
                        star1.src = "../images/star.png";
                        star2.src = "../images/star.png";
                        star3.src = "../images/star.png";
                        star4.src = "../images/bstar.png";
                        star5.src = "../images/star.png";
                        break;
                case 4: kind = 5;
                        star1.src = "../images/star.png";
                        star2.src = "../images/star.png";
                        star3.src = "../images/star.png";
                        star4.src = "../images/star.png";
                        star5.src = "../images/bstar.png";
                        break;
                default: break;  
            }
        });
    }

    answerA.addEventListener('click', function () {
        answerA.className = "answer right";
        answerB.className = "answer B";
        answerC.className = "answer C";
        answerD.className = "answer D";
        right = "A";
    });

    answerB.addEventListener('click', function () {
        answerA.className = "answer A";
        answerB.className = "answer right";
        answerC.className = "answer C";
        answerD.className = "answer D";
        right = "B";
    });

    answerC.addEventListener('click', function () {
        answerA.className = "answer A";
        answerB.className = "answer B";
        answerC.className = "answer right";
        answerD.className = "answer D";
        right = "C";
    });

    answerD.addEventListener('click', function () {
        answerA.className = "answer A";
        answerB.className = "answer B";
        answerC.className = "answer C";
        answerD.className = "answer right";
        right = "D";
    });

    submitQ.addEventListener('click', function () {
        let ti = document.querySelector('#ti');
        let answerA = document.querySelector('#answerA');
        let answerB = document.querySelector('#answerB');
        let answerC = document.querySelector('#answerC');
        let answerD = document.querySelector('#answerD');
        //提交题目
        ajax({
            url: myurl + "/vote/admin/question",    //替换为vote/admin/question
            method: "POST",
            data: {
                "title": ti.value,
                "A": answerA.value,
                "B": answerB.value,
                "C": answerC.value,
                "D": answerD.value,
                "answer": right,
                "kind": kind
            },
            success: function (json) {
                let data = JSON.parse(json);
                if (data == "上传成功") {
                    ti.value = "";
                    answerA.value = "";
                    answerB.value = "";
                    answerC.value = "";
                    answerD.value = "";
                    ajax({
                        url: myurl + "/vote/admin/question_num",    //替换为vote/admin/question_num
                        method: "get",
                        success: function (json) {
                            let data = JSON.parse(json);
                            let num = document.querySelectorAll('.num');
                            for (var i = 0; i < data.length; i++) {
                                num[i].innerHTML = data[i].total + "题"; 
                                let kind = i + 1;
                                if (role == 1) {
                                    num[i].addEventListener('click', function () {
                                        window.location.href = "./viewQuestion.html?kind=" + kind;
                                    });
                                }
                            }
                            for (let i = 0; i < num.length; i++) {
                                num[i].addEventListener('click', function () {
                                    let kind = i + 1;
                                    let data = {
                                        sessionId: sessionId,
                                        kind: kind
                                    };
                                });
                            }
                        }
                    });
                    ajax({
                        url: myurl + "/vote/admin/question_num",    //替换为vote/admin/question_num
                        method: "get",
                        success: function (json) {
                            let data = JSON.parse(json);
                            let num = document.querySelectorAll('.num');
                            for (var i = 0; i < data.length; i++) {
                                num[i].innerHTML = data[i].total + "题"; 
                                let kind = i + 1;
                                if (role == 1) {
                                    num[i].addEventListener('click', function () {
                                        window.location.href = "./viewQuestion.html?kind=" + kind;
                                    });
                                }
                            }
                            for (let i = 0; i < num.length; i++) {
                                num[i].addEventListener('click', function () {
                                    let kind = i + 1;
                                    let data = {
                                        sessionId: sessionId,
                                        kind: kind
                                    };
                                });
                            }
                        }
                    });
                } else {
                    return false;
                }
            }
        });
    });

    exist.addEventListener('click' ,function () {
        
    });

    function getCookie(Name) {  
        var search = Name + "=" ; 
        if (document.cookie.length > 0) {
            offset = document.cookie.indexOf(search);
            if (offset != -1) {  
                offset += search.length;
                end = document.cookie.indexOf(";", offset);  
                if (end == -1) end = document.cookie.length;  
                return unescape(document.cookie.substring(offset, end));
            } 
            else return ""; 
        }  
    }

}