package com.kk.ssdb;

import com.udpwork.ssdb.*;

/**
 * SSDB Java client SDK demo.
 * <p/>
 * 官方客户端
 * <p/>
 * 推荐 封装连接池后再使用。
 */
public class SimpleDemo {
    static SSDB ssdb = null;

    static {
        try {
            ssdb = new SSDB("localhost", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        byte[] b;
        Response resp;

		/* kv */
        System.out.println("---- kv -----");

        ssdb.set("a", "12113");
        b = ssdb.get("a");
        System.out.println(new String(b));


        //setnx
        System.out.println("setnx=" + ssdb.setnx("add1", "159871"));

//        System.out.println("extsts=" + ssdb.exists("a"));

        ssdb.del("a");

        System.out.println("extsts=" + ssdb.exists("a"));

        b = ssdb.get("a");
        System.out.println(b);


        //  key不存在时候会自动创建，初始值0
        ssdb.incr("a", 10);
        System.out.println("extsts=" + ssdb.exists("a"));

        // setx
//        ssdb.setx("ta", "1qaz!", 100);
        System.out.println("ttl=" + ssdb.ttl("ta"));

        resp = ssdb.scan("", "", 10);
        resp.print();
        resp = ssdb.rscan("", "", 10);
        resp.print();
        System.out.println("");



		/* hashmap */
        System.out.println("---- hashmap -----");

        ssdb.hset("n", "a", "123");
        b = ssdb.hget("n", "a");
        System.out.println(new String(b));
        ssdb.hdel("n", "a");
        b = ssdb.hget("n", "a");
        System.out.println(b);
        ssdb.hincr("n", "a", 10);


        resp = ssdb.hscan("n", "", "", 10);
        resp.print();
        System.out.println("");

		/* zset */
        System.out.println("---- zset -----");

        Long d;
        ssdb.zset("n", "a", 123);
        d = ssdb.zget("n", "a");
        System.out.println(d);
        ssdb.zdel("n", "a");
        d = ssdb.zget("n", "a");
        System.out.println(d);
        ssdb.zincr("n", "a", 10);

        resp = ssdb.zscan("n", "", null, null, 10);
        resp.print();
        System.out.println("");

		/* multi */
        ssdb.multi_set("a", "1", "b", "2");
        resp = ssdb.multi_get("a", "b");
        resp.print();
        System.out.println("");

        // 注意: 如果某个命令没有对应的函数, 你就使用 request() 方法来执行
        resp = ssdb.request("qpush", "q", "a");
        for (int i = 1; i < resp.raw.size(); i += 2) {
            String s = new String(resp.raw.get(i));
            System.out.println(s);
        }

        //
        ssdb.close();
    }


}
