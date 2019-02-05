package com.flame.util;

import org.springframework.stereotype.Repository;

@Repository
public class FtpConfigUtil {
	//ftp服务器地址
	private static String HOST;
	//端口号
	private static int PORT;
	//链接用户名
	private static String username;
	//链接密码
	private static String password;
	//tomcat访问端口
	private static int viewPort;
	//tomcat访问文件路径
	private static String viewPath;
	
	public static String getHOST() {
		return HOST;
	}
	public static void setHOST(String hOST) {
		HOST = hOST;
	}
	public static int getPORT() {
		return PORT;
	}
	public static void setPORT(int pORT) {
		PORT = pORT;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		FtpConfigUtil.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		FtpConfigUtil.password = password;
	}
	public static int getViewPort() {
		return viewPort;
	}
	public static void setViewPort(int viewPort) {
		FtpConfigUtil.viewPort = viewPort;
	}
	public static String getViewPath() {
		return viewPath;
	}
	public static void setViewPath(String viewPath) {
		FtpConfigUtil.viewPath = viewPath;
	}
	
}
