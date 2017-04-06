# hcs-rpc-all

nfs-rpc http://code.google.com/p/nfs-rpc/ 淘宝牛人开源的一个RPC框架。传输层使用mina/netty，协议层灵活可扩展，支持多种序列化方式目前支持Hessian ，Java，Protocol Buffers，序列化方式可扩展。该框架设计非常优秀，代码质量高，可读性强，结构清晰，层次分明。

nfs-rpc是一个非常优秀的RPC基础框架，在它的基础上进行扩展开发也非常方便。从序列化方式来看Java序列化虽然使用简单，但性能不高，不能跨语言。Hessian，能跨语言，使用也很方便，但性能不占优势
