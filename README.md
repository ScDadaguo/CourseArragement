# CourseArrange

#### 介绍
先给个Star再看嘛！


基于遗传算法的高中排课系统。
该项目是针对高校的教学生态，本人将其全部改版成了适用于高中阶段的教学生态，
当然了，节次数跟当前的高中肯定是不一样的，因为我当时的课题是为培训机构安排，
大家只需要更改时间片的数量即可实现不同节次课程的编排，照葫芦画瓢即可。

如果喜欢GitHub，下方是项目在GitHub的地址。
GitHub地址: https://github.com/lyk1576070851/CourseArragement
别忘了给一个star哦！


#### 软件架构
前端技术栈：
    Vue2.x + Element UI，使用npm包管理工具

后端技术栈：
    JDK1.8 + Spring Boot + MySQL8.0 + Mybatis-Plus，使用maven实现包管理，开发工具：IDEA



#### 安装教程

1.  配置好JDK环境，node.js环境，安装好Vue脚手架工具一级maven环境
2.  前端项目在UI目录下的文件夹内，在CourseArrange目录下运行命令：npm install 安装完相关的前端依赖
3.  执行 npm run dev 命令将前端项目启动
4.  后端项目在配置好JDK环境之后，使用IDEA等IDE工具打开，将项目配置成自己本地的Maven(建议使用阿里云镜像)
5.  运行maven安装项目所需依赖，配置好数据库的连接，待依赖安装完成启动项目的Application启动类即可(不会的也不教了哈)
6.  用到文件上传功能时，需要配置好自己的阿里云key与密钥，在utils下面的AliyunUtil类下面
#### 实现功能

1.  系统有管理员（教务处主任）、讲师、学生三种用户
2.  前端比较菜，应用启动后进入的引导页面如下，根据需求进入不同登录页面
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0708/111552_fafcb0e9_5505532.png "屏幕截图.png")
3.  这里主要放管理员的功能截图
    1）管理员登录成功后进入到系统数据页面
        ![输入图片说明](https://images.gitee.com/uploads/images/2020/0708/111732_908e9b16_5505532.png "屏幕截图.png")
    2）课程计划是某一个学期需要安排上的课程，应该一次性全部导入
        ![输入图片说明](https://images.gitee.com/uploads/images/2020/0708/111837_60f807d0_5505532.png "屏幕截图.png")
        可以手动添加课程任务（课程编号，讲师编号等信息一定要与数据库对得上）、也可以使用Excel模板填写后导入Excel文件直接将课程任务导入（点击“导入”选择好文件之后，点上传到服务器即可），没有模板可以点击下载模板下载对应的Excel模板（模板文件也根据UploadController.java中的路径存放在自己本地），随后根据要求填写模板，点击“排课”按钮开始排课，排课完成跳转到课表页面
        ![输入图片说明](https://images.gitee.com/uploads/images/2020/0708/111952_de046c5a_5505532.png "屏幕截图.png")
    3）课程表效果如下所示（选择对应的年级、对应的班级之后即可显示）
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0708/112342_b83b9f32_5505532.png "屏幕截图.png")

4.  其余的功能就不多介绍了，都是属于辅助功能
    1）讲师管理（添加、删除、编辑讲师的信息）
    2）网课管理（这个是后面加的，在线教育的前端部分单独做了一个项目放在Nginx中访问的，就没放出来了，这个模块本来是给学生课余时间在线学习用的）
    3）班级管理
    4）教材管理（就是排课任务中使用的哪些课程名称，排课任务的课程编号与这里的课程编号一样）
    5）学生管理
    6）教学资料管理（主要是讲师、管理员给某一个班级发布作业，学习文档用的，还有在线测试，这个在在线教育那个前端项目中，没做完，忽略这个）
    7）教学设施管理（教学楼，教室管理，还有教学区域就是：比如我要高一年级在1号教学楼上课，也可以让它在1号、2号多个地方上课，后面排课算法找教室的时候就根据这个数据表去查询教学位置的安排）
![输入图片说明](https://images.gitee.com/uploads/images/2020/0708/113007_3e1321af_5505532.png "屏幕截图.png")
    


#### 后面的话

1.  本人代码写得比较烂，所以大家就忽略我的那些你认为垃圾的东西
2.  开源出来只是给大家做排课方面的小项目有一个参考
3.  这是我的毕业设计项目，答辩被老师说排课系统没价值（给了我75分，很心痛），心中一万个草泥马，这些老师不知道想什么
4.  后面如果有时间我也希望去维护一下这个项目，有兴趣的小伙伴可以跟我一起搞搞
5.  希望项目可以帮助到广大小伙伴们

如果项目对你有帮助，也可以请我喝喝奶茶哦~
![输入图片说明](https://images.gitee.com/uploads/images/2021/0204/084342_ec588898_5505532.png "屏幕截图.png")![输入图片说明](https://images.gitee.com/uploads/images/2021/0204/084506_5c50d8ef_5505532.png "屏幕截图.png")
