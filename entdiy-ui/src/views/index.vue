<template>
  <div class="app-container">
    <el-alert
      title="显示内容提示"
      type="info"
      :closable="false"
      show-icon>
      当前首页内容直接Markdown组件加载主工程的README.md文件内容，会导致部分相对链接存在问题，建议直接浏览GIT托管站点在线页面内容！
    </el-alert>
    <el-divider></el-divider>
    <el-card class="box-card">
      <!-- class markdown-body 必须加，否则标签样式会出现问题 -->
      <div class="markdown-body">
        <markdown/>
      </div>
    </el-card>
  </div>
</template>

<script>
// 引入 markdown 文件，引入后是一个组件，需要在 components 中注册
import markdown from '../../../README.md'
// 代码高亮
import 'highlight.js/styles/github.css'
// 其他元素使用 github 的样式
import 'github-markdown-css'

export default {
  components: {markdown},
}

</script>
