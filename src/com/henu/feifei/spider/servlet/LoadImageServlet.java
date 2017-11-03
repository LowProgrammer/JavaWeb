package com.henu.feifei.spider.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONUtil;

import com.tz.spider.entity.Image;
import com.tz.spider.util.SpiderUtil;

/**
 * 
 * 加载图片的servlet
 * LoadImageServlet
 * 创建人:Jery
 * 时间：2016年5月2日-下午10:02:20 
 * @version 1.0.0
 *
 */
//@WebServlet(urlPatterns= {"/load.do"})
public class LoadImageServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(123456789);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try{
			//页数和类型
			String category = request.getParameter("category");
			String pageNum = request.getParameter("pageNum");
			
			//高内聚  积耦合
			List<Image> images = SpiderUtil.queryImage(category, pageNum);
			PrintWriter out = response.getWriter();
			out.print(JSONUtil.serialize(images));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
