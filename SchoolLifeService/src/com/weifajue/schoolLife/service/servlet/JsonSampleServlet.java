/** 
 * com.weifajue.schoolLife.service.servlet.JsonSampleServlet.java
 * WeiFaJue.com
 */
package com.weifajue.schoolLife.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * json的例子 只是简单的返回数据，还没有完整的增删改查示范
 * 
 * @author Lemon create on 2012-7-17 上午1:20:44 @version 1.0
 */
@SuppressWarnings("serial")
public class JsonSampleServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 评论的json，里面的"必须添加\转义字符，实在讨厌
		String commentJson = "{ \"comments\":[{\"content\":\"我是评论的内容\", \"author\":\"龙\", \"sharingId\":\"11\"}, {\"content\":\"我是第二条评论\", \"author\":\"鹏\", \"sharingId\":\"11\"}] }";
		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().println(commentJson);
	}
	
}
