# logserver
日志服务器，接收APP发送的日志

采用HTTP协议栈，通信框架采用Netty,业务处理采用Disruptor来构建处理流程。

待处理：
向文件中写日志计划改成Memory Mepped File的方式，提高写入速度。
写系统启动脚本。
