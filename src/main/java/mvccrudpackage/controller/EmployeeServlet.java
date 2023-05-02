package mvccrudpackage.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvccrudpackage.model.bean.Employee;
import mvccrudpackage.model.dao.EmployeeDAO;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private EmployeeDAO empDAO; // Define as instance variable

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		empDAO = new EmployeeDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// String action = request.getServletPath();
		String action = request.getParameter("action");
		if (action == null) {
			action = "No action";
		}
		try {
			switch (action) {
			case "new":
				showNewEmployee(request, response);
				break;
			case "insert":
				insertNewEmployee(request, response);
				break;
			case "delete":
				deleteExistingEmployee(request, response);
				break;
			case "edit":
				showEditEmployee(request, response);
				break;
			case "update":
				updateExistingEmployee(request, response);
				break;
			default:
				listEmployee(request, response);
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}// End of doPost method

	private void listEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {//action=default
		List<Employee> allEmployee = empDAO.selectAllEmployees();
		request.setAttribute("listEmployees", allEmployee);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//action="new"
		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeform.jsp");
		dispatcher.forward(request, response);
	}

	private void insertNewEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {//action="insert"
		String eName = request.getParameter("name");
		int eAge = Integer.parseInt(request.getParameter("age"));
		Employee e = new Employee(eName, eAge);
		empDAO.insertEmployee(e);
		response.sendRedirect(request.getContextPath() + "/EmployeeServlet?action=____");
	}

	private void showEditEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {//action="edit"
		int id = Integer.parseInt(request.getParameter("id"));
		Employee existingEmployee = empDAO.selectEmployee(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeform.jsp");
		request.setAttribute("employee", existingEmployee);
		dispatcher.forward(request, response);
	}

	private void updateExistingEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {//action="update"
		int id = Integer.parseInt(request.getParameter("id"));
		String ename = request.getParameter("name");
		int eage = Integer.parseInt(request.getParameter("age"));
		Employee e = new Employee(id, ename, eage);
		empDAO.updateEmployee(e);
		response.sendRedirect(request.getContextPath() + "/EmployeeServlet?action=list");
	}
	
	private void deleteExistingEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		empDAO.deleteEmployee(id);
	    response.sendRedirect(request.getContextPath() +"/EmployeeServlet?action=list");
	}
}