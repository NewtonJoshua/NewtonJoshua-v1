package com.newtonjoshua;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(Messages.getString("Controller.0")); //$NON-NLS-1$

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter(Messages.getString("Controller.1")); //$NON-NLS-1$
		response.setContentType(Messages.getString("Controller.2")); //$NON-NLS-1$
		response.addHeader(Messages.getString("Controller.3"), Messages.getString("Controller.4")); //$NON-NLS-1$ //$NON-NLS-2$
		JSONObject res = new JSONObject();
		try {
			// Login
			if (action.equals(Messages.getString("Controller.5"))) { //$NON-NLS-1$

				String user = request.getParameter(Messages.getString("Controller.6")); //$NON-NLS-1$
				String name = request.getParameter(Messages.getString("Controller.7")); //$NON-NLS-1$
				String status = SendMail.sendMail(user, name);
				res.put(Messages.getString("Controller.8"), status); //$NON-NLS-1$
			}

			// Resume
			if (action.equals(Messages.getString("Controller.9"))) { //$NON-NLS-1$
				res = Feeds.getFeed();
			}

			// Resume
			if (action.equals(Messages.getString("Controller.10"))) { //$NON-NLS-1$
				res = FB_Posts.facebook();
			}

			// Captcha- AITAM Cred
			if (action.equals(Messages.getString("Controller.11"))) { //$NON-NLS-1$
				String recap = request.getParameter(Messages.getString("Controller.12")); //$NON-NLS-1$
				String result = ReCaptcha.verify(recap);
				if (result.equalsIgnoreCase(Messages.getString("Controller.13"))) { //$NON-NLS-1$
					String mail = request.getParameter(Messages.getString("Controller.14")); //$NON-NLS-1$
					String user = request.getParameter(Messages.getString("Controller.15")); //$NON-NLS-1$
					String r = SendMail.sendCred(mail, user);
					res.put(Messages.getString("Controller.16"), r); //$NON-NLS-1$
				}

			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, Messages.getString("Controller.17") + e.toString() + Messages.getString("Controller.18") + e.getStackTrace().toString()); //$NON-NLS-1$ //$NON-NLS-2$
			String Exc = e.toString() + Messages.getString("Controller.19") + e.getStackTrace().toString(); //$NON-NLS-1$
			SendMail.sendException(Exc);
		}
		LOGGER.log(Level.SEVERE, action + Messages.getString("Controller.20") + res.toString()); //$NON-NLS-1$
		response.getWriter().write(res.toString());

	}

}
