# jdkfacsimile
Just learn JDK code

## BlockingQueue三个问题
* LinkedBlockingQueue, 在一个元素的时候，存在竞争吗？
* LinkedBlockingQUeue, 为什么在入队的时候，还要检查队列是否已满，并在队列不满的时候，调用notFull.notify()?
* ArrayBlockingQueue的性能为什么比LinkedBlockingQueue的并发性能差？
* 参考：https://monkeysayhi.github.io/2017/10/18/%E6%BA%90%E7%A0%81%7C%E5%B9%B6%E5%8F%91%E4%B8%80%E6%9E%9D%E8%8A%B1%E4%B9%8BBlockingQueue/   作者分析的还不错。 

