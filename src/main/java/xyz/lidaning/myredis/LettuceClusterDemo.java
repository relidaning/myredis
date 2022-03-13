package xyz.lidaning.myredis;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LettuceClusterDemo {

    public static void main(String[] args) {
        ArrayList<RedisURI> list = new ArrayList<>();
        RedisURI uri1 = new RedisURI();
        uri1.setHost("192.168.178.135");
        uri1.setPort(6379);
        list.add(uri1);
        RedisURI uri2 = new RedisURI();
        uri2.setHost("192.168.178.135");
        uri2.setPort(6380);
        list.add(uri2);
        RedisURI uri3 = new RedisURI();
        uri3.setHost("192.168.178.135");
        uri3.setPort(6381);
        list.add(uri3);
        RedisClusterClient client = RedisClusterClient.create(list);
        StatefulRedisClusterConnection<String, String> connect = client.connect();

        /* 同步执行的命令 */
        RedisAdvancedClusterCommands<String, String> commands = connect.sync();
        String str = commands.get("lettuceDemo");
        System.out.println(str);

        /*  异步执行的命令  */
        /*RedisAdvancedClusterAsyncCommands<String, String> commands= connect.async();
        RedisFuture<String> future = commands.set("lettuceDemo", "a lettuceDemo value!");
        try {
          String str = future.get();
          System.out.println(str);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }*/

        connect.close();
        client.shutdown();

    }
}
