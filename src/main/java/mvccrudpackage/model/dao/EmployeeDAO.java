package mvccrudpackage.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvccrudpackage.model.bean.Employee;

public class EmployeeDAO {

//Define instance variables	
    private String DBURL = "jdbc:mysql://localhost:3306/employeedb?serverTimezone=Australia/Melbourne";
    private String DBUsername = "root";
    private String DBPassword = "p@ssw0rd";

    private String INSERTEMPSQL = "INSERT INTO EmployeeTBL (Ename, Eage) VALUES (?, ?);";
    private String SELECTEMPID = "select Eid,Ename,Eage from EmployeeTBL where Eid =?";
    private String SELECTALLEMPS = "select * from EmployeeTBL";
    private String DELETEEMPSQL = "delete from EmployeeTBL where Eid = ?;";
    private String UPDATEEMPSQL = "update EmployeeTBL set Ename = ?,Eage= ? where Eid = ?;";

    //constructor
    public EmployeeDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public void insertEmployee(Employee emp) throws SQLException {
        System.out.println(INSERTEMPSQL);
        Connection connection = null; 
    	PreparedStatement preparedStatement = null;
        // try-with-resource statement will auto close the connection.
        try {
        	connection = getConnection(); 
            preparedStatement = connection.prepareStatement(INSERTEMPSQL);
            preparedStatement.setString(1, emp.getEname());
            preparedStatement.setInt(2, emp.getEage());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            	printSQLException(e);
        } finally {
        	finallySQLException(connection,preparedStatement,null);
        }
    }

    public Employee selectEmployee(int Eid) {
    	Employee emp = null;
    	Connection connection = null; 
      	PreparedStatement preparedStatement = null;
      	ResultSet rs=null;
        // Step 1: Establishing a Connection
        try {
        	connection = getConnection();
          // Step 2:Create a statement using connection object
            preparedStatement = connection.prepareStatement(SELECTEMPID);
            preparedStatement.setInt(1, Eid);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String Ename = rs.getString("Ename");
                int Eage = rs.getInt("Eage");
                emp = new Employee(Eid, Ename, Eage);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        finally {
        	finallySQLException(connection,preparedStatement,rs);
        }
        return emp;
    }

    public List < Employee > selectAllEmployees() {
    	Connection connection = null; 
      	PreparedStatement preparedStatement = null;
      	ResultSet rs=null;
        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Employee > emps = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try { 
        	   connection = getConnection(); 
               // Step 2:Create a statement using connection object
               preparedStatement = connection.prepareStatement(SELECTALLEMPS);
               System.out.println(preparedStatement);
               // Step 3: Execute the query or update query
               rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
               while (rs.next()) {
                   int Eid = rs.getInt("Eid");
                   String Ename = rs.getString("Ename");
                   int Eage = rs.getInt("Eage");
                   
                   emps.add(new Employee(Eid, Ename, Eage));
               }
        } catch (SQLException e) {
            printSQLException(e);
        }
        finally {
        	finallySQLException(connection,preparedStatement,rs);
        }
        return emps;
    }

    public boolean deleteEmployee(int id) throws SQLException {
        boolean empDeleted = false;
        Connection connection = null; 
      	PreparedStatement preparedStatement = null;
      	try {
        	 connection = getConnection(); 
        	 preparedStatement = connection.prepareStatement(DELETEEMPSQL);
        	 preparedStatement.setInt(1, id);
             empDeleted = preparedStatement.executeUpdate() > 0 ? true:false;
        }
        finally {
        	finallySQLException(connection,preparedStatement,null);
        }
        return empDeleted;
    }

    public boolean updateEmployee (Employee emp) throws SQLException {
        boolean empUpdated = false;
        Connection connection = null; 
      	PreparedStatement preparedStatement = null;
      	try {
        	connection = getConnection(); 
        	preparedStatement = connection.prepareStatement(UPDATEEMPSQL);
        	preparedStatement.setString(1, emp.getEname());
        	preparedStatement.setInt(2, emp.getEage());
        	preparedStatement.setInt(3, emp.getEid());

        	empUpdated = preparedStatement.executeUpdate() > 0 ? true:false;
        }
        catch (SQLException e) {
        	printSQLException (e);
        }     
      	finally {
        	finallySQLException(connection,preparedStatement,null);
        }
        return empUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    
    private void finallySQLException(Connection c, PreparedStatement p, ResultSet r){
    	 if (r != null)	{
             try {
                r.close();
             } catch (Exception e) {}
                r = null;
             }
 	
          if (p != null) {
             try {
                p.close();
             } catch (Exception e) {}
                p = null;
             }
 	
          if (c != null) {
             try {
                c.close();
             } catch (Exception e) {
           	  c = null;
             }

          }
    }
}
  
