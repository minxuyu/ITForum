package jodd.forum.service;

import jodd.petite.meta.PetiteBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@PetiteBean
public class RedisService {

    private static JedisPool jedisPool;

    static {
        //创建连接池配置对象：
        JedisPoolConfig jpc = new JedisPoolConfig();
        //设置最大闲置个数
        jpc.setMaxIdle(30);
        //设置最小闲置个数：
        jpc.setMinIdle(10);
        //设置最大的连接数
        jpc.setMaxTotal(50);
        //创建连接池对象  host:连接redis主机IP ；port:redis的默认端口
        jedisPool = new JedisPool(jpc, "127.0.0.1", 6379);
    }

    public Jedis getResource() {
        return jedisPool.getResource();
    }

    public void returnResource(Jedis jedis){
         jedisPool.returnResource(jedis);
        System.out.println("return ok");
    }
}
