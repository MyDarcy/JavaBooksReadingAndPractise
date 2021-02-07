package chapter4;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Author by darcy
 * Date on 17-8-18 下午3:00.
 * Description:
 */
public class ClubTest {


  public static void main(String[] args) throws InterruptedException {
    testDirectConnection();

    testPoolConnection();

    testClientApi();
  }

  private static void testClientApi() throws InterruptedException {
    String key = "hello";
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    System.out.println(jedis.get(key));
    TimeUnit.SECONDS.sleep(10);
    System.out.println(jedis.ping());
    TimeUnit.SECONDS.sleep(5);
    jedis.close();
  }

  private static void testPoolConnection() {
    GenericObjectPoolConfig config
        = new GenericObjectPoolConfig();
    config.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
    config.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
    config.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
    config.setJmxEnabled(true);
    // 没有连接后client端的最大等待时间.
    config.setMaxWaitMillis(3000);

    JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    Jedis jedis = null;
    Logger log = Logger.getLogger("log");

    try {
      jedis = jedisPool.getResource();
      System.out.println(jedis.get("hello"));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }

  }

  private static void testDirectConnection() {
    ProtobuffSerializer protobuffSerializer = new ProtobuffSerializer();
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    String key = "club:1";

    // 序列化
    Club club = new Club(1, "AC", "米兰", new Date(), 1);
    byte[] clubBytes = protobuffSerializer.serialize(club);
    jedis.set(key.getBytes(), clubBytes);
    System.out.println(club);

    // 反序列化
    byte[] bytes = jedis.get(key.getBytes());
    Club resultClub = protobuffSerializer.deserialize(bytes);
    System.out.println(club);
  }
}
