package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.BanJi;
import entity.Student;

public class StudentDao extends BaseDao{

	public List <Student> searchAll()
	{
		List<Student> list=new ArrayList<>();
		 try {
			getStatement();
			//5,执行sql语句
			rs=stat.executeQuery("select s.*,bj.name as bjName, bj.stuNums from student as s left join banji as bj on s.bj_id=bj.id");
			//6，处理结果集
			while(rs.next())
			{   
				Student stu=new Student();
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
                
				BanJi bj=new BanJi();
                bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("bjName"));
				bj.setStuNums(rs.getInt("stuNums"));
				
				stu.setBj(bj);
				list.add(stu);
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
	public Student searchById(int id)
	{      
		Student stu=new Student();
		 try {
			getStatement();
			//5,执行sql语句
			rs=stat.executeQuery("select s.*,bj.name as bjName, bj.stuNums from student as s left join banji as bj on s.bj_id=bj.id where s.id="+id);
			//6，处理结果集
			while(rs.next())
			{   
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				
				BanJi bj=new BanJi();
                bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("bjName"));
				bj.setStuNums(rs.getInt("stuNums"));
				
				stu.setBj(bj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally{
			//7，关闭连接
			 closeAll();
		 }
		return stu;
	}
	public boolean add (Student stu)
	{    boolean flag=false;
		
		  try {
			  getStatement();
			//5,执行sql语句
			String str="insert into student(name,sex,age,bj_id) values('"+stu.getName()+"','"+stu.getSex()+"','"+stu.getAge()+"',"+stu.getBj().getId()+")";
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
	
	public boolean update(Student stu)
	{     
		boolean flag=false;
		  try {
			  getStatement();
			//4，建立sql执行器
			String sql="update student set name=?,sex=?,age=?,bj_id=? where id=?";
			getPreparedStatement(sql);
			past.setString(1, stu.getName());
			past.setString(2,stu.getSex());
			past.setInt(3, stu.getAge());
			past.setInt(4, stu.getBj().getId());
			past.setInt(5, stu.getId());
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
	public boolean delete(Student stu)
	{
		boolean flag=false;
		 //1,添加jar包
		  try {
			  getStatement();
			//5,执行sql语句
			String str="delete from student where id="+"'"+stu.getId()+"'";
			int rs=stat.executeUpdate(str);
			if(rs>0) flag=true;
			//7，关闭连接
			 closeAll();
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	public List <Student> searchByCondition(Student stu)
	{
		List<Student> list=new ArrayList<>();
		 try {
				getStatement();
				//5,执行sql语句
				String where="";
				String str=" where ";
				if(!stu.getName().equals(""))
				{
					where+= str+" s.name like '%"+stu.getName()+"%'";
					str=" and ";
				}
				if(!stu.getSex().equals(""))
				{
					where+= str+ " sex='"+stu.getSex()+"'";
					str=" and ";
				}
				if(stu.getAge()!=-1)
				{
					where+= str+ " age="+stu.getAge()+"";
					str=" and ";
				}
				if(stu.getBj()!=null&&stu.getBj().getId()==-1)
				{
					where+= str+ " bj_id=0 or bj_id is null";
				}
				else if(stu.getBj()!=null&&stu.getBj().getId()!=0)
				{
					where+= str+ " bj_id="+stu.getBj().getId()+"";
				}
				
				
				String sql="select s.*,bj.name as bjName, bj.stuNums from student as s left join banji as bj on s.bj_id=bj.id "+where;
				rs=stat.executeQuery(sql);
				//6，处理结果集
				while(rs.next())
				{  
				Student student=new Student();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setAge(rs.getInt("age"));
				
				BanJi bj=new BanJi();
                bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("bjName"));
				bj.setStuNums(rs.getInt("stuNums"));
				
				student.setBj(bj);
				list.add(student);
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
