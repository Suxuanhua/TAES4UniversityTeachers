1.管理员 登录/注册/删除/修改/查询/查询全部管理员

        管理员类型：超级管理员（root）、管理员（增删改查）、审阅者（审查）

        -- 实现 管理员头像上传/修改，通过算法可以将任何比例的 图片切割成正方形
        -- 实现 通过 验证码 验证操作
        -- 实现权限控制，不同类型用户，具备不同权限

2.教师信息 添加/删除/修改/查询/查询全部教师信息
        -- 实现 教师头像上传/修改，通过算法可以将任何比例的 图片切割成正方形
        -- 实现 通过 验证码 验证操作
        -- 实现 赛选功能：通过教师性别/职称 进行赛选
        -- 实现 通过 教师信息条目添加课程信息
        -- 实现权限控制，不同类型用户，具备不同权限

3.课程信息 添加/删除/修改/查询/查询全部课程
        -- 实现 通过 验证码 验证操作
        -- 实现 赛选功能：通过课程名称/年级/班级/授课老师 进行赛选
        -- 实现权限控制，不同类型用户，具备不同权限
        -- 实现权限控制，不同类型用户，具备不同权限

4.全局搜索
        -- 通过关键字（职称、ID、人名、邮件、课程）进行全系统数据库模糊搜索，时间不够，未实现对 全部动态 内的模块进行搜索。

5.控制中心
        -- 实现对整个系统的信息进行分类、预览、及功能跳转（全部教师个数、不同职称的教师个数、全部课程总个数、不同年级的课程总个数、）
        -- 不同年级课程分类实现动态分类（根据系统时间进行学年分类，实现动态显示数据库、大一到大四的课程个数）

6.全部动态（论文列表/活动情况/奖惩通知/出版专著）
        -- 时间不够，统一以发贴的方式展现在动态列表，未实现关键字分组。

7.设置中心，查看/修改系统配置文件，控制应用服务器重启
        -- 时间不够，未实现

8.统一异常处理
9.统一日志记录
10.数据回显
11.记录请求头到 数据库。
