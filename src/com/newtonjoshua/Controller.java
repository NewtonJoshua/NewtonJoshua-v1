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
				
		//Resume
		if(action.equals("Resume")){
		}
		
		//AITAM
		if(action.equals("AITAM")){
		}
		
		//Demo
		if(action.equals("Demo")){
		}
		
		//Code
		if(action.equals("Code")){
		}
		}
		catch(Exception e){
			LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
		}
		LOGGER.log(Level.SEVERE,action+" : "+ res.toString());
		response.getWriter().write(res.toString());
		
	}

}
