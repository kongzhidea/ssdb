package com.kk.ssdb4j;

import com.kk.log4j.ConsoleLogger;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.nutz.ssdb4j.SSDBs;
import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;

public class Ssdb4JTest {
    private static ConsoleLogger logger = new ConsoleLogger();

    private static String host = "localhost";
    private static int port = 8888;
    private static int timeout = 3000;//  毫秒

    //SSDBs.simple 客户端 未使用连接池
//        private static SSDB ssdb = SSDBs.simple(host, port, timeout);
    // SSDBs.pool 使用连接池
    private static SSDB ssdb = null;

    static {
        PoolConfig config = new PoolConfig();
        config.setMaxActive(300); // 可以从缓存池中分配对象的最大数量
        config.setMaxIdle(50); // 缓存池中最大空闲对象数量
        config.setMinIdle(30); // 缓存池中最小空闲对象数量
        config.setMaxWait(10); // 阻塞的最大数量
        config.setTestOnBorrow(false); // 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法
        config.setTestOnReturn(false);
        config.setTestWhileIdle(false);
        ssdb = SSDBs.pool(host, port, timeout, null);
    }

    public static void main(String[] args) {


        ssdb.set("name", "wendal"); // call check() to make sure resp is ok

        Response resp = ssdb.get("name");
        logger.info("name=" + resp.asString());

//        ssdb.setx("tta", 1, 100);

        logger.info(ssdb.get("tta").asString());
        logger.info(ssdb.ttl("tta").asInt());
    }
}
