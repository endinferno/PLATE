<template>
  <el-menu :default-active="activeIndex" class="header" mode="horizontal" >
    <el-menu-item index="1" style="color:#1989fa;">主页</el-menu-item>
  </el-menu>
</template>
<script>
export default {
  name:'Header',
  data(){
    return{
      activeIndex:'1',
    }
  }
};
</script>
<style scoped>
.el-header {
  color: #1989fa; /*北航蓝*/
  /* background-color:#1989fa; */
  padding:0px;
}
</style>