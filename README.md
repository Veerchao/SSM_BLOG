# SSM_Demo
1. SSM框架的搭建，并实现CRUD --11.2
2. 增加登陆功能。--11.7
3. 配置拦截器，完成用户的增删改查。 --11.8 ~11.17
4. 完成文章的service和持久层。  --11.20 ~ 11.24
5. 尝试使用Thymeleaf模板进行html页面的解析。 --11.27
6. 尝试使用REST风格。更改拦截器配置。--11.28
7. 	1. 新增查看文章列表   
	2. log出问题无法使用
	3. listArtcles.html中的createtime无法识别  --11.29
8.  1. 尝试mockmvc+JUnit 进行控制器的测试。
    2. 增加点击文章转到文章详情   --11.30
	
9.  1. 加入新增文章的模态框 --12.1
	2. 在artcle页面新增删除 
	3. 引入Bootstrap框架，美化网站。--12.3~12.19
	4. 加入修改的模态框    --12.26
	
10. 1. 新增管理员界面，提供用户管理功能 --12.27
    2. 采用	QQ邮箱的服务器，增加发送邮件功能 --12.28
	3. 登陆后，导航条自动改变。
	4. 增加用户注销功能。            -- 12.29
	
11. 1. 改为利用druid连接数据库，启用并配置config_filter给数据库密码加密。 
     2.  放弃自定义的interceptor,采用shiro进行登陆检测和权限控制（未完成）。 --2018.1.2
	
12. 1. 利用shiro完成了初步的权限管理，替代了自定义的拦截器。 --2018.1.8~2018.1.17
