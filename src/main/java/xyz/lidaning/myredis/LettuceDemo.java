package xyz.lidaning.myredis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;

import java.nio.charset.StandardCharsets;

public class LettuceDemo {

    public static void main(String[] args) {
        RedisURI uri = new RedisURI();
        uri.setHost("127.0.0.1");
        uri.setPort(6379);
        uri.setPassword("passwordhere".toCharArray());
        RedisClient client = RedisClient.create(uri);

        // connection, 线程安全的长连接，连接丢失时会自动重连，直到调用 close 关闭连接。
        StatefulRedisConnection<String, String> connection = client.connect();

        // sync, 默认超时时间为 60s.
        RedisStringCommands<String, String> sync = connection.sync();
        sync.set("lettuceDemo", "It's wonderful!");
        String value = sync.get("lettuceDemo");
        System.out.println(value);

        // close connection
        connection.close();

        // shutdown
        client.shutdown();
    }
}
