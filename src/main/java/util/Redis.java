package util;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 18:24 2018/3/13
 */


import redis.clients.jedis.Jedis;

public class Redis  {
    private static Jedis jedis;
    public Jedis getJedis(){
        if(jedis== null) {
            jedis = new Jedis("localhost");
        }
       return jedis;
    }

}
