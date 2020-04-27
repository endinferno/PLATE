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
          <el-form-item prop="email" label="邮箱">
            <el-input v-model="user.email" placeholder="请输入邮箱" prefix-icon></el-input>
          </el-form-item>
          <el-form-item prop="password" id="password" label="新密码">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="checkpass" id="checkpass" label="确认密码">
            <el-input v-model="user.checkpass" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="code" id="code" label="验证码">
            <el-input v-model="user.code" placeholder="请输入验证码" class='checkCode'></el-input>
            <span class="button_true" @click="getCode()" v-show="show">获取验证码</span>
            <span class="button_false" v-show="!show">{{total}}s后重新发送</span>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-button type="primary" @click="doRegister('loginForm')">确认修改</el-button>
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
    return {
      user: {
        email: "",
        password: "",
        checkpass: "",
        code: "",
      },
      total: 30,
      show: true, 
      timer: null,
      rules: {
        email: [
            {required:true,message:"请输入邮箱", trigger:'blur'},
            {pattern:/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/,message:"请输入合法邮箱！", trigger:'blur'}
        ],
        password: [
            {required:true,message:"请输入密码", trigger:'blur'},
            {pattern:/^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z]).*$/,message:"密码最少6位且包含大写字母小写字母和数字", trigger:'blur'}
        ],
        checkpass: [
            {required:true,message:"请再次输入密码", trigger:'blur'},
            {validator:validatePass2,trigger:'blur'}
        ],
        code: [
            {required:true,message:"请输入验证码", trigger:'blur'},
            {len:6,message:"验证码长度为6位", trigger:'blur'}
        ]
      }
    };
  },
  methods: {
    isEmail(str){
      var re=/^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+.([A-Za-z]{2,4})$/;
      return re.test(str)
    },
    doReturn() {
      this.$router.push("/");
    },
    countDown() {
      const COUNT = 30
      if(!this.timer){
        this.total = COUNT
        this.show = false
        this.timer = setInterval(() => {
          if (this.total > 0 && this.total <= COUNT) {
            this.total--;
          } else {
            this.show = true
            clearInterval(this.timer);
            this.timer = null;
          }
        }, 1000)
      }
      else{
        this.show = true
        clearInterval(timer)
        this.timer = null
      }
    },
    getCode() {
      if(!this.isEmail(this.user.email)){
        this.$message.error('邮箱错误，请重新输入');
        return
      }
      this.countDown()//这里在联调完成后应该放入success
      axios
        .post("/api/user/retrieve/code",{
            email:this.user.email
        })
        .then(res => {
            if(res.status === 200){
                this.$message({
                    message:'登录邮箱查看验证码',
                    type:'success'
                });
            }else{
                this.$message.error(res.data.message);
            }
        })
        .catch(failResponse=>{
             this.$message.error('网络错误');
           })
    },
    doRegister(formName){
      this.$refs[formName].validate((valid) => {
        if(valid){
          axios.post("/api/user/retrieve",{
            email:this.user.email,
            password:this.user.password,
            validateRetrieve:this.user.code
          })
          .then(res => {
            if(res.status === 200){
                this.$message({
                    message:'修改密码成功',
                    type:'success'
                });
                this.$router.push('/');
            }else{
              this.$message.error(res.data.message);
            }
          })
          .catch(failResponse=>{
            this.$message.error('网络错误');
          })
        }
        else{
          this.$message.error('信息不合法');
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
.checkCode {
  width: 150px;
  float: left;
}
.button_true {
  border-radius: 5px;
  background-color: #3399ff;
  color: white;
  cursor: pointer;
  padding: 10px;
}
.button_false {
  border-radius: 5px;
  background-color: #bbbbbb;
  color:  white;
  cursor: default;
  padding: 10px;
}
</style>
