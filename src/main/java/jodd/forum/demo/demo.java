package jodd.forum.demo;

import redis.clients.jedis.Jedis;

public class demo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("hello", "javaRedis");
        String hello = jedis.get("hello");
        System.out.println(hello);
        jedis.close();
    }
}
