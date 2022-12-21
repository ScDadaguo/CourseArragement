<template>
  <div>
    <!-- 功能 -->
    <div class="header-menu">
      <el-input placeholder="搜索仪器" v-model="keyword" @clear="inputListener" clearable>
        <el-button slot="append" type="primary" icon="el-icon-search" @click="searchStudent">搜索</el-button>
      </el-input>
<!--      <el-button type="primary" @click="addPatient()" class="addon">添加医生</el-button>-->
    </div>
    <!-- 数据显示 -->
    <el-table :data="machineData" size="mini" :stripe="true" :highlight-current-row="true">
      <el-table-column label="序号" type="selection"></el-table-column>
      <!-- <el-table-column prop="id" label="ID"></el-table-column> -->
      <el-table-column prop="name" label="仪器名称" fixed width="100"></el-table-column>
      <el-table-column prop="responsibilities" label="治疗项目" fixed width="100"></el-table-column>

<!--      <el-table-column prop="operation" label="操作">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="danger" size="mini" @click="deleteById(scope.$index, scope.row)">删除</el-button>-->
<!--          <el-button type="primary" size="mini" @click="editById(scope.$index, scope.row)">编辑</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>


    <!-- 上一页，当前页，下一页 -->
    <div class="footer-button">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="page"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "DoctorList",
  data() {
    return {
      machineData: [],
      editFormData: [],
      keyword: "",
      page: 1,
      pageSize: 10,
      total: 0,
      value1: "", // 年级
      value2: "", // 班级
      addPatientRules: {
        name: [
          { required: true, message: "请输入真实姓名", trigger: "blur" }
        ],
        responsibilities: [{ required: true, message: "治疗项目必填", trigger: "blur" }]
      },

      project: [
        {
          value: 0,
          label: "经颅磁"
        },
        {
          value: 1,
          label: "强磁"
        },
        {
          value: 2,
          label: "脑循环"
        },
        {
          value: 3,
          label: "生物反馈"
        },
        {
          value: 4,
          label: "NMES"
        },
        {
          value: 5,
          label: "膈肌"
        },
        {
          value: 6,
          label: "直立床"
        },
        {
          value: 7,
          label: "踩车"
        },
        {
          value: 8,
          label: "导频"
        },
        {
          value: 9,
          label: "蜡疗"
        },
        {
          value: 10,
          label: "直立床楼"
        },
        {
          value: 11,
          label: "PT"
        },
        {
          value: 12,
          label: "OT"
        },
        {
          value: 13,
          label: "ST"
        },
        {
          value: 14,
          label: "感统"
        }
      ],
      locationType: [
        {
          value: 0,
          label: "楼上"
        },
        {
          value: 1,
          label: "楼下"
        },
      ],
      classNo: [
        {
          value: "",
          label: ""
        }
      ],
      visibleForm: false,
      visibleAddForm: false,
      editFormRules: {
        username: [{required: true, message: "请输入昵称", trigger: "blur"}],
        realname: [{required: true, message: "请输入姓名", trigger: "blur"}],
        grade: [{required: true, message: "请输入年级", trigger: "blur"}],
        classNo: [{required: true, message: "请输入班级", trigger: "blur"}],
        telephone: [{required: true, message: "请输入联系电话", trigger: "blur"}],
        email: [{required: true, message: "请输入邮件", trigger: "blur"}],
        address: [{required: true, message: "请输入地址", trigger: "blur"}],
        age: [{required: true, message: "请输入年龄", trigger: "blur"}]
      },
      addPatientForm: {
        name: "",
        bedNumber: "",
        phone: "",
        responsibilities: "",
        hyperbaricOxygenStartTime: "",
        hyperbaricOxygenEndTime: "",
        location: "",
      }
    };
  },

  mounted() {
    this.allMachines();
  },

  methods: {

    // 提交添加讲师的表单
    addCommit() {
      this.addPatientForm.responsibilities = this.addPatientForm.responsibilities.toString();
      console.log(this.addPatientForm);
      this.$axios
        .post("http://localhost:9101/course/addPatient", this.addPatientForm)
        .then(res => {
          console.log(res);
          if (res.data.code == 0) {
            this.allTeacher();
            this.visibleAddForm = false;
            this.$message({ message: "添加讲师成功", type: "success" });
          }
        })
        .catch(error => {
          this.$message.error("添加讲师失败");
        });
    },

    addPatient() {
      this.visibleAddForm = true;
    },

    // 清空年级回到查询所有学生
    gradeListener() {
      this.allMachines()
      this.value2 = ''
    },

    // 清空班级回到查询所有班级
    classListener() {

    },

    // 查询班级信息
    queryClass() {
      this.$axios
        .get("http://localhost:9101/class-grade/" + this.value1)
        .then(res => {
          let ret = res.data.data
          this.classNo.splice(0, this.classNo.length)
          this.value2 = ""
          ret.map(v => {
            this.classNo.push({
              value: v.classNo,
              label: v.className
            });
          });
        })
        .catch(error => {

        });
    },


    /***
     * 编辑提交
     */
    commit() {
      this.modifyStudent(this.editFormData)
    },

    inputListener() {
      this.allMachines()
    },

    /**
     * 查询所有医生
     */
    allMachines() {
      this.$axios
        .get("http://localhost:9101/course/queryAllMachines/" + this.page)
        .then(res => {
          let ret = res.data.data
          this.machineData = ret.records
          this.total = ret.total
          // this.$message({message:'查询成功', type: 'success'})
        })
        .catch(error => {
          this.$message.error("查询学生列表失败")
        });
    },

    /**
     * 关键字查询学生
     */
    searchStudent() {
      this.$axios
        .get("http://localhost:9101/student/search/" + this.keyword)
        .then(res => {
          let ret = res.data.data
          this.machineData = ret.records
          this.total = ret.total
          this.$message({message: "查询成功", type: "success"})
        })
        .catch(error => {
          this.$message.error("查询失败")
        });
    },

    /**
     * 根据id删除学生
     */
    deleteById(index, row) {
      this.deleteStudentById(row.id)
    },

    deleteStudentById(id) {
      this.$axios
        .delete("http://localhost:9101/student/delete/" + id)
        .then(res => {
          this.$message({message: "删除成功", type: "success"})
          this.allMachines()
        })
        .catch(error => {
          this.$message.error("删除失败")
        });
    },

    /**
     * 编辑学生
     */
    editById(index, row) {
      let modifyId = row.id
      this.editFormData = row
      this.visibleForm = true
    },

    /**
     * 更新学生
     */
    modifyStudent(modifyData) {
      this.$axios
        .post("http://localhost:9101/student/modify/" + this.editFormData.id, modifyData)
        .then(res => {
          this.$message({message: "更新成功", type: "success"})
          this.allMachines()
          this.visibleForm = false
        })
        .catch(error => {
          this.$message.error("更新失败")
        });
    },

    handleSizeChange() {
    },

    handleCurrentChange(v) {
      this.page = v
      this.allMachines()
    }
  }
};
</script>

<style lang="less" scoped>
.el-input-group {
  width: 40%;
}

.header-menu {
  margin-bottom: 5px;
  padding: 0;
  text-align: left;
  margin-bottom: 5px;
}

.footer-button {
  margin-top: 10px;
}
</style>
