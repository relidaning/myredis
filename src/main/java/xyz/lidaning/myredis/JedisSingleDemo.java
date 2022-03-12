package xyz.lidaning.myredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class JedisSingleDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("82.157.147.8", 6379);
        jedis.set("myredis", "done");
        String myredis = jedis.get("myredis");
        System.out.println(myredis);

        jedis.lpush("mylist", "a", "b", "c", "d");
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);

        jedis.sadd("myset", "shake", "100", "rock&roll", "200");
        jedis.scard("myset");

        Pipeline pipeline = jedis.pipelined();
        pipeline.sadd("p_myset", "shake", "100", "rock&roll", "200");
        pipeline.scard("myset");
        pipeline.sync();

    }
}
