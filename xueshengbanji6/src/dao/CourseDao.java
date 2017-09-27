package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Course;

public class CourseDao extends BaseDao {
	
	
	public List <Course> searchAll()
	{
		List<Course> list=new ArrayList<>();
		 try {
			getStatement();
			//5,执行sql语句
			rs=stat.executeQuery("select * from course");
			//6，处理结果集
			while(rs.next())
			{   
				Course banji=new Course();
				banji.setId(rs.getInt("id"));
				banji.setName(rs.getString("cname"));
				list.add(banji);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally{
			//7，关闭连接
			 closeAll();
		 }
		return list;
	}
	public Course searchById(int id)
	{      
		Course cou=new Course();
		 try {
			getStatement();
			//5,执行sql语句
			rs=stat.executeQuery("select * from course where id="+id);
			//6，处理结果集
			while(rs.next())
			{   
				cou.setId(rs.getInt("id"));
				cou.setName(rs.getString("cname"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally{
			//7，关闭连接
			 closeAll();
		 }
		return cou;
	}
	public boolean add (Course cou)
	{    boolean flag=false;
		
		  try {
			  getStatement();
			//5,执行sql语句
			String str="insert into course(cname) values('"+cou.getName()+"')";
			int rs=stat.executeUpdate(str);
			if(rs>0) flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		  return flag;
	}
	
	public boolean update(Course cou)
	{     
		boolean flag=false;
		  try {
			  getStatement();
			//4，建立sql执行器
			String sql="update course set cname=? where id=?";
			getPreparedStatement(sql);
			past.setString(1, cou.getName());
			past.setInt(2, cou.getId());
			//5,执行sql语句
			int rs=past.executeUpdate();
			if(rs>0) flag=true;
		}  catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;
	}
	public boolean delete(Course cou)
	{
		boolean flag=false;
		 //1,添加jar包
		  try {
			  getStatement();
			//5,执行sql语句
			String str="delete from course where id="+"'"+cou.getId()+"'";
			int rs=stat.executeUpdate(str);
			if(rs>0) flag=true;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {closeAll();}
		return flag;
	}
	public List <Course> searchByCondition(Course cou)
	{
		List<Course> list=new ArrayList<>();
		 try {
				getStatement();
				//5,执行sql语句
				String where="";
				String str=" where ";
				if(!cou.getName().equals(""))
				{
					where+= str+" cname like '%"+cou.getName()+"%'";
					str=" and ";
				}
				String sql="select * from course "+where;
				System.out.println(sql);
				rs=stat.executeQuery(sql);
				//6，处理结果集
				while(rs.next())
				{  
				Course cou1=new Course();
				cou1.setId(rs.getInt("id"));
				cou1.setName(rs.getString("cname"));
				list.add(cou1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 finally{
				//7，关闭连接
				 closeAll();
			 }
		return list;
	}

	
}
