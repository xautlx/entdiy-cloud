<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'ProjectDoc',
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
