# akka classic cluster sample

akka集群技术分享的代码.

[slides]()

## Demo1 动态变化的集群动画

```
sh cluster-start
sh node-stop 4
sh node-down 5
sh node-start 5
任务管理器杀掉Java进程
sh node-start 4
sh node-start 5
sh cluster-stop
```

## Demo2 集群成员变更事件通知/演示JMX

## Demo3 Classic Distributed Data

## Demo4 Classic Cluster Singleton

## Demo5 Classic Cluster Publish Subscribe

## Demo6 Classic Cluster Sharding

## Demo7 Gossip Animation Simulator

## Demo8 Phi Accrual Failure Detector
