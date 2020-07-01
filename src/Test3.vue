<template>
  <el-container>
    <el-header>
      <Header></Header>
    </el-header>
    <el-main>
      <div class="left">
        <div style="overflow:hidden;">
          <el-button icon="el-icon-arrow-left" style="float:left">返回</el-button>
          <el-button icon="el-icon-edit" style="float:left">记录</el-button>
        </div>
        <div class="page">这里是一个画板</div>
      </div>
      <div class="right">
        <Chat></Chat>
      </div>
    </el-main>
  </el-container>
</template>
<script>
import Header from "./components/Header.vue";
import Chat from "./components/Chat.vue";
export default {
  components: {
    Header,
    Chat
  }
};
</script>
<style scoped>
.el-container {
  background-color: white;
}
.el-main {
  color: #1989fa;
  display: inline-block;
  /* height:600px; */
}
.left {
  float: left;
  background-color: white;
  width: 70%;
  height: 600px;
  /* padding:5px; */
}
.right {
  float: right;
  /* background-color: black; */
  width: 30%;
  height: 600px;
}
.page {
  height: 500px;
  margin: 5px 0 5px 0;
  border: 2px solid #f2f2f2;
}
</style>