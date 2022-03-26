package xyz.lidaning.myredis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RedissionDemo {

    public static void main(String[] args) throws IOException {
//        quickstart();
//        configFromYaml();
        RedissionDemo demo = new RedissionDemo();
        RLock rLock = demo.acquireLock();
        for (int i=0;i<100;i++){
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    demo.multiThreadDemo(rLock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }

    public static void quickstart(){
        // 1. Create config object
        Config config = new Config();
        // use "rediss://" for SSL connection
//        config.useClusterServers().addNodeAddress("redis://127.0.0.1:6379/1");
        config.useSingleServer().setAddress("redis://127.0.0.1:6379/1");
        config.useSingleServer().setPassword("password");

        // or read config from file
        //config = Config.fromYAML(new File("config-file.yaml"));

        // 2. Create Redisson instance
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        // Reactive API
//        RedissonReactiveClient redissonReactive = redisson.reactive();
        // RxJava3 API
//        RedissonRxClient redissonRx = redisson.rxJava();

        // 3. Get Redis based implementation of java.util.concurrent.ConcurrentMap
        RMap<String, Object> map = redisson.getMap("myMap");
//        RMapReactive<MyKey, MyValue> mapReactive = redissonReactive.getMap("myMap");
//        RMapRx<MyKey, MyValue> mapRx = redissonRx.getMap("myMap");

        // 4. Get Redis based implementation of java.util.concurrent.locks.Lock
        RLock lock = redisson.getLock("myLock");
//        RLockReactive lockReactive = redissonReactive.getLock("myLock");
//        RLockRx lockRx = redissonRx.getLock("myLock");
    }

    public static void configFromYaml() throws IOException, InterruptedException {
        Config config = Config.fromYAML(RedissionDemo.class.getResourceAsStream("/redission.yml"));
        RedissonClient redisson = Redisson.create(config);
        RMap<String, String> map = redisson.getMap("myMap");
        RLock lock = redisson.getLock("myLock");
    }

    public RLock acquireLock() throws IOException {
        Config config = Config.fromYAML(RedissionDemo.class.getResourceAsStream("/redission.yml"));
        RedissonClient redisson = Redisson.create(config);
        RMap<String, String> map = redisson.getMap("myMap");
        RLock lock = redisson.getLock("myLock");
        return lock;
    }

    public void multiThreadDemo(RLock lock) throws InterruptedException {
        if(lock.tryLock(3, TimeUnit.SECONDS)){
            System.out.println("Thread: "+Thread.currentThread().getName()+" got myLock...");
        }else{
            System.out.println("failure");
        }
    }

}
