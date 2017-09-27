package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDao {
	Connection con=null;
	Statement stat=null;
	PreparedStatement past=null;
	ResultSet rs=null;
	public void getConnection()
	{
		//2，加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//3，建立连接
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/school?characterEncoding=utf-8","root","123456");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getStatement()
	{  
		getConnection();
		try {
			stat=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getPreparedStatement(String sql)
	{
		getConnection();
		try {
			past=con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void closeAll()
	 {
		
			try {
				if(con!=null)
				con.close();
				if(stat!=null)
					stat.close();
				if(past!=null)
					past.close();
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	 }
}
