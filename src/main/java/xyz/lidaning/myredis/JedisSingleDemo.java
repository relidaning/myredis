package xyz.lidaning.myredis;

import org.springframework.core.io.ClassPathResource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class JedisSingleDemo {

    public static void main(String[] args) throws IOException {

        //connection
        Jedis jedis = new Jedis("82.157.147.8", 6379);
        jedis.auth("");     //auth here

        //String
        jedis.set("myredis", "done");
        String myredis = jedis.get("myredis");
        System.out.println(myredis);

        //list
        jedis.lpush("mylist", "a", "b", "c", "d");
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);

        //set
        jedis.sadd("myset", "shake", "100", "rock&roll", "200");
        jedis.scard("myset");

        //pipeline
        Pipeline pipeline = jedis.pipelined();
        pipeline.sadd("p_myset", "shake", "100", "rock&roll", "200");
        pipeline.scard("myset");
        pipeline.sync();

        //transaction
        jedis.set("count", "1");
        Transaction transaction = jedis.multi();
        transaction.decr("count");
        transaction.exec();
        jedis.get("count");

        //exec lua
        ClassPathResource classPathResource = new ClassPathResource("updatejson.lua");
        InputStream in =classPathResource.getInputStream();
        String lua = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
        String lua_SHA = jedis.scriptLoad(lua);
        String lua_result = jedis.evalsha(lua_SHA, 1, "count").toString();
        System.out.println("lua_result:"+lua_result);

    }
}
