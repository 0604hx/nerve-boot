package com.nerve.commons.bean;

import java.util.Date;

/**
 * 中转服务器
 *
 * 中转服务器是用于搭建SSH安全隧道的中转机子，需要有独立的IP地址。每次调度中心与采集器进行数据交互时，
 * 都是通过中转服务器的SSH隧道发送数据包。同时，采集器回传的数据也是通过中转机再到邮件接收器。
 * 中转机至少需要一台。如果没有填写中转机信息，将无法完成采集器的交互（包括采集器的初始化，采集器心跳测试等）。
 *
 *   字段名	类型	默认值	必填	备注
	 name	string			主机别名，用于区分不同的主机
	 host	string		是	主机地址
	 user	string		是	登录名
	 password	string		是	登录密码（AES加密）
	 status	string			状态
	 belong	string			标识了此中转为谁所专用，可选值：
	                         ALL    所有人可用
							 RECEIVER  接收器专用
							 CONTROLLER  控制中心专用
	 addDate	datetime			收录时间

 * com.zeus.jpea.domain
 * Created by zengxm on 2016/2/26 0026.
 */
public class TransitServer {

	private String name;
	private String host;
	private int port;
	private String user;
	private String password;
	private String status;
	private String belong = Scope.ALL;    //默认是对所用人可用
	private Date addDate;
	private String lastUseOfTime;   //最后使用的时间描述

	public String getLastUseOfTime() {
		return lastUseOfTime;
	}

	public TransitServer setLastUseOfTime(String lastUseOfTime) {
		this.lastUseOfTime = lastUseOfTime;
		return this;
	}

	public int getPort() {
		return port;
	}

	public TransitServer setPort(int port) {
		this.port = port;
		return this;
	}

	public String getName() {
		return name;
	}

	public TransitServer setName(String name) {
		this.name = name;
		return this;
	}

	public String getHost() {
		return host;
	}

	public TransitServer setHost(String host) {
		this.host = host;
		return this;
	}

	public String getUser() {
		return user;
	}

	public TransitServer setUser(String user) {
		this.user = user;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public TransitServer setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public TransitServer setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getBelong() {
		return belong;
	}

	public TransitServer setBelong(String belong) {
		this.belong = belong;
		return this;
	}

	public Date getAddDate() {
		return addDate;
	}

	public TransitServer setAddDate(Date addDate) {
		this.addDate = addDate;
		return this;
	}
}
