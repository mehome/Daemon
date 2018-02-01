### 通过service提高进程权限

前台进程是优先级最高的类型。在官方指南中有介绍：

![](file:///E:/software/AndroidStudio/projects/Daemon/resources/keep_service2.png)

创建一个前台服务用于提高app在按下home键之后的进程优先级

    startForeground(ID,Notification)：使Service成为前台Service。

    前台服务需要在通知栏显示一条：

![](file:///E:/software/AndroidStudio/projects/Daemon/resources/keep_service1.png)

    API level < 18 ：参数2 设置 new Notification()，图标不会显示。
    API level >= 18：在需要提优先级的service A启动一个InnerService。两个服务都startForeground，且绑定同样的 ID。Stop 掉InnerService ，通知栏图标被移除。