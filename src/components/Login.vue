<template>
  <div class="login" clearfix>
    <div class="login-wrap">
      <el-row type="flex" justify="center" id="user">
        <el-form ref="loginForm" :model="user" label-position="left" label-width="80px">
          <el-form-item prop="username" label="用户名">
            <el-input v-model="user.username" placeholder="请输入用户名" prefix-icon></el-input>
          </el-form-item>
          <el-form-item prop="password" id="password" label="密码">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item id="last">
            <router-link to="/find">找回密码</router-link>
            <router-link to="/register">注册账号</router-link>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-button type="primary" @click="doLogin()">登 录</el-button>
      </el-row>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "Login",
  data: function() {
    return {
      user: {
        username: "",
        password: ""
      }
    };
  },
  methods:{
      doLogin(){
          if(!this.user.username){
              this.$message.error("请输入用户名！");
              return;
          }
          if(!this.user.password){
              this.$message.error("请输入密码！");
              return;
          }
          if(this.user.password&&this.user.username)
          {
              axios
              .post("/login/",{
                  name:this.user.username,
                  password:this.user.password
              })
              .then(res => {
                  if(res.data.status === 200){
                      this.$message({
                          message:'登录成功',
                          type:'success'
                      });
                      this.$router.push('/personal');
                  }else{
                      this.$message.error("您输入的用户或密码错误！");
                  }
              });
          }
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
  width: 400px;
  height: 300px;
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
#user{
    padding-top:30px;
}
#last{
    margin-left:105px;
}
.el-form-item{
    margin:20px;
}
</style>
