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
       

    public Controller() {
        super();
    }

    private final static Logger LOGGER = Logger.getLogger("Controller"); 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action= request.getParameter("action");
		response.setContentType("application/json");
		response.addHeader("Content-Type", "application/json");
		JSONObject res = new JSONObject();
		try{
		//Login
		if(action.equals("Login")){

			String user= request.getParameter("user");
			String name= request.getParameter("name");
			String status=SendMail.sendMail(user,name);
			res.put("Status", status);
		}
		
		//Resume
		if(action.equals("GitHub")){
			res=Feeds.getFeed();
		}

		//Resume
		if(action.equals("FaceBook")){
			res=FB_Posts.facebook();
		}
				
		//Captcha- AITAM Cred
		if(action.equals("Captcha")){
			String recap= request.getParameter("response");
			String result=ReCaptcha.verify(recap);
			if(result.equalsIgnoreCase("true}")){
				String mail=request.getParameter("mail");
				String user=request.getParameter("user");
				String r=SendMail.sendCred(mail, user);
				res.put("Captcha", r);
			}
			
		}
		


		}
		catch(Exception e){
			LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
			String Exc=e.toString()+" : "+ e.getStackTrace().toString();
			SendMail.sendException(Exc);
		}
		LOGGER.log(Level.SEVERE,action+" : "+ res.toString());
		response.getWriter().write(res.toString());
		
	}

}
