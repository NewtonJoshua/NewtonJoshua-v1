package com.newtonjoshua;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Newton_JoshuaServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType(Messages.getString("Newton_JoshuaServlet.0")); //$NON-NLS-1$
		resp.getWriter().println(Messages.getString("Newton_JoshuaServlet.1")); //$NON-NLS-1$
	}
}
