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
##6. 找回密码，重新设置密码POST
###url:/api/user/retrieve
1. 传参{ "email":"xxx@xx.com","id":"xxx","password":"xxx","validateRetrieve": "123456"}
2. 发送成功状态码200返回{"message"："success","data":null}
3. 发送失败状态码250返回{"message"："验证码发送失败","data":null}
##7. 创建房间 PUT
###url:/api/room/createRoom
1. 传参{"describe": "string","openStatus": "string","owner": "string","roomName": "string","type": 0}
2. 发送成功状态码200返回{"message"："success","data":返回房间的ID号}
3. type String（1：“一对一”,2：”一对多”,3：”多对一”,4：”多对多”）;openStatus String（“开放”，“不开放”）
4. 发送失败状态码250返回{"message": "创建房间失败","data": null}
##8. 分页搜索 POST
###url:/api/room/searchRooms
1. 传参{"cnt": 1,"keyword": "string","page": 1,"way": "string"}
2. 发送成功状态码200返回{
           "message": "success",
           "data": "{\"content\":[],\"empty\":true,\"first\":false,\"last\":true,\"number\":1,\"numberOfElements\":0,\"pageable\":{\"offset\":1,\"pageNumber\":1,\"pageSize\":1,\"paged\":true,\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"unpaged\":false},\"size\":1,\"sort\":{\"$ref\":\"$.pageable.sort\"},\"totalElements\":0,\"totalPages\":0}"
         }
搜索所符合条件的房间，并按照一种特定的方式对搜索结果排序（数据库里的默认排序如果不会出现顺序问题也可），然后返回搜索结果总数量和第n页的m项的具体内容。
3. way String (“按房间名”，“按房主”，“按房间号”);
4. 发送失败状态码250返回{"message": "参数错误","data": null}
##9. 分页查询所有房间 GET
###url:api/room/getRooms
1. 传参 Parameters  "cnt":"int",""page":"int";
2. 发送成功状态码200返回{
           "message": "success",
           "data": "{\"content\":[],\"empty\":true,\"first\":false,\"last\":true,\"number\":1,\"numberOfElements\":0,\"pageable\":{\"offset\":1,\"pageNumber\":1,\"pageSize\":1,\"paged\":true,\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"unpaged\":false},\"size\":1,\"sort\":{\"$ref\":\"$.pageable.sort\"},\"totalElements\":0,\"totalPages\":0}"
         }
3. 搜索所符合条件的房间，并按照一种特定的方式对搜索结果排序（数据库里的默认排序如果不会出现顺序问题也可），然后返回搜索结果总数量和第n页的m项的具体内容。
4. 发送失败状态码250返回{"message": "参数错误","data": null}