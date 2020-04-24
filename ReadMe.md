#跨域域名修改
修改config/index.js中的第15行中的proxyTable

#提交条件
##登录
1.邮箱必须符合邮箱格式：/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/
2.密码最少6位且包含大写字母小写字母和数字
不满足上述两个条件则不予提交。

##注册
###注册验证码发送
邮箱必须符合邮箱格式：/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/
否则无法获取验证码。
###注册
1.邮箱必须符合邮箱格式：/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/
2.昵称必须满足8到16位字母数字下划线或减号组成
3.密码最少6位且包含大写字母小写字母和数字
4.确认密码要和密码相同
5.验证码必须为6位
否则不予提交。

##找回密码
###找回验证码发送
邮箱必须符合邮箱格式：/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/
否则无法获取验证码。
###注册密码
1.邮箱必须符合邮箱格式：/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/
2.密码最少6位且包含大写字母小写字母和数字
3.确认密码要和密码相同
4.验必须为6位
否则不予提交。

#接口请求
##登录
post("/api/user/login",
{
email:this.user.username,
password:this.user.password
})
从response的status中判断接下来怎么执行，
1.若status为200，则显示登陆成功，并执行window.sessionStorage.setItem('UID', res.data.date)
2.若status大于200且小于300，则显示后端返回的报错信息
3.若statuts大于等于300，则抛出异常，显示网络错误（可考虑改为显示status）

##注册
###注册验证码获取
post("/api/user/register/code",
{
email:this.user.email
})
发送post请求前进入30s倒计时，倒计时结束后才能再次获取验证码。
从response的status中判断接下来怎么执行，
1.若status为200，则提示打开邮箱获取验证码
2.若status大于200且小于300，则显示后端返回的报错信息
3.若statuts大于等于300，则抛出异常，显示网络错误（可考虑改为显示status）

###注册
post("/api/user/register",
{
email:this.user.email,
name:this.user.name,
password:this.user.password,
validateRegistration:this.user.code
})
从response的status中判断接下来怎么执行，
1.若status为200，则提示注册成功，并跳回根目录
2.若status大于200且小于300，则显示后端返回的报错信息
3.若statuts大于等于300，则抛出异常，显示网络错误（可考虑改为显示status）

##找回密码
###找回验证码获取
post("/api/user/retrieve/code",
{
email:this.user.email
})
发送post请求前进入30s倒计时，倒计时结束后才能再次获取验证码。
从response的status中判断接下来怎么执行，
1.若status为200，则提示打开邮箱获取验证码
2.若status大于200且小于300，则显示后端返回的报错信息
3.若statuts大于等于300，则抛出异常，显示网络错误（可考虑改为显示status）

###找回密码
post("/api/user/retrieve",
{
email:this.user.email,
password:this.user.password,
validateRetrieve:this.user.code
})
从response的status中判断接下来怎么执行，
1.若status为200，则提示修改密码成功，并跳回根目录
2.若status大于200且小于300，则显示后端返回的报错信息
3.若statuts大于等于300，则抛出异常，显示网络错误（可考虑改为显示status）
