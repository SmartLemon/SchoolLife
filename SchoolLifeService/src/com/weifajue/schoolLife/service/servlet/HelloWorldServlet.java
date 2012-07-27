/** 
 * com.weifajue.schoolLife.service.servlet.HelloWorldServlet.java
 * WeiFaJue.com
 */
package com.weifajue.schoolLife.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * 示范代码
 * 
 * @author Lemon
 * create on 2012-7-17 上午1:00:46 @version 1.0
 */
@SuppressWarnings("serial")
public class HelloWorldServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello World");
	}
	
}
