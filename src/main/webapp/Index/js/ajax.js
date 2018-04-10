function ajax(json) {
    // 创建ajax对象
    let xhr = null; 
    let method = json.method || 'get';
    let url = json.url;
    let asyn = json.asyn ? true : json.asyn == false ? false : true;
    let data = json.data || '';
    let success = json.success;
    let error = json.error;

    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else {
        xhr = new ActiveXObject('Microsoft.XMLHTTP')
    }
    if (method.toLowerCase() === 'get') {
        url += '?' + data + '&' + new Date().getTime();
    } else if (method.toUpperCase() === 'DELETE') {
        let params = [];
        for (let key in data) {
            if(data[key] || data[key] === 0) {
                params.push(key + '=' + data[key]);
            }
        };
        xhr.withCredentials = true;
        let Data = params.join('&');
        xhr.open('DELETE', url + "?" + Data, true);
        // xhr.setRequestHeader('content-type','application/x-www-form-urlencoded');
        xhr.send(null);
        xhr.onreadystatechange = function() {
            if(xhr.readyState == 4){
                if (xhr.status == 200) {
                    success && success(xhr.responseText);
                } else {
                    if (error) {
                        error && error();
                    }
                }
            }
        }
        return;
    }

    // 处理返回数据
    xhr.onreadystatechange = function() {
        if(xhr.readyState == 4){
            if (xhr.status == 200) {
                success && success(xhr.responseText);
            } else {
                if (error) {
                    error && error();
                }
            }
        }
    }
    xhr.open(method, url, asyn);
    xhr.setRequestHeader('content-type','application/x-www-form-urlencoded');
    xhr.withCredentials = true;
    xhr.send(JSON.stringify(data));
};