# 源码地址fork

https://github.com/xiaozhiliaoo/phi-accrual-failure-detector

# 图片解释

https://doc.akka.io/docs/akka/current/typed/failure-detector.html

# 数学背景之概率论

正常的错误检测是标准的伯努利分布，也即二元分布，只能告诉我们失败和成功。 而phi=-log10(1-F(timeSinceLastHeartBeat)), F是累计分布函数(CDF)，也就是对概率密度函数的积分，保持严格递增，
累计分布是对一个随机变量概率分布。phi算出的是心跳和失败概率的关系。而心跳是一个正态分布。也即心跳时间越长，失败概率越大。 u=1000ms,σ=200ms

![img.png](img.png)