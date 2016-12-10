package cs320.Homework2;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import cs320.Homework1.User;

@WebServlet("/Homework2/MyNotes")
public class MyNotes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException
    {
		
		if (getServletContext().getAttribute("cs320.hw2.Note") == null)
			getServletContext().setAttribute("cs320.hw2.Note", new ArrayList<Notes>());

       

        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
			getServletContext().setAttribute("cs320.hw2.Note", new ArrayList<Notes>());

		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("CurrentUser");
		List<Notes> entries = (List<Notes>) getServletContext().getAttribute(
				"cs320.hw2.Note");

		if (user == null) {
			// User isn't authenticated
			response.sendRedirect("../Homework1/Login");
			return;
		}
		else {
			
			
			 Connection c = null;
		        try
		        {
		            String host = "cs3.calstatela.edu";
		            String port = "3306";
		            String dbName = "cs320stu38";
		            String username = "cs320stu38";
		            String password = "qxy6w!h*";
		            
		            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

		            c = DriverManager.getConnection( url, username, password );
		            
		            String sname = (String)session.getAttribute("name");
		            
		            String selectStatement = "select * from entry where name =?";
		            PreparedStatement prepStmt = (PreparedStatement) c.prepareStatement(selectStatement);
		            prepStmt.setString(1, sname);
		            ResultSet rs = prepStmt.executeQuery();
		      
		            while( rs.next() )
		            {
		            		int id 			= rs.getInt("id");
		            		String name 	= rs.getString("name");
		            		String note 	= rs.getString("note");
		            		String title 	= rs.getString("title");
		            		
		            		
		            		Notes entry = new Notes(id, name, note, title);
		            		entries.add(entry);
		            		 
		            }
		           
		            request.setAttribute("entries", entries);
		            
		  
		           
       	            request.getRequestDispatcher( "/WEB-INF/homework2/MyNotes.jsp" ).forward(
				         request, response );
		            
		           // for (Notes u : entries) {
					//	System.out.println("id " + u.id + "name:  " + u.name + "Note:  "
				//				+ u.note + "title:  " + u.title);
				//	}
       	         
       	      
		            
		        }
		        catch( SQLException e )
		        {
		            throw new ServletException( e );
		        }
		        finally
		        {
		            try
		            {
		                if( c != null ) c.close();
		            }
		            catch( SQLException e )
		            {
		                throw new ServletException( e );
		            }
		        }


			}	
			
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
