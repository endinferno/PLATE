<template>
  <el-card body-style="padding:10px;">
    <div class="left">ID:{{this.id}} 房间名:{{this.name}} 房间类型:{{this.type}} 房间描述:{{this.describe}}</div>
    <div class="right">
      <el-button @click="doInfo" class="info">查看信息</el-button>
      <el-button @click="doInRoom()" class="in">进入房间</el-button>
    </div>
  </el-card>
</template>
<script>
export default {
  name: "RoomItem",
  props: {
    id: "",
    name: "",
    avatar: "",
    describe: "",
    type: "",
    owner: "",
    time: "",
    openstatus: ""
  },
  methods: {
    doInRoom() {},
    doInfo(){}
  }
};
</script>
<style scoped>
.left {
  float: left;
  height: 50px;
  line-height:50px;
  width:65%;
  text-align: left;
  margin-left:15px;
}
.right {
  float: right;
  width:30%;
}
.el-card {
  height: 70px;
  margin:15px;
}
.el-button{
    height:50px;
    /* width:190px; */
}
</style>