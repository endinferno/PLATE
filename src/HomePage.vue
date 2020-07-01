<template>
  <el-container>
    <el-header>
      <Header></Header>
    </el-header>
    <el-main>
      <div class="left">
        <div class="bar">
          <el-input
            v-model="search"
            placeholder="请输入房间ID/房间名"
            style="width:180px;float:left;margin-right:10px;"
          ></el-input>
          <el-button class="el-icon-search" style="float:left" @click="doSearch()"></el-button>
          <el-button type="primary" style="float:left;" @click="create = true">创建房间</el-button>
        </div>
        <div class="roomlist">
          <div class="list">
            <RoomItem id="123456" type="123" describe="this is a test" name="todd"></RoomItem>
            <RoomItem id="123456" type="123" describe="this is a test" name="todd"></RoomItem>
            <RoomItem id="123456" type="123" describe="this is a test" name="todd"></RoomItem>
            <RoomItem id="123456" type="123" describe="this is a test" name="todd"></RoomItem>
            <RoomItem id="123456" type="123" describe="this is a test" name="todd"></RoomItem>
            <!-- 用v-for渲染 -->
          </div>
          <div class="bottom">
            <el-pagination layout="prev, pager, next" :total="30" :page-size="5"></el-pagination>
          </div>
        </div>
      </div>
      <div v-if="info" class="right">房间信息a</div>
      <el-drawer
        title="请创建一个房间吧！"
        :before-close="handleClose"
        :visible.sync="create"
        direction="rtl"
        ref="drawer"
      >
        <div class="createroom">
          <el-form class="form" :model="room">
            <el-form-item>ID:{{createID()}}</el-form-item>
            <el-form-item prop="name">
              房间名
              <el-input v-model="room.name" placeholder="请输入房间名" style="width:60%;margin-left:18px;"></el-input>
            </el-form-item>
            <el-form-item>房间类型
                <el-select v-model="room.type" placeholder="请选择" style="margin-left:5px;">
                    <el-option
                    v-for="item in types"
                    :key="item.value"
                    :value="item.value"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                开放状态
                <el-select v-model="room.status" placeholder="请选择" style="margin-left:5px;">
                    <el-option
                    v-for="item in status"
                    :key="item.value"
                    :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                房间描述
                <el-input v-model="room.describe" placeholder="请输入房间简单描述"></el-input>
            </el-form-item>
            <div class="push"></div>
          </el-form>
          <div class="footer">
            <el-button class="button-left" @click="cancelForm">取 消</el-button>
            <el-button class="button-right" type="primary" @click="$refs.drawer.closeDrawer()">确定</el-button>
          </div>
        </div>
      </el-drawer>
    </el-main>
  </el-container>
</template>
<script>
import Header from "./components/Header";
import RoomItem from "./components/RoomItem";
export default {
  components: {
    Header,
    RoomItem
  },
  data() {
    return {
      create: false, //抽屉是否显示
      info: false, //不点击就不显示房间信息 默认是false
      search: "",
      room: {
        id: "",
        name: "",
        avatar: "", //头像暂时不加入
        describe: "",
        type: "",
        owner: "",
        time: "",
        openstatus: ""
      },
      types: [
        { value: "一对一" },
        { value: "一对多" },
        { value: "多对一" },
        { value: "多对多" }
      ],
      status:[
          {value:'开放'},
          {value:'不开放'}
      ]
    };
  },
  methods: {
    doSearch() {},
    handleClose() {}, //创建房间的处理
    createID() {}, //随机一个Room ID
    cancelForm() {
      this.create = false;
    }
  }
};
</script>
<style scoped>
.el-main {
  display: inline-block;
}
.left {
  float: left;
  height: 600px;
  width: 65%;
}
.right {
  float: right;
  height: 600px;
  width: 30%;
  /* display: none; */
}
.bar {
  overflow: hidden;
}
.roomlist {
  padding: 15px;
  margin-top: 15px;
  height: 510px;
  background-color: white;
  border-radius: 5px;
  box-shadow: 5px 5px 5px #b3b3b3;
}
.list {
  height: 90%;
}
.bottom {
  height: 10%;
}
.footer {
  height: 10%;
}
.createroom {
  height: 100%;
  margin: 25px;
}
.form {
  min-height: 100%;
  margin-bottom: -80px;
}
.footer,
.push {
  height: 80px;
}
.button-left {
  width: 48%;
  float: left;
}
.button-right {
  width: 48%;
  float: right;
}
</style>