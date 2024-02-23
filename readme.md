
# FixFrontDemo #

# 概述 #
这个Demo将帮助你基于QuickFIX搭建基本的消息接收端。

# 怎么运行 #

## 项目介绍 ##

本仓库下面有个项目：FixFrontDemo.

- 它是一个客户端，它向服务器端发出订阅请求，并收取回报。

## 用户手册 ##
下载代码之后，可以直接用IDEA打开项目

记得更改"src/main/resources/quickfix.cfg"文件中的如下设置：

- SocketConnectHost=XXX.XXX.XXX.XXX (通信的IP地址，一般取你本地的)
- SocketConnectPort=XXX (通信的端口，不要跟别的冲突就行)

以及更改"src/main/resources/application.properties"文件中的如下设置：
TargetCompID=XXXX
SenderCompID1=XXXX
SenderCompID2=XXXX
SenderCompID3=XXXX

其他设置可以先不管，但如果你要改的话，记得两边都要改，不然对不上。

# 联系方式 #

- 博客：[https://blog.csdn.net/jsjxb1987](https://blog.csdn.net/jsjxb1987)
- 邮箱：[396114734@qq.com](mailto:396114734@qq.com)











