<template>
  <div class="BasicInformationSettings">
    <!-- 面包屑导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础信息设置</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 标签页 -->
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="部门管理" name="department"></el-tab-pane>
      <el-tab-pane label="职位管理" name="position"></el-tab-pane>
      <el-tab-pane label="职称管理" name="jobLevel"></el-tab-pane>
      <el-tab-pane label="奖惩规则" name="rewardPunish"></el-tab-pane>
      <el-tab-pane label="权限组" name="permissionGroup"></el-tab-pane>
    </el-tabs>

    <!-- 职称管理内容 -->
    <div v-if="activeTab === 'jobLevel'">
      <!-- 添加表单 -->
      <div class="add-form">
        <el-form :model="form" :inline="true" size="small">
          <el-form-item label="职称名称">
            <el-input v-model="form.name" placeholder="请输入职称名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="职称级别">
            <el-select v-model="form.level" placeholder="请选择职称级别">
              <el-option label="初级" value="初级"></el-option>
              <el-option label="中级" value="中级"></el-option>
              <el-option label="高级" value="高级"></el-option>
              <el-option label="专家" value="专家"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addJobLevel">添加</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 数据表格 -->
      <div class="data-table">
        <el-table
            :data="jobLevels"
            style="width: 100%"
            border
            @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="id" label="编号" width="80"></el-table-column>
          <el-table-column prop="name" label="职称名称"></el-table-column>
          <el-table-column prop="level" label="职称级别"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column prop="enabled" label="是否启用">
            <template #default="{ row }">
              <el-tag :type="row.enabled ? 'success' : 'danger'">
                {{ row.enabled ? '启用' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="{ row }">
              <el-button size="mini" @click="editJobLevel(row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="deleteJobLevel(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 批量操作按钮 -->
        <div class="batch-actions">
          <el-button type="danger" @click="batchDelete">批量删除</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'JobLevelManage',
  data() {
    return {
      activeTab: 'jobLevel',
      form: {
        name: '',
        level: ''
      },
      jobLevels: [],
      selectedRows: []
    }
  },
  created() {
    this.loadJobLevels()
  },
  methods: {
    handleTabClick(tab) {
      // 处理标签页切换
    },
    loadJobLevels() {
      // 获取职称列表数据
      this.$http.get('/system/basic/joblevel/').then(response => {
        this.jobLevels = response.data
      }).catch(error => {
        console.error('获取职称列表失败:', error)
      })
    },
    addJobLevel() {
      if (!this.form.name || !this.form.level) {
        this.$message.warning('请填写完整的职称信息')
        return
      }

      this.$http.post('/system/basic/joblevel/', this.form).then(response => {
        this.$message.success('添加成功')
        this.form.name = ''
        this.form.level = ''
        this.loadJobLevels()
      }).catch(error => {
        console.error('添加职称失败:', error)
        this.$message.error('添加失败')
      })
    },
    editJobLevel(row) {
      // 编辑职称
      this.$prompt('请输入职称名称', '编辑职称', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: row.name
      }).then(({ value }) => {
        if (!value.trim()) {
          this.$message.warning('职称名称不能为空')
          return
        }

        this.$http.put(`/system/basic/joblevel/`, { name: value, level: row.level }).then(response => {
          this.$message.success('修改成功')
          this.loadJobLevels()
        }).catch(error => {
          console.error('修改职称失败:', error)
          this.$message.error('修改失败')
        })
      }).catch(() => {
        this.$message.info('取消编辑')
      })
    },
    deleteJobLevel(id) {
      this.$confirm('确定要删除该职称吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete(`/system/basic/joblevel/${id}`).then(response => {
          this.$message.success('删除成功')
          this.loadJobLevels()
        }).catch(error => {
          console.error('删除职称失败:', error)
          this.$message.error('删除失败')
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    batchDelete() {
      if (this.selectedRows.length === 0) {
        this.$message.warning('请先选择要删除的记录')
        return
      }

      this.$confirm(`确定要删除选中的${this.selectedRows.length}条记录吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = this.selectedRows.map(row => row.id)
        this.$http.delete(`/system/basic/joblevel/`, { data: { ids } }).then(response => {
          this.$message.success('批量删除成功')
          this.loadJobLevels()
          this.selectedRows = []
        }).catch(error => {
          console.error('批量删除失败:', error)
          this.$message.error('批量删除失败')
        })
      }).catch(() => {
        this.$message.info('已取消批量删除')
      })
    },
    handleSelectionChange(val) {
      this.selectedRows = val
    }
  }
}
</script>

<style scoped>
.job-level-manage {
  padding: 20px;
}

.add-form {
  margin-bottom: 20px;
}

.data-table {
  margin-top: 20px;
}

.batch-actions {
  margin-top: 20px;
  text-align: left;
}
</style>
