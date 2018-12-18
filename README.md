# 一款监听文件变化的eclipse插件
## 怎么使用？
1.将改项目导入eclipse </br>
2.在项目上右击选择 -> export 弹出，如下图： </br>
![export1](https://raw.githubusercontent.com/ZhaoXiCeil/listen-file-change-eclipse/master/profile/export1.jpg) </br>
3.选择 -> next 弹出，如下图： </br>
![export2](https://raw.githubusercontent.com/ZhaoXiCeil/listen-file-change-eclipse/master/profile/export2.jpg) </br>
4.选择导出的文件夹，选择 -> Finish 完成导出！ </br>
5.在导出的文件夹里面会得到一个jar包，把jar包拷到eclipse安装目录下面的 plugins 文件夹下面，启动eclipse即可使用本插件。 </br>
## 怎么在此插件基础上进行二次开发？
1.将改项目导入eclipse </br>
2.在src 下面 co.yujie.fileChangeListener 包里面的 ListenFileChange.java文件里面添加需要添加的代码，如下图： </br>
![export2](https://raw.githubusercontent.com/ZhaoXiCeil/listen-file-change-eclipse/master/profile/develop1.jpg) </br>
## 怎么打印日志？
1.在eclipse工作空间 -> .metadata -> .plugins -> co.yujie.fileChangeListener 文件夹下面添加日志配置文件，日志配置文件采用log4j配置文件。如下图： </br>
![export2](https://raw.githubusercontent.com/ZhaoXiCeil/listen-file-change-eclipse/master/profile/log1.jpg) </br>

