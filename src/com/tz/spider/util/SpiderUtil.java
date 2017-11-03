package com.tz.spider.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tz.spider.entity.Image;

/**
 * 
 * 加载图片工具类
 * SpiderUtil
 * 创建人:Jery
 * 时间：2016年5月2日-下午10:23:45 
 * @version 1.0.0
 *
 */
public class SpiderUtil {//属性 方法 代码块
	private static Map<String,String> urls = new HashMap<String, String>();
	static{
		urls.put("all", "http://www.dbmeinv.com/dbgroup/show.htm");//所有
		urls.put("sex", "http://www.dbmeinv.com/dbgroup/show.htm?cid=2");
		urls.put("tun", "http://www.dbmeinv.com/dbgroup/show.htm?cid=6");
		urls.put("sw", "http://www.dbmeinv.com/dbgroup/show.htm?cid=7");
		urls.put("leg", "http://www.dbmeinv.com/dbgroup/show.htm?cid=3");
		urls.put("all", "http://www.dbmeinv.com/dbgroup/show.htm?cid=4");
		urls.put("qt", "http://www.dbmeinv.com/dbgroup/show.htm?cid=5");
	}
	
	public static List<Image> queryImage(String category,String pageNum){
		List<Image> images = new ArrayList<Image>();
		try{
			Document doc = Jsoup.connect(urls.get(category)).data("p",pageNum).timeout(3000).get();
			Elements imgs = doc.getElementsByTag("img");
			Image image = null;
			for(Element img:imgs){//高内聚
				image = new Image();
				String url = img.attr("src");//拿到图片地址
				String bigUrl = img.attr("src");
				String title = img.attr("title");
				
				if(url!=null && !"".equals(url)
						&& title!=null && !"".equals(title)){
					image.setsUrl(url);
					image.setBigUrl(bigUrl);
					image.setTitle(title);
					images.add(image);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return images;
	}

}
