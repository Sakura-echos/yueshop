<!--   -->
<template>
   <div>
      <el-tree
         :data="menus"
         :props="defaultProps"
         node-key="id"
      @node-click="nodeclick"
      >
      </el-tree>
   </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';

export default {
   // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    return {
      menus: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
   // 监听属性 类似于data概念
  computed: {},
   // 监控data中的数据变化
  watch: {},
   // 方法集合
  methods: {
    nodeclick (data, node, component) {
      console.log('节点对应的对象', data)
      console.log('节点对应的node', node)
      console.log('节点对应的组件', component)
        // 将数据传到父组件中
      this.$emit('tree-nodeclick', data, node, component)
    },
      // 定义一个方法，用于发起请求，从后台中获取三级分类的信息
    getMenus () {
      this.$http({
        url: this.$http.adornUrl('/product/category/list/tree'),
        method: 'get'
      }).then((data) => {
        console.log(data)
            // 将后台查询到数据data赋值到vue.data中
        this.menus = data.data.data
      })
    }
  },
   // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    this.getMenus()
  }
}
</script>
<style scoped>
</style>
