package com.gl.expensetracker.usrprof;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gl.expencetracker.ExpenseUser;
import com.gl.expensetracker.connection.DatabaseUtils;

import java.sql.*;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/jsp/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oldpass = request.getParameter("oldpass");
		String newpass = request.getParameter("newpass");
		String confpass = request.getParameter("confpass");
		//extract userid value from session
		

		Connection dbConnection = null;
		PreparedStatement prepareStmt = null;
		RequestDispatcher requestDispatcher; 
		ExpenseUser user = new ExpenseUser();
		String updatesql = "update userdetails set password=? "
				+ " where userid=?";

		try {
			DatabaseUtils db = new DatabaseUtils();
			dbConnection = db.getConnection();
			prepareStmt = dbConnection.prepareStatement(updatesql);
			prepareStmt.setString(1, newpass);
			prepareStmt.setInt(2, 1);
			// execute update SQL stetement
			prepareStmt.executeUpdate();
			HttpSession session = request.getSession(true);
			user.setPassWord(newpass);
			session.setAttribute("user", user);
			requestDispatcher = request.getRequestDispatcher("welcome.jsp");
			requestDispatcher.forward(request, response);
			

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			response.sendRedirect("Error.jsp");
		} finally {

			if (prepareStmt != null) {
				try {
					prepareStmt.close();
					if(dbConnection != null)
						dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}


