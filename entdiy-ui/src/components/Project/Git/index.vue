<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'ProjectGit',
  data() {
    return {
      url: 'https://gitee.com/xautlx/entdiy-cloud'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
