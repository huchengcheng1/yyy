package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import entity.BanJi;
import entity.Course;
import entity.Score;
import entity.Student;

public class ScoreDao extends BaseDao {
      
	public List <Score> searchAll()
	{
		List<Score> list=new ArrayList<>();
		 try {
			getStatement();
			String sql="select * from v_stu_bj_cou_score";
			//5,执行sql语句
			rs=stat.executeQuery(sql);
			//6，处理结果集
			while(rs.next())
			{   
				 Score sc=new Score();
				 sc.setId(rs.getInt("scID"));
				 if(rs.getString("score")!=null)
				 {
				 sc.setScore(rs.getInt("score"));
				 }
				 else
				 {
					 sc.setScore(-1);
				 }
				 sc.setGrade(rs.getString("grade"));
				 
				 Student stu=new Student();
				 stu.setId(rs.getInt("stuID"));
				 stu.setName(rs.getString("stuName"));
				 
				 BanJi bj=new BanJi();
				 bj.setId(rs.getInt("bjID"));
				 bj.setName(rs.getString("bjName"));
				 
				 stu.setBj(bj);
				 
				 Course cou =new Course();
				 cou.setId(rs.getInt("couID"));
				 cou.setName(rs.getString("couName"));
				 
				 sc.setStu(stu);
				 sc.setCou(cou);
				 
				 list.add(sc);
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
    public List<Score> searchCondition(Score sc)
    {
    	List<Score> list=new ArrayList<>();
		 try {
				getStatement();
				//5,执行sql语句
				String where="";
				if(!sc.getStu().getName().equals(""))
				{
					where+=" and stuName like '%"+sc.getStu().getName()+"%'";
				}
				if(sc.getStu().getBj().getId()!=-1)
				{
					where+=" and bjID="+sc.getStu().getBj().getId();
			
				}
				if(sc.getCou().getId()!=-1)
				{
					where+=" and couID="+sc.getCou().getId();
				}
				String sql="select * from v_stu_bj_cou_score where 1=1 "+where;
				rs=stat.executeQuery(sql);
				//6，处理结果集
				while(rs.next())
				{   
					 Score sc1=new Score();
					 sc1.setId(rs.getInt("scID"));
					 if(rs.getString("score")!=null)
					 {
					 sc1.setScore(rs.getInt("score"));
					 }
					 else
					 {
						 sc1.setScore(-1);
					 }
					 sc1.setGrade(rs.getString("grade"));
					 
					 Student stu=new Student();
					 stu.setId(rs.getInt("stuID"));
					 stu.setName(rs.getString("stuName"));
					 
					 BanJi bj=new BanJi();
					 bj.setId(rs.getInt("bjID"));
					 bj.setName(rs.getString("bjName"));
					 
					 stu.setBj(bj);
					 
					 Course cou =new Course();
					 cou.setId(rs.getInt("couID"));
					 cou.setName(rs.getString("couName"));
					 
					 sc1.setStu(stu);
					 sc1.setCou(cou);
					 
					 list.add(sc1);
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
	public boolean save(Set<Score> saveset) {
		boolean flag=false;
		for(Score sc:saveset)
		{
			if(sc.getId()==0)
			{
				flag=add(sc);
			}
			else
				flag=update(sc);
		}
		return flag;
	}
	private boolean update(Score sc) {
	    boolean flag=false;
		try {
			  String sql="update score set score=? where id=?";
			  getPreparedStatement(sql);
			  past.setInt(1, sc.getScore());
			  past.setInt(2, sc.getId());
			  int result=past.executeUpdate();
			  if(result>0)
			  {
				  flag=true;
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;
	}
	private boolean add(Score sc) {
        boolean flag=false;
        try {
        	getStatement();
        	String sql="insert into score(s_id,c_id,score) values("+sc.getStu().getId()+","+sc.getCou().getId()+","+sc.getScore()+")";
			int result= stat.executeUpdate(sql);
			if(result>0)
				flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;
	}
}
