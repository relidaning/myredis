package xyz.lidaning.myredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolDemo {

    public static void main(String[] args) {
        JedisPool pool = new JedisPool("tcp://:password@82.157.147.8:6379");

        Jedis jedis = pool.getResource();
        String count = jedis.get("count");
        System.out.println("count:"+count+", from pool");

    }

    class MyJedisConfig extends JedisPoolConfig{

        public MyJedisConfig(){

        }
    }
}
