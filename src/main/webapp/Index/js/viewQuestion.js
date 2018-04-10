window.onload = function () {
    let kind;
    let buttonDelete, answer;
    let xingji = document.querySelector('.xingji');
    let search = document.querySelector('#search');
    let submit = document.querySelector('#submit');
    kind = UrlSearch();
    let sessionId = getCookie('sessionId');
    let myurl = "";

    //星级
    switch (kind) {
        case "1": xingji.innerHTML = "一星";
                break;
        case "2": xingji.innerHTML = "二星";
                break;
        case "3": xingji.innerHTML = "三星";
                break;
        case "4": xingji.innerHTML = "四星";
                break;
        case "5": xingji.innerHTML = "五星";
                break; 
        default: break;      
    }

    //加载题目
    ajax({
        url: myurl + "/vote/admin/question_list?kind=" + kind + "&aa=" + kind ,     //vote/admin/question_list
        method: "GET",
        success: function (json) {
            let data = JSON.parse(json);
            let realData = data;
            let ul = document.querySelector('.ul-question');
            if (data) {
                for (let i = 0; i < realData.length; i++) {
                    let questionId = realData[i].questionId;
                    let question = "<div class='question' index='" + questionId + "'>" + questionId + ":" + realData[i].title + "</div>";
                    let answer = realData[i].answer;
                    let answerA = "<span class='answer'>A:" + realData[i].A + "</span>";
                    let answerB = "<span class='answer'>B:" + realData[i].B + "</span>";
                    let answerC = "<span class='answer'>C:" + realData[i].C + "</span>";
                    let answerD = "<span class='answer'>D:" + realData[i].D + "</span>";
                    
                    switch (answer) {
                        case "A": answerA = "<span class='answer right'>A:" + realData[i].A + "</span>";
                                break;
                        case "B": answerB = "<span class='answer right'>B:" + realData[i].B + "</span>";
                                break;
                        case "C": answerC = "<span class='answer right'>C:" + realData[i].C + "</span>";
                                break;
                        case "D": answerD = "<span class='answer right'>D:" + realData[i].D + "</span>";
                                break;
                    }
                    
                    let classDelete = "<span class='delete'></span>";
                    let li = document.createElement('li');

                    li.innerHTML = question + answerA + answerB + answerC + answerD + classDelete;
                    li.className = "li";
                    ul.appendChild(li);
                }
                buttonDelete = document.querySelectorAll('.delete');
                answer = document.querySelectorAll('.answer');
                deleteid();
                revise()
            }
        }
    });

    //删除题目
    function deleteid() {
        setTimeout(function () {
            for (let i = 0; i < buttonDelete.length; i++) {
                buttonDelete[i].addEventListener('click', function () {
                    let num = buttonDelete[i].parentNode.children[0].getAttribute('index');
                    let question = document.querySelectorAll('.question')[i];
                    let answer = document.querySelectorAll('.li')[i].querySelectorAll('.answer');
                    let questionId = num;
                    let data = {
                        questionId: num
                    };
                    ajax({
                        url: myurl + "/vote/admin/question/del",    //vote/admin/question/del/{questionId}
                        method: "DELETE",
                        data: data,
                        success: function (json) {
                            let data = JSON.parse(json);
                            if (data === "删除成功") {
                                for (let j = 0; j < answer.length; j++) {
                                    answer[j].className = "answer del";
                                }
                                question.className = "question del";
                            }
                        }
                    });
                });
            }   
        }, 50);
    }

    //修改题目
    function revise() {
        setTimeout(function () {
            for (let i = 0; i < answer.length; i++) {
                answer[i].addEventListener('click', function () {
                    let question = answer[i].parentNode;
                    let questionId = question.childNodes[0].getAttribute('index');
                    let answerA = question.childNodes[1];
                    let answerB = question.childNodes[2];
                    let answerC = question.childNodes[3];
                    let answerD = question.childNodes[4];
                    let questionContent = question.childNodes[0].innerHTML.split(':')[1];
                    let Data = {
                        questionId: questionId,
                        title: questionContent,
                        A: answerA.innerHTML,
                        B: answerB.innerHTML,
                        C: answerC.innerHTML,
                        D: answerD.innerHTML,
                        answer: answer[i].innerHTML.split(':')[0],
                        kind: kind
                    };
                    for (let j = 1; j < question.childNodes.length - 1; j++) {
                        question.childNodes[j].className = 'answer';
                    }

                    ajax({
                        url: myurl + "/vote/admin/question_list/",     //vote/admin/question_list
                        method: "POST",
                        data: Data,
                        success: function (json) {
                            let data = JSON.parse(json);
                            if (data) {
                                answer[i].className = 'answer right';
                            }
                        }
                    });
                    
                });
            }   
        }, 50);
    };

    //搜索题目
    submit.addEventListener('click', function () {      
        let key = search.value;
        ajax({
            url: myurl + "/vote/admin/question/sel?key=" + key + "&aa=1",     //vote/admin/question/sel/{key}
            method: "GET",
            success: function (json) {
                let data = JSON.parse(json);

                if (data) {
                    let ul = document.querySelector('.ul-question');
                    let child = ul.querySelectorAll('li');

                    for (let t = 0; t < child.length; t++) {
                        ul.removeChild(child[t]);
                    }

                    for (let i = 0; i < data.length; i++) {
                        let questionId = data[i].questionId;
                        let question = "<div class='question' index='" + questionId + "'>" + questionId + ":" + data[i].title + "</div>";
                        let answer = data[i].answer;
                        let answerA = "<span class='answer'>A:" + data[i].A + "</span>";
                        let answerB = "<span class='answer'>B:" + data[i].B + "</span>";
                        let answerC = "<span class='answer'>C:" + data[i].C + "</span>";
                        let answerD = "<span class='answer'>D:" + data[i].D + "</span>";
                        
                        switch (answer) {
                            case "A": answerA = "<span class='answer right'>A:" + data[i].A + "</span>";
                                    break;
                            case "B": answerB = "<span class='answer right'>B:" + data[i].B + "</span>";
                                    break;
                            case "C": answerC = "<span class='answer right'>C:" + data[i].C + "</span>";
                                    break;
                            case "D": answerD = "<span class='answer right'>D:" + data[i].D + "</span>";
                                    break;
                        }
                        
                        let classDelete = "<span class='delete'></span>";
                        let li = document.createElement('li');

                        li.innerHTML = question + answerA + answerB + answerC + answerD + classDelete;
                        li.className = "li";
                        ul.appendChild(li);
                    }
                    
                    buttonDelete = document.querySelectorAll('.delete');
                    answer = document.querySelectorAll('.answer');
                    deleteid();
                    revise();
                }
            }
        });
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

    function getCookie(Name) {  
        let search = Name + "=" ; 
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