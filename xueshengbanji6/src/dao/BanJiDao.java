package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BanJi;
import entity.Course;

public class BanJiDao extends BaseDao {
	
	
	public List <BanJi> searchAll()
	{
		List<BanJi> list=new ArrayList<>();
		 try {
			getStatement();
			//5,执行sql语句
			rs=stat.executeQuery("select * from banji");
			//6，处理结果集
			while(rs.next())
			{   
				BanJi banji=new BanJi();
				banji.setId(rs.getInt("id"));
				banji.setName(rs.getString("name"));
				banji.setStuNums(rs.getInt("stuNums"));
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
	public BanJi searchById(int id)
	{      
		BanJi banji=new BanJi();
		 try {
			getStatement();
			//5,执行sql语句
			rs=stat.executeQuery("select * from banji where id="+id);
			//6，处理结果集
			while(rs.next())
			{   
				banji.setId(rs.getInt("id"));
				banji.setName(rs.getString("name"));
				banji.setStuNums(rs.getInt("stuNums"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally{
			//7，关闭连接
			 closeAll();
		 }
		return banji;
	}
	public boolean add (BanJi banji)
	{    boolean flag=false;
		
		  try {
			  getStatement();
			//5,执行sql语句
			String str="insert into banji(name,stuNums) values('"+banji.getName()+"',0)";
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
	
	public boolean update(BanJi banji)
	{     
		boolean flag=false;
		  try {
			  getStatement();
			//4，建立sql执行器
			String sql="update banji set name=? where id=?";
			getPreparedStatement(sql);
			past.setString(1, banji.getName());
			past.setInt(2, banji.getId());
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
	public boolean delete(BanJi banji)
	{
		boolean flag=false;
		 //1,添加jar包
		  try {
			  getStatement();
			 String str2="update student set bj_id=0 where bj_id="+banji.getId();
			  stat.executeUpdate(str2);
			//5,执行sql语句
			String str="delete from banji where id="+"'"+banji.getId()+"'";
			int rs=stat.executeUpdate(str);
			if(rs>0) flag=true;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {closeAll();}
		return flag;
	}
	public List <BanJi> searchByCondition(BanJi banji)
	{
		List<BanJi> list=new ArrayList<>();
		 try {
				getStatement();
				//5,执行sql语句
				String where="";
				String str=" where ";
				if(!banji.getName().equals(""))
				{
					where+= str+" name like '%"+banji.getName()+"%'";
					str=" and ";
				}
				if(banji.getStuNums()!=-1)
				{
					where+= str+ " stuNums='"+banji.getStuNums()+"'";
				}
				String sql="select * from banji "+where;
				rs=stat.executeQuery(sql);
				//6，处理结果集
				while(rs.next())
				{  
				BanJi banji1=new BanJi();
				banji1.setId(rs.getInt("id"));
				banji1.setName(rs.getString("name"));
				banji1.setStuNums(rs.getInt("stuNums"));
				list.add(banji1);
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

	public List<Course> searchByCourse(BanJi bj)

	{
		List<Course> list=new ArrayList<>();
		 try {
				getStatement();
				//5,执行sql语句
				String str="select c.id as id ,c.cname as name from banji  as bj INNER JOIN m_bj_cs as ms on bj.id=ms.bj_id INNER JOIN course as c on ms.cs_id=c.id where bj.id="+""+bj.getId()+"";
				rs=stat.executeQuery(str);
				//6，处理结果集
				while(rs.next())
				{  
				Course cou=new Course();
				cou.setId(rs.getInt("id"));
				cou.setName(rs.getString("name"));
				list.add(cou);
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
	public List<Course> searchNotInCouse(BanJi bj)
	{
		List<Course> list=new ArrayList<>();
		 try {
				getStatement();
				//5,执行sql语句
				String str=" select id,cname from course where id not in (select c.id as id  from banji  as bj INNER JOIN m_bj_cs as ms on bj.id=ms.bj_id INNER  JOIN course as c on ms.cs_id=c.id where bj.id="+""+bj.getId()+""+")";
				rs=stat.executeQuery(str);
				//6，处理结果集
				while(rs.next())
				{  
				Course cou=new Course();
				cou.setId(rs.getInt("id"));
				cou.setName(rs.getString("cname"));
				list.add(cou);
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
    public boolean addCourse(int  bjid,int cid )
    {
    	 boolean flag=false;
 		
		  try {
			  getStatement();
			//5,执行sql语句
			String str="INSERT into m_bj_cs (bj_id,cs_id) VALUES ("+bjid+","+cid+") ";
			
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
    public boolean deleteCourse(int bjid,int cid)
    {
    	 boolean flag=false;
  		
		  try {
			  getStatement();
			//5,执行sql语句
			String str="DELETE from m_bj_cs where bj_id ="+bjid+" and cs_id="+cid+"";
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
}
