#后端服务器接口：39.106.42.184:8080
#后端数据库接口：39.106.42.184:27017
#接口详细说明：
##1. 发送注册验证码POST
###url:/api/user/register/code
1. 传参{ "email":"xxx@xx.com"}
2. 发送成功状态码200返回{"message"："success","data":null}
3. 发送失败状态码250返回{"message"："验证码发送失败","data":null}
##2. 注册POST
###url:/api/user/register
1. 传参{ "email":"xxx@xx.com","id":"xxx","password":"xxx","validateRegistration": "123456"}
2. 发送成功状态码200返回{"message"：{
  "email": "string",
  "favoriteRoom": [
    "string"
  ],
  "historyRoom": [
    "string"
  ],
  "id": "string",
  "nickName": "string",
  "password": "string",
  "photoImage": "string",
  "registerDate": "2020-04-24T14:29:40.426Z",
  "uid": "string",
  "validateRegistration": "string",
  "validateRetrieve": "string"
},"data":null}
3. 发送失败状态码250返回{"message"："验证码发送失败","data":null}
##3. 登录POST
###url:/api/user/login
1. 传参{ "email":"xxx@xx.com","password": "string"}
2. 发送成功状态码200返回{"message"："success","data":"xxx"}(xxx为uId)
3. 发送失败状态码250返回{"message"："用户名或密码错误","data":null}
##4. 根据用户id查询用户信息GET 
###url:/api/user/getUserInfo/{uid}
1. 发送成功状态码200返回{"message"："success","data":{
  "email": "string",
  "favoriteRoom": [
    "string"
  ],
  "historyRoom": [
    "string"
  ],
  "nickName": "string",
  "photoImage": "string",
  "uid": "string"
}} 
2. 发送失败状态码250返回{"message"："用户不存在","data":null}
##5. 发送找回密码验证码POST
###url:/api/user/retrieve/code
1. 传参{ "email":"xxx@xx.com"}
2. 发送成功状态码200返回{"message"："success","data":null}
3. 发送失败状态码250返回{"message"："验证码发送失败","data":null}
##6. 找回密码，重新设置密码
###url:/api/user/retrieve
1. 传参{ "email":"xxx@xx.com","id":"xxx","password":"xxx","validateRetrieve": "123456"}
2. 发送成功状态码200返回{"message"："success","data":null}
3. 发送失败状态码250返回{"message"："验证码发送失败","data":null}