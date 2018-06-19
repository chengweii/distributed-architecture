# 针对电商分布式系统的基础架构实现

# 环境搭建
## 整体架构

## Mysql集群

## Redis集群
![sharding cluster](doc/redis_sharding_cluster.jpg)  
负载均衡统一通过客户端分片机制的ShardedJedisPool实现。  
高可用则针对不同的场景采用不同的方式实现：  
* 针对分布式锁、分布式ID等业务对缓存数据一致性要求很高的场景，无法通过主从模式保证强一致性，所以目前通过多写的方式来实现，虽然一定程度上降低了性能，但保证了强一致性要求下的高可用。
* 针对普通读缓存业务对缓存数据一致性要求不高的场景，可以不用保证高可用，通过单写，获取失败重新加载缓存的方式实现即可。
* ShardedJedisPool 无法处理结点宕机问题，需要在结点异常时通过 zookeeper 获取最新的可访问结点列表重新初始化 ShardedJedisPool。
    * https://gitee.com/zhanggaofeng/redis-proxy
    * https://github.com/smallvq123/redis-ha-proxy

## RabbitMQ集群

## Zookeeper集群

## config-toolkit
具体配置服务部署参考config-toolkit的<a href="https://github.com/dangdangdotcom/config-toolkit">github</a>

## 分布式环境各场景采用方案
### 分布式事务
关于分布式环境下的各个服务的事务均采用Spring事务管理器实现本地事务，而分布式服务之间的事务采用MQ消息实现最终一致的全局事务。

### 分布式Session
分布式环境下的Session采用独立的Redis集群进行管理。

### 分布式ID
设计单独的分布式ID获取服务，该服务通过Redis集群的incr获取自增id保证原子操作。
* redis

### 分布式锁
分布式环境下的锁服务通过Redis集群环境下的setnx方式实现分布式并发同步控制。需要注意如下几点：
* 为了避免宕机情况引起的锁定失败，在集群中至少一半以上的结点上设置锁成功才算最终锁定成功
* 为了避免因为在集群各结点设置锁顺序不一致而导致在锁失效时间内死锁的情况出现，设置锁集群结点的顺序必须一致
* 为了避免因为主从同步不一致情况而引起的锁定失败，集群不再通过主备模式保证高可用，客户端+多个Redis结点方式已经可以保证高可用
* 实现上除了通过 jedis 客户端 + redis 服务 的方式，还可以通过 Nginx lua + redis 服务 的方式，后者可以将分布式锁的实现细节与客户端解耦，当然本地也可以通过单独服务或SDK的方式解耦分布式锁的具体实现细节