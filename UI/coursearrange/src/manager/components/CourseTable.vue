<template>
  <div class="class-table">
<!--    <div class="top-select">-->
<!--      <el-select v-model="value1" placeholder="学期">-->
<!--        <el-option-->
<!--          v-for="item in semester"-->
<!--          :key="item.value"-->
<!--          :label="item.label"-->
<!--          :value="item.value"-->
<!--        ></el-option>-->
<!--      </el-select>-->
<!--      <el-select v-model="value2" placeholder="年级" @change="queryClass">-->
<!--        <el-option v-for="item in grade" :key="item.value" :label="item.label" :value="item.value"></el-option>-->
<!--      </el-select>-->
<!--      <el-select v-model="value3" placeholder="班级" @change="queryCoursePlan">-->
<!--        <el-option-->
<!--          v-for="item in classNo"-->
<!--          :key="item.value"-->
<!--          :label="item.label"-->
<!--          :value="item.value"-->
<!--        ></el-option>-->
<!--      </el-select>-->
<!--    </div>-->
    <div class="table-wrapper">
      <div class="tabel-container">

        <table>
          <thead>
          <tr>
            <th>时间</th>
            <th
              v-for="(moment, weekIndex) in classTableData.moment"
              :key="weekIndex">
              <p class="period">{{ moment }}</p>
            </th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(course) in courseData">
            <td class="first-titled-pane">
              <p>{{course.project}}</p>
            </td>
            <td>
              <p>{{course.moment1}}</p>
            </td>
            <td>
              <p>{{course.moment2}}</p>
            </td>
            <td>
              <p>{{course.moment3}}</p>
            </td>
            <td>
              <p>{{course.moment4}}</p>
            </td>
            <td>
              <p>{{course.moment5}}</p>
            </td>
            <td>
              <p>{{course.moment6}}</p>
            </td>
            <td>
              <p>{{course.moment7}}</p>
            </td>
            <td>
              <p>{{course.moment8}}</p>
            </td>
            <td>
              <p>{{course.moment9}}</p>
            </td>
            <td>
              <p>{{course.moment10}}</p>
            </td>
            <td>
              <p>{{course.moment11}}</p>
            </td>
            <td>
              <p>{{course.moment12}}</p>
            </td>
            <td>
              <p>{{course.moment13}}</p>
            </td>
            <td>
              <p>{{course.moment14}}</p>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      courseData: [],
      semester: [
        {
          value: "2019-2020-1",
          label: "2019-2020-1"
        }
      ],
      grade: [
        {
          value: "01",
          label: "高一"
        },
        {
          value: "02",
          label: "高二"
        },
        {
          value: "03",
          label: "高三"
        }
      ],
      classNo: [
        {
          value: "",
          label: ""
        }
      ],
      value1: "",
      value2: "",
      value3: "",
      classTableData: {
        lessons: [
          "8:00-8:30",
          "8:35-9:05",
          "9:10-9:40",
          "9:45-10:15",
          "10:20-10:50",
          "10:55-11:25",
          "11.25-11.55",
          "1:30-2:00",
          "2:05-2:35",
          "2:40-3:10",
          "3:15-3:45",
          "3:50-4:20",
          "4:25-4:55",
          "4:55-5:15",
        ],
        // 每一行对应周几的一列
        // 第1节：7.20-8.55
        // 第2节：9.10-10.45
        // 第3节：11.00-12.35

        // 下午：
        // 第4节：14.20-15.55
        // 第5节：16.10-17.45
        // courses: [
        //   ["", "", "", "", "", "", "", ""],
        //   ["生物", "物理", "化学", "政治", "历史"],
        //   ["语文", "数学", "英语", "历史", "", "化学", "物理", "生物"],
        //   ["生物", "", "化学", "政治", "历史", "英语", "数学", "语文"],
        //   ["语文", "数学", "英语", "历史", "政治", "", "物理", "生物"],
        //   ["生物", "物理", "化学", "", "历史", "英语", "数学", "语文"],
        //   ["语文", "数学", "英语", "", "", "", "", ""]
        // ],
        courses: [[], [], [], [], [], [], []
          // ["生物", "物理", "化学", "政治", "历史"],
          // ["语文", "数学", "英语", "历史", "", "化学", "物理", "生物"],
          // ["生物", "", "化学", "政治", "历史", "英语", "数学", "语文"],
          // ["语文", "数学", "英语", "历史", "政治", "", "物理", "生物"],
          // ["生物", "物理", "化学", "", "历史", "英语", "数学", "语文"]
        ],
        moment: [
          "8:00-8:30",
          "8:35-9:05",
          "9:10-9:40",
          "9:45-10:15",
          "10:20-10:50",
          "10:55-11:25",
          "11.25-11.55",
          "1:30-2:00",
          "2:05-2:35",
          "2:40-3:10",
          "3:15-3:45",
          "3:50-4:20",
          "4:25-4:55",
          "4:55-5:15",
        ],

      }
    };
  },
  created() {
    // /* mock随机数据*/
    // Mock.mock({
    //     lessons: ['08:00-09:00', '09:00-10:00', '10:00-11:00', '11:00-12:00', '13:00-14:00', '14:00-15:00', '15:00-16:00', '16:00-17:00'],
    //     'courses|7': [['生物', '物理', '化学', '政治', '历史', '英语', '', '语文']]
    // });
  },
  mounted() {
    this.queryCoursePlan();
  },
  methods: {

    // 查询班级编号，班级名
    queryClass() {
      this.$axios
        .get("http://localhost:9101/class-grade/" + this.value2)
        .then(res => {
          //alert(this.value2)
          let r = res.data.data;
          this.classNo.splice(0, this.classNo.length);
          this.value3 = ''
          r.map(v => {
            this.classNo.push({
              value: v.classNo,
              lable: v.className
            })
          })
        })
        .catch(error => {
          this.$message.error("失败")
        });
    },

    // 查询课程表
    queryCoursePlan() {
      // this.classTableData.courses.map((item, index) => {
      //   this.classTableData.courses[index].splice(0, this.classTableData.courses[index].length)
      // })
      this.$axios
        .get("http://localhost:9101/course/queryAllCourse")
        .then(res => {
          console.log(res)
          this.courseData = res.data.data
          console.log(this.courseData)
          this.$message({message: '查询成功', type: 'success'})
        })
    },

    /**
     * 数字转中文
     * @param {Number} num 需要转换的数字
     * @param {String} identifier 标识符
     * @returns {String} 转换后的中文
     */
    digital2Chinese(num, identifier) {
      const character = [
        "零",
        "一",
        "二",
        "三",
        "四",
        "五",
        "六",
        "七",
        "八",
        "九",
        "十",
        "十一",
        "十二",
        "十三",
        "十四"
      ];
      return identifier === "week" && (num === 0 || num === 7)
        ? "日"
        : character[num];
    },

    /**
     * 数字转中文
     * @param {Number} num 需要转换的数字
     * @param {String} identifier 标识符
     * @returns {String} 转换后的中文
     */
    digitalChinese(num, identifier) {
      const character = [
        "零",
        "一",
        "二",
        "三",
        "四",
        "五",
        "六",
        "七",
        "八",
        "九",
        "十",
        "十一",
        "十二",
        "十三",
        "十四"
      ];
      return identifier === "week" && (num === 0 || num === 7)
        ? "日"
        : character[num];
    },


  }
};
</script>

<style lang="less" scoped>

.first-titled-pane{
  /*灰色*/
  background-color: #E5E5E5;
}
.class-table {

}

.top-select {
  text-align: left;
  margin-left: 7px;
}

.table-wrapper {
  width: 100%;
  height: 100%;
  overflow: auto;
}

.tabel-container {
  margin: 7px;
  font-size: 30px;
}

table {
  table-layout: fixed;
  width: 100%;
  word-wrap: break-word;
  word-break: break-all;
  border-collapse: collapse;
}

thead {
  background-color: #67a1ff;
}

th {
  color: #fff;
  line-height: 17px;
  font-weight: normal;
  font-size: 15px;
}


tbody {
  background-color: #eaf2ff;
}

td {
  font-size: 13px;
  color: #677998;
  line-height: 13px;
}

td {
  width: 60px;
  padding: 12px 2px;
  font-size: 12px;
  text-align: center;
}

tr td:first-child {
  color: #333;
}

.period {
  font-size: 12px;
}

</style>
