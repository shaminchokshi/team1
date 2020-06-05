package com.module_a;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class email_alpha
 */
@WebServlet("/email_alpha")
public class email_alpha extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email_1=request.getParameter("email_1");
		class1 myclass=new class1();
		
		if(email_1!=null && email_1.contains("@gmail.com") )
		{
            class1.Mail("alphatest21121999@gmail.com","Password@123",email_1);
            
            response.sendRedirect("sucessfull.jsp");
            
		}
		else
		{
			response.sendRedirect("failure.jsp");
		}
		
            	
        }
	


}

	