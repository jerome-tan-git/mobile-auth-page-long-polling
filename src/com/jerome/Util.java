package com.jerome;

import java.util.UUID;

import redis.clients.jedis.Jedis;

public class Util {
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	public static boolean isPassed(String ID)
	{
		boolean returnValue = false;
		Jedis jedis = new Jedis("192.168.103.18");
		jedis.auth("123456redis");
		returnValue = jedis.sismember("passed", ID);
		if (returnValue)
		{
			jedis.srem("passed", ID);
		}
		return returnValue;
	}
	
	public static void startWaiting(String _id)
	{
        Thermometer thermometer = new Thermometer();
        thermometer.start(_id);
        Jedis jedis = new Jedis("192.168.103.18");
		jedis.auth("123456redis");
		jedis.rpush("forpass", _id);
	}
	
}
