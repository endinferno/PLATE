<template>
  <div class="login" clearfix>
    <div class="login-wrap">
      <el-row id="user">
        <el-form
          ref="loginForm"
          :model="find"
          label-position="left"
          label-width="80px"
          :rules="rules"
        >
          <!-- <el-form-item prop="email" label="验证邮箱">
            <el-input class="mail" v-model="find.email" placeholder="请输入验证邮箱"></el-input>
            <el-button type="primary" @click="doSend()" inline>发送</el-button>
          </el-form-item>
          <el-form-item prop="code" label="验证码">
            <el-input class="code" v-model="find.code" placeholder="请输入验证码"></el-input>
          </el-form-item> -->
          <el-form-item prop="password" label="新密码">
            <el-input class="pass" v-model="find.password" show-password placeholder="请输入新密码" prefix-icon></el-input>
          </el-form-item>
          <el-form-item prop="checkpass" label="确认密码">
            <el-input class="pass" v-model="find.checkpass" show-password placeholder="请再次输入新密码" prefix-icon></el-input>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-button type="primary" @click="doAlter()">修改密码</el-button>
        <el-button @click="doReturn()">返回</el-button>
      </el-row>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "Find",
  data: function() {
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.find.password) {
        callback(new Error("两次输入密码不一致！"));
      } else {
        callback();
      }
    };
    return {
      find: {
        email: "",
        password: "",
        checkpass: "",
        code: ""
      },
      rules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            pattern: /^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z]).*$/,
            message: "密码最少6位且包含大写字母小写字母和数字",
            trigger: "blur"
          }
        ],
        checkpass: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
          // {pattern:new RegExp("^"+this.user.password+"$"),message:"两次输入不一致！", trigger:'blur'}
          { validator: validatePass2, trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            pattern: /^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/,
            message: "请输入合法邮箱！",
            trigger: "blur"
          }
        ],
        code: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          { len: 4, message: "验证码长度为4位", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    doReturn() {
      this.$router.push("/");
    },
    doAlter(){
        axios
        .post("/alter/",{
            email:this.$store.state.email,
            password:this.find.password
        })
        .then(res => {
            if(res.data.status === 200){
                this.$message({
                    message:'修改成功,请重新登录',
                    type:'success'
                });
                this.$router.push('/');
            }else{
                this.$message.error('修改失败');
            }
        })
    }
  }
};
</script>
<style scoped>
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
  height: 240px;
  margin: 100px auto;
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
  padding-left: 50px;
  padding-right: 50px;
}
.mail{
    width: 180px;
    padding-right:20px;
}
.code{
    width: 120px;
    margin-right: 160px;
}
.el-input{
    width:200px;
    margin-right: 10px;
}
</style>
