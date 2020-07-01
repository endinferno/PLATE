<template>
    <div class="parent">
        <div class="up">up</div>
        <div class="bottom">
            <el-input v-model="input" placeholder="请输入聊天内容" style="float:left"></el-input>
            <el-button type="primary" style="float:right">发送</el-button>
        </div>
    </div>
</template>
<script>
export default {
    name:'chat',
    data(){
        return{
            input:'',
        }
    }
}
</script>
<style scoped>
.parent{
    height: 600px;
    border: 2px solid #f2f2f2;
}
.up{
    height: 90%;
}
.bottom{
    height: 10%;
    display: block;
    margin-right: 5px;
    margin-left:5px;
    margin-top:12px;
}
.el-input{
    width:330px;
}
</style>