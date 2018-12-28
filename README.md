

# 啦啦队项目接口文档

## 目录

[TOC]

## 后台 [无需加密]

### 登陆vote/admin/login  [POST]

#### POST参数

- username 管理员账号
- password 密码

#### Return

- sessionId

```
json{
	"role": 0,
	"username": 789
}
```

### 啦啦队是否上传信息  vote/admin/cheer_result [GET]

- sessionId

#### Return

- classId 啦啦队的代号
- className 啦啦队所在学院名称呼
- result 是否上传
  - 1 已经上传
  - 0 尚未上传

```json
[{
	"classId": 3,
	"className": "计算机",
	"result": 1
}, {
	"classId": 2,
	"className": "自动化",
	"result": 0
}, {
	"classId": 1,
	"className": "软件",
	"result": 0
}, {
	"classId": 4,
	"className": "通信",
	"result": 1
}]
```

### 啦啦队信息上传 vote/admin/cheer_info [POST]

- sessionId

#### POST参数

- classId 啦啦队代号
- slogan 口号
- introduce 简介
- firstImg 首页宣传图 
- video 详情页宣传视视频
- playImg1 轮播图1
- playImg2 轮播图2
- playImg3 轮播图3

#### Return

```json
{
	"上传成功"
}
```

### 啦啦队队员信息上传 vote/admin/cheer_player [POST]

- sessionId

#### POST参数

- classId 啦啦队代号
- img 图片
- playerName 姓名
- introduce 简介

#### Return

```
{
	"上传成功"
}
```

### 后台题目上传 vote/admin/question [POST]

- sessionId 

#### POST参数

- question 题目
- A 答案
- B 答案
- C 答案
- D 答案
- answer 正确答案
- kind 题目难度类型
  - 1-5分别代表1-5星难度

```json
{
    "title": "ndaofo",
	"kind": "3",
	"A": "xadda",
	"B": "传递是",
	"C": "按错发",
	"D": "啊啊我",
	"answer": "A"
}
```

#### Return

```json
{
	"上传成功"
}
```

### 后台获取题目数量 vote/admin/question_num [GET]

- sessionId

#### Return

```json
[{
	"kind": 1,
	"total": 4
}, {
	"kind": 2,
	"total": 5
}, {
	"kind": 3,
	"total": 2
}, {
	"kind": 4,
	"total": 2
}, {
	"kind": 5,
	"total": 1
}]
```

### 后台查看题目 vote/admin/question_list/{kind} [GET]

- sessionId 

#### GET参数

- kind 题目种类

#### Return

```json
[ {
	"A": "你好1",
	"B": "12456",
	"C": "撒旦da",
	"D": "ANOWHD AOWDHA",
	"answer": "D",
	"kind": "1",
	"questionId": 4,
	"title": "nihao "
}, {
	"A": "你好7",
	"B": "12456",
	"C": "撒旦da",
	"D": "ANOWHD AOWDHA",
	"answer": "A",
	"kind": "1",
	"questionId": 10,
	"title": "nihao "
}, {
	"A": "你好8",
	"B": "12456",
	"C": "撒旦da",
	"D": "ANOWHD AOWDHA",
	"answer": "D",
	"kind": "1",
	"questionId": 11,
	"title": "nihao "
}]
```

### 后台删除题目 vote/admin/question/del/{questionId} [DELETE]

#### DELETE参数

- sessionId
- questionId 需要删除的题目id 

#### Return

```json
{
	"删除成功"
}
```

### 后台搜索题目 vote/admin/question/sel/{key} [GET]

#### GET参数

- sessionId
- key 关键词

#### Return

```json
[{
	"A": "222222",
	"B": "12456",
	"C": "你好",
	"D": "ANOWHD AOWDHA",
	"answer": "D",
	"kind": "1",
	"questionId": 4,
	"title": "nihao "
}, {
	"A": "你好2",
	"B": "12456",
	"C": "nihao",
	"D": "ANOWHD AOWDHA",
	"answer": "C",
	"kind": "4",
	"questionId": 5,
	"title": "456"
}, {
	"A": "1111",
	"B": "12456",
	"C": "撒旦da",
	"D": "ANOWHD AOWDHA",
	"answer": "D",
	"kind": "3",
	"questionId": 6,
	"title": "你好啊"
}]
```

### 后台修改题目 vote/admin/question_list [POST]

#### POST参数

- sessionId
- question 题目
- A 答案
- B 答案
- C 答案
- D 答案
- answer 正确答案
- questionId 题号

```json
{		
    "questionId": "5",
	"title": "ndaofo",
	"A": "xadda",
	"B": "传递是",
	"C": "按错发",
	"D": "啊啊我",
	"answer": "A",
	"kind":"1"
}
```

#### Return

```json
{		
    "questionId": "5",
	"title": "ndaofo",
	"A": "xadda",
	"B": "传递是",
	"C": "按错发",
	"D": "啊啊我",
	"answer": "A",
	"kind":"1"
}
```

## 留言 [有加密]

### 发布留言 vote/user/content [POST]

#### POST参数[加密]

- sessionId

- string 

  - base64(json)后的字符串

    - ```json
      {
          "classId":"5",
      	"content":"你好啊"
      }
      ```

    - content 留言内容

    - classId 啦啦队id

- timestamp 

- nonce

- signature

#### Return

```json
{
   	"contentId": "5",
	"content": "ndaofo",
	"classId":"5",
    "create_time":"2018-02-24 01:42:38"
}
```

### 留言点赞 vote/user/content_praise [POST]

- sessionId 

#### POST参数

- contentId 被点赞文章id

#### Return

```json
{
	"contentId": "2",
	"praiseNum": "2",
	"message": "点赞成功"
}
```

### 获取留言 vote/user/message/{page}/{classId} [GET]

- sessionId 

#### GET参数

- page：页数
- classId ：啦啦队id

#### Return

```json
[{
	"content": "你好",
	"contentId": 12,
	"created_time": "2018-03-21 23:36:36.0",
	"headImgUrl": "242vvevev",
	"nickName": "1111",
	"prasieNum": 0
}, {
	"content": "你好",
	"contentId": 10,
	"created_time": "2018-03-21 23:36:30.0",
	"headImgUrl": "242vvevev",
	"nickName": "1111",
	"prasieNum": 0
}  {
	"content": "你好哦5",
	"contentId": 4,
	"created_time": "2018-02-24 01:43:15.0",
	"headImgUrl": "saas",
	"nickName": "4656",
	"prasieNum": 0
}, {
	"content": "你好哦",
	"contentId": 2,
	"created_time": "2018-02-24 01:42:38.0",
	"headImgUrl": "saas",
	"nickName": "4656",
	"prasieNum": 2
}]
```

## 答题 [有加密]

### 获取当日答题数量 vote/user/todayNum [GET]

- sessionId

#### Return

```json
{
	"todayNum": 6,
}
```

### 获取题目 vote/user/question [GET]

- sessionId 

#### Return

```json
{
	"A": "你好8",
	"B": "12456",
	"C": "撒旦da",
	"questionId": 11,
	"D": "ANOWHD AOWDHA",
	"answer": "",
	"todayNum": 1,
	"title": "nihao "
}
```

### 获取正确答案 vote/user/answer [POST]

- sessionId

#### POST参数 [加密]

- string 

  - base64(json)后的字符串

  - ```json
    {
        "questionId":5,
    	"userAnswer":"B"
    }
    ```

- timestamp 

- nonce

- signature

#### Return 

- questionId 题目id
- openId 答题者openId
- answer 答题者选择的答案

```json
{
	"rightAnswer": "C",
	"questionId": 3,
	"userAnswer": "C",
	"openId": "o6sBL0r9h8WVC6XuHuU2Q_3E-Niw",
	"status": 1
}
```

### 获取幸运用户 vote/user/luckyUser [GET]

- sessionId

#### Return

- nickname 用户名
- rightNum 今日答题正确数

```json
[{
	"headImgUrl": "aaaaaaaaaaaaaaaaaaaaa",
	"nickname": "aaaaaa",
	"rightNum": "1"
}, {
	"headImgUrl": "242vvevev",
	"nickname": "1111",
	"rightNum": "1"
}, {
	"headImgUrl": "wf gcreegeg",
	"nickname": "cerv rh h",
	"rightNum": "1"
}]
```

### 获取答题榜 vote/user/get/userRank [GET]

- sessionId

#### Return

```json
[{
	"headImgUrl": "aaaaaaaaaaaaaaaaaaaaa",
	"nickname": "aaaaaa",
	"rightNum": "2"
}, {
	"headImgUrl": "242vvevev",
	"nickname": "1111",
	"rightNum": "0"
}, {
	"headImgUrl": "wf gcreegeg",
	"nickname": "cerv rh h",
	"rightNum": "0"
}]
```

## 助力

### 获取自己的剩余助力数 vote/user/assistance [GET]

- sessionId

#### Return

```json
{
	"assistance":255
}	
```

### 获取自己的助力历史 vote/user/assistance/history [GET]

- sessionId

#### Return

```json
[{
	"assistance": 1,
	"classId": "1",
	"className": "计算机",
	"distance": 2,
	"groupId": 1,
	"groupName": "kkk"
}, {
	"assistance": 1,
	"classId": "2",
	"className": "自动化",
	"distance": 2,
	"groupId": 2,
	"groupName": "ooo"
}, {
	"assistance": 1,
	"classId": "3",
	"className": "软件",
	"distance": 5,
	"groupId": 3,
	"groupName": "ppp"
}]
```

### 为啦啦队增加里程 vote/user/cheer/distance [POST]

- sessionId

  #### POST参数

- string 

  - base64(json)后的字符串

  - ```json
    {
        "data":[{
    				"classId": "5",
    				"num": "10"，
    				"groupId":2
    			},{
               		"classId": "6",
    				"num": "5",
    				"groupId":3
    			}]
    }
    ```

  - classId 今日已经回答题数

  - num 添加的助力数

  - groupId 啦啦队对应的组号

- timestamp 

- nonce

- signature

#### Return

```
json[{
	"distance": 2,
	"groupId": "1",
	"groupName": "kkk"
}, {
	"distance": 50,
	"groupId": "3",
	"groupName": "ppp"
}, {
	"distance": 35,
	"groupId": "7",
	"groupName": "aaa"
}]
```

### 获取啦啦队榜单 vote/user/cheer/groupRank [GET]

- sessionId

#### Return

```json
[{
	"className": "[\"自动化\"]",
	"distance": 2,
	"groupId": "1",
	"groupName": "kkk"
}, {
	"className": "[\"通信\"]",
	"distance": 2,
	"groupId": "2",
	"groupName": "ooo"
}, {
	"className": "[\"计算机\",\"软件\"]",
	"distance": 60,
	"groupId": "3",
	"groupName": "ppp"
}, {
	"className": "[\"传媒\"]",
	"distance": 45,
	"groupId": "7",
	"groupName": "aaa"
}]
```

## 获取信息

### 详情页拉拉队信息获取 vote/user/cheer_info/main/{classId} [GET]

- sessionId

#### GET参数

- classId 啦啦队id

#### Return

```json
[{
	"className": "计算机",
	"firstImg": "https://www.bejson.com/",
	"introduce": "aaaaaa",
	"playImg1": "https://www.bejson.com/",
	"playImg2": "https://www.bejson.com/",
	"playImg3": "https://www.bejson.com/",
	"slogan": "aaaa",
	"video": ""
}, {
	"className": "软件",
	"firstImg": "https://www.bejson.com/",
	"introduce": "软件",
	"playImg1": "https://www.bejson.com/",
	"playImg2": "https://www.bejson.com/",
	"playImg3": "https://www.bejson.com/",
	"slogan": "2",
	"video": "https://www.bejson.com/"
}]	
```

### 详情页啦啦队队员信息获取 vote/user/cheerPlayerInfo/{classId}

#### GET参数

- classId 拉拉队参数

#### Return

```json
[{
	"classId": 9,
	"imgUrl": "D:\\cheer_vote\\target\\cheer_vote\\.\%uploadbingdao1.png",
	"introduce": "5654trdf",
	"playerName": "erfwer"
}, {
	"classId": 9,
	"imgUrl": "D:\\cheer_vote\\target\\cheer_vote\\.\%uploadbingdao1.png",
	"introduce": "sdxbfbdfg",
	"playerName": "ew5"
}]
```

### 首页信息获取 vote/user/cheer_info/firstPage [GET]

- sessionId

#### Return

```json
[{
	"className": "自动化",
	"firstImg": "https://www.bejson.com/",
	"slogan": "滚"
}, {
	"className": "软件",
	"firstImg": "https://www.bejson.com/",
	"slogan": "2"
}, {
	"className": "通信",
	"firstImg": "https://www.bejson.com/",
	"slogan": "5"
}]
```

### 启动页信息获取 vote/user/startPage [GET]

- sessionId

#### Return

```json
{
	"rightNumRank": "99%",
	"assistanceRank": "97%",
	"todayNumRank": "99%",
	"todayNum": "5",
	"assistance": "5",
	"rightNum": "2"
}
```

### 用户信息获取 vote/user/info [GET]

- sessionId

#### Return

```json
{
	"headImgUrl": 	 "http://thirdwx.qlogo.cn/mmopen/vi_32/MLtIOC9Jq8JHVzfuZvHw75R8hflN0YZwQlsDZ3CXS5ovNTia3W5MPQHbFcx1Y66Fg5kwmlEHfPjSWwUcSMjXIY",
	"openId": "o6sBL0r9h8WVC6XuHu",
	"nickName": "Augustus"
}	
```

## 注释

### 学院和其对应的学院id

1. 通信学院
2. 计算机科学与技术学院
3. 自动化学院
4. 先进制造工程学院
5. 光电工程学院/国际半导体学院
6. 软件工程学院
7. 生物信息学院
8. 理学院
9. 经济管理学院
10. 传媒艺术学院
11. 外国语学院
12. 国际学院
13. 网络空间安全与信息法学院
14. 马克思学院
15. 体育学院

### 信息加密方式

- signature=sha1(md5(string+timestamp+nonce)+"cheer_vote")
- timestamp UNIX时间戳
- string = base64(json);
  - json 需要提交的数据
- nonce 随机数

