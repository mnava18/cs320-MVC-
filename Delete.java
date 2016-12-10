package cs320.Homework2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;


@WebServlet("/Homework2/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				
		  int id3 = Integer.parseInt(request.getParameter("id"));
				ArrayList<Notes> entries = (ArrayList<Notes>) getServletContext().getAttribute("cs320.hw2.Note");
				if (entries != null){
					for (Notes entry : entries)
						if (entry.getId() == id3){
							
							break;
						}					
				}
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
		            HttpSession session = request.getSession();
		            String sname = (String)session.getAttribute("name");
		            int id2 = Integer.parseInt(request.getParameter("id"));
		            String query="DELETE FROM entry WHERE id = ? AND name = ?";
		            
		            PreparedStatement prepStmt = (PreparedStatement) c.prepareStatement(query);
		            prepStmt.setInt(1, id2);
		            prepStmt.setString(2, sname);
		            prepStmt.executeUpdate();
		           
		            request.setAttribute("entries", entries);
		           
					response.sendRedirect("MyNotes");
					System.out.println("succesfully deleted");
		            
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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
