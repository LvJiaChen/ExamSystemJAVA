package com.flame.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class SocketUtil {
	public static Socket client;
	private static PrintWriter pw;

	private SocketUtil() {
		try {
			client = new Socket("127.0.0.1", 6666);
			// client = new Socket("192.168.0.164", 6666);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendMsg(String msg) {
		msg = msg + "&";
		System.out.println("socket发送的数据：" + msg);
		try {
			pw = new PrintWriter(client.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(msg.getBytes("UTF-8"))));
			pw.write(br.readLine());
			pw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void companyDynamicManage(String opFlag, Long companyId) {
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("serviceCode", "companyDynamicManage");
		message.put("opFlag", opFlag);
		message.put("companyId", companyId);

		sendMsg(JSON.toJSONString(message));
	}

	/*
	 */
	public static void devRemoveControl(String commCode, Long companyId, String fnCode, Map<String, Object> param) {
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("serviceCode", "devRemoveControl");
		message.put("commCode", commCode);
		message.put("companyId", companyId);
		message.put("fnCode", fnCode);
		message.put("param", param);

		System.out.println("++++++++++++++++++");
		System.out.println("socket send sessage:");
		System.out.println(JSON.toJSONString(message));
		sendMsg(JSON.toJSONString(message));
	}
}
