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


@WebServlet("/Homework2/EditNote")
public class EditNote extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
	public void init() throws ServletException
    {
		
		
        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("CurrentUser");
		 if (user == null) {
				// User isn't authenticated
			 response.sendRedirect("../Homework1/Login");
				return;
			}
		
		Notes noteid = null;
		try{
			int id = Integer.parseInt(request.getParameter("id"));
		
			ArrayList<Notes> entries = (ArrayList<Notes>) getServletContext().getAttribute("cs320.hw2.Note");
			for (Notes e : entries){
				if (e.getId() == id){
					noteid = e;
					System.out.println(id);
					break;
				}
			}
		}catch(Exception e){}
		finally{
			if (noteid == null){
				System.out.println("NuLL NOT WORKING");
				//response.sendRedirect("MyNotes");
				return;
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
		            
		            String sname = (String)session.getAttribute("name");
		            int id2 = Integer.parseInt(request.getParameter("id"));
		            String query="SELECT * FROM entry WHERE id = ? AND name = ?";
		            
		            PreparedStatement prepStmt = (PreparedStatement) c.prepareStatement(query);
		            prepStmt.setInt(1, id2);
		            prepStmt.setString(2, sname);
		            ResultSet rs = prepStmt.executeQuery();
		            getServletContext().setAttribute("cs320.hw2.Note", new ArrayList<Notes>());
		            List<Notes> entries = (List<Notes>) getServletContext().getAttribute(
		    				"cs320.hw2.Note");
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
       	          request.getRequestDispatcher( "/WEB-INF/homework2/EditNote.jsp" ).forward(
				      request, response );
		     
		            
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
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String title2 = request.getParameter("title");
		String note2 = request.getParameter("note");
		HttpSession session = request.getSession();
		String name2 = (String) session.getAttribute("CurrentUser");
		System.out.println("name: " + name2);
		System.out.println("title: " + title2);
		System.out.println("note: " + note2);

		List<Notes> entries = (List<Notes>) getServletContext().getAttribute(
				"cs320.hw2.Note");

		Connection c = null;
		try {
			String host = "cs3.calstatela.edu";
			String port = "3306";
			String dbName = "cs320stu38";
			String username = "cs320stu38";
			String password = "qxy6w!h*";

			String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

			c = DriverManager.getConnection(url, username, password);

			String selectStatement = "UPDATE  entry SET name =?, title=?,note=? WHERE id=? AND name=?";
			
			PreparedStatement prepStmt = (PreparedStatement) c
					.prepareStatement(selectStatement);
			
			prepStmt.setString(1, name2);
			prepStmt.setString(2, title2);
			prepStmt.setString(3, note2);
			prepStmt.setInt(4, id);
			prepStmt.setString(5, name2);
			prepStmt.executeUpdate();

			Notes entry = new Notes(id, name2, title2, note2);
			entries.add(entry);

			request.setAttribute("entries", entries);

			response.sendRedirect("MyNotes");
			System.out.println("succesfully added");
		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}

	}
	
		
	}


