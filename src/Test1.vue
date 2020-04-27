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
          <el-button icon="el-icon-monitor" style="float:right">准确率</el-button>
          <el-button icon="el-icon-time" style="float:right">测试时间</el-button>
        </div>

        <div class="page">一些图片，后面用组件替代</div>
        <div>
          <el-button>
            下一页
            <i class="el-icon-arrow-right"></i>
          </el-button>
        </div>
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