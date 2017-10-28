package com.henu.feifei;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HTMLHttpServerlet
 */
@WebServlet("/HTMLHttpServerlet")
public class HTMLHttpServerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public HTMLHttpServerlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		PrintWriter out=response.getWriter();
		out.println("<!DOCTYPE HTML>");
		out.println("<HTML>");
		out.println("<head><title>动态生成html</title></head>");
		out.println("<body>");
		out.println("<p><h1>标题</h1></p>");
		out.println("<table border='0' align='center'>");
		out.println("<tr><td>哈哈哈哈</td></tr>");
		out.println("<tr><td>哈哈哈哈</td></tr>");
		out.println("</table>");
		out.println("</doby>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
