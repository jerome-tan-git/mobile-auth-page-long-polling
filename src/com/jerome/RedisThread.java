package com.jerome;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

import redis.clients.jedis.Jedis;

public class RedisThread implements Runnable {

	@Override
	public void run() {
		while (true) {

			Jedis jedis = new Jedis("192.168.103.18");
			jedis.auth("123456redis");
			System.out.println("In get message....");
			List<String> o = jedis.blpop(10, "r_queue");
			System.out.println("After get message....");
			if (o != null && o.size() > 0) {
				for (String str : o) {
					System.out.println("Get message: " + str + " |"  + ReverseAjaxServlet.map.containsKey(str));
					if (ReverseAjaxServlet.map.containsKey(str)) {
						ConnectionObject co = ReverseAjaxServlet.map.get(str);

						HttpServletResponse peer = (HttpServletResponse) co
								.getContext().getResponse();


						try {
							peer.getWriter().write(
									new JSONArray().put("get message").toString());
							peer.setStatus(HttpServletResponse.SC_OK);
							peer.setContentType("application/json");
							peer.flushBuffer();
							co.getContext().complete();
							ReverseAjaxServlet.map.remove(str);
							System.out.println("After send message: " + str);
						} catch (Exception e) {
							try {
								co.getContext().complete();
							} catch (Exception e1) {
								System.out.println("Send message error: " + str + " error: " + e1.getMessage());
							}
							ReverseAjaxServlet.map.remove(str);
						}

					}
				}
			}

		}

	}

}
