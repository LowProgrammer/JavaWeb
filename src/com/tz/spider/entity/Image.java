package com.tz.spider.entity;
/**
 * 
 * 图片对象实体类
 * Image pojo
 * 创建人:Jery
 * 时间：2016年5月2日-下午10:15:55 
 * @version 1.0.0
 *
 */
public class Image {
	private String title; //4   private protected default public
	private String sUrl;
	private String bigUrl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getsUrl() {
		return sUrl;
	}
	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}
	public String getBigUrl() {
		return bigUrl;
	}
	public void setBigUrl(String bigUrl) {
		this.bigUrl = bigUrl;
	}
}
