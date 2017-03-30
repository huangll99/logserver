# logserver
日志服务器，接收APP发送的日志

采用HTTP协议栈，通信框架采用Netty,业务处理采用Disruptor来构建处理流程,单线程向文件顺序写日志。
