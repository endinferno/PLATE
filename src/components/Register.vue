<template>
  <div class="login" clearfix>
    <div class="login-wrap">
      <el-row justify="center" id="user">
        <el-form
          ref="loginForm"
          :rules="rules"
          label-position="left"
          label-width="80px"
          status-icon
          :model="user"
        >
          <el-form-item prop="username" label="用户名">
            <el-input v-model="user.username" placeholder="请输入用户名" prefix-icon></el-input>
          </el-form-item>
          <el-form-item prop="password" id="password" label="密码">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="checkpass" id="checkpass" label="确认密码">
            <el-input v-model="user.checkpass" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="email" id="email" label="验证邮箱">
            <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item prop="code" id="code" label="验证码">
            <el-input v-model="user.code" placeholder="请输入验证码"></el-input>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-button type="primary" @click="doRegister()">立即注册</el-button>
        <el-button @click="doReturn()">返回</el-button>
      </el-row>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "Register",
  data: function() {
    // var validateUsername = (rule, value, callback) => {
    //   let uPattern = /^[a-zA-Z0-9_-]{8,16}$/; //8到16位 字母数字下划线减号
    //   if (value === '') {
    //     return callback(new Error("用户名不能为空"));
    //   }
    //   else if (uPattern.test(value)) {
    //     console.log(value);
    //     console.log(uPattern.test(value));
    //     callback();
    //   } else {
    //     callback(new Error("用户名由8到16位字母数字下划线或减号组成"));
    //   }
    // };
    // var validatePass = (rule,value,callback) => {
    //     var pPattern = /^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$/;
    //     if(value === ''){
    //         return callback(new Error('请输入密码'));
    //     }
    //     else if(pPattern.test(value)){
    //         callback();
    //     }
    //     else{
    //         callback(new Error("密码必须大于等于6位且包含大写字母小写字母和数字"));
    //     }
    // };
    var validatePass2 = (rule, value, callback) => {
        if(value === ''){
            callback(new Error('请再次输入密码'));
        }
        else if(value !== this.user.password){
            callback(new Error('两次输入密码不一致！'));
        }
        else{
            callback();
        }
    };
    // var validateCode = (rule,value,callback) => {
    //     if(value.length !== 4){
    //         callback(new Error('验证码为4位数'));
    //     }
    //     else{
    //         callback();
    //     }
    // };
    // var validateEmail = (rule,value,callback) => {
    //     var ePattern = /^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+\.([A-Za-z]{2,4})$/;
    //     if(value === ''){
    //         callback(new Error('请输入邮箱'));
    //     }
    //     else if(ePattern.test(value)){
    //         callback();
    //     }
    //     else{
    //         callback(new Error('邮箱格式不正确！'));
    //     }
    // }
    return {
      user: {
        username: "",
        password: "",
        checkpass: "",
        email: "",
        code: ""
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger:'blur' },
          { pattern: /^[a-zA-Z0-9_-]{8,16}$/, message:"用户名必须由8到16位字母数字下划线或减号组成", trigger:'blur'}
        ],
        password: [
            {required:true,message:"请输入密码", trigger:'blur'},
            {pattern:/^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z]).*$/,message:"密码最少6位且包含大写字母小写字母和数字", trigger:'blur'}
        ],
        checkpass: [
            {required:true,message:"请再次输入密码", trigger:'blur'},
            // {pattern:new RegExp("^"+this.user.password+"$"),message:"两次输入不一致！", trigger:'blur'}
            {validator:validatePass2,trigger:'blur'}
        ],
        email: [
            {required:true,message:"请输入邮箱", trigger:'blur'},
            {pattern:/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/,message:"请输入合法邮箱！", trigger:'blur'}
        ],
        code: [
            {required:true,message:"请输入验证码", trigger:'blur'},
            {len:4,message:"验证码长度为4位", trigger:'blur'}
        ]
      }
    };
  },
  methods: {
    doReturn() {
      this.$router.push("/");
    },
    doRegister(){
        axios
        .post("/register/",{
            name:this.user.username,
            password:this.user.password,
            email:this.user.email,
            code:this.user.code
        })
        .then(res => {
            if(res.data.status === 200){
                this.$message({
                    message:'注册成功',
                    type:'success'
                });
                this.$router.push('/');
            }else{
                this.$message.error('注册失败');
                this.$refs[this.user].resetFields();
            }
        })
    }
  }
};
</script>
<style scoped>
.el-form-item{
    padding-left:50px;
    padding-right:50px;
}
.login {
  background: url("../assets/login_background1.jpg") no-repeat;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  min-width: 1000px;
  z-index: -10;
  zoom: 1;
  background-color: #fff;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -o-background-size: cover;
  background-position: center 0;
}
.login-wrap {
  /* visibility: hidden; */
  /* background-color: #00000030; */
  background-color: white;
  background-size: cover;
  width: 450px;
  height: 430px;
  margin: 80px auto;
  overflow: hidden;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 5px;
  text-align: center;
  box-shadow: 5px 5px 5px;
}
a {
  margin-right: 10px;
  font-size: 5px;
}
#user {
  padding-top: 30px;
}
#last {
  margin-left: 105px;
}
.el-form-item {
  margin-top: 20px;
}
</style>
