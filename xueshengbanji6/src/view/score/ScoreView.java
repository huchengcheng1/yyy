package view.score;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import until.Constant;
import view.ShowMessage;
import view.student.StudentView;
import dao.BanJiDao;
import dao.CourseDao;
import dao.ScoreDao;
//import dao.ScoreDao;
import entity.BanJi;
import entity.Course;
import entity.Score;
import entity.Student;
//import entity.Score;

public class ScoreView {
      JFrame frame;
      JComboBox combox;
      JComboBox combox2;
      JTextField nametext;
      JTable table;
      ScoreTableModel model = null;
      ScoreDao scDao = new ScoreDao();
      BanJiDao bjDao=new BanJiDao();
      CourseDao couDao=new CourseDao();
      List<Score> list = new ArrayList<Score>();
      List<BanJi> bjlist = new ArrayList<>();
      List<Course> coulist = new ArrayList<>();
      Set <Score> saveset=new HashSet<>();
      
      private static ScoreView instance;
      private ScoreView()
      {
      }
      public static ScoreView getInstance()
      {
    	  if(instance==null)
  		{
  			instance=new ScoreView();
  		}
  		return instance;
      }
      public void CreatFrame()
      {
    	  if(frame==null)
  		{
  			frame = new JFrame();
  			init();
  		}
    	  else
    	  {    nametext.setText("");
    	      bjlist.clear();
    	      bjlist=bjDao.searchAll();
    	      combox.removeAllItems();
    	      combox.addItem(Constant.BJ_PROMOT_SELECT);
    		  for(BanJi bj:bjlist)
    		  {
    			  combox.addItem(bj.getName());
    		  }
//    	       combox.setSelectedIndex(0);
    		  refreshTable();
    		  frame.setVisible(true);
    	  }
      }
      public void init()
      {  
    	  frame.setSize(Constant.SCORE_FRAME_WIDTH,Constant.SCORE_FRAME_HIGH);
    	  frame.setTitle(Constant.SCORE_FRAME_TITTLE);
    	  frame.setLocationRelativeTo(null);
    	  frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	  
    	  JPanel mainpanel=(JPanel) frame.getContentPane();
    	  BoxLayout boxlayout=new BoxLayout(mainpanel, BoxLayout.Y_AXIS);
  		  mainpanel.setLayout(boxlayout);
  		  
  		  JPanel p1=new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
		  JPanel p2=new JPanel();
		  JPanel p3=new JPanel(new FlowLayout(FlowLayout.CENTER));
		  mainpanel.add(p1);
		  mainpanel.add(p2);
		  mainpanel.add(p3);
		  
    	  
		  JLabel namelab=new JLabel();
		  namelab.setPreferredSize(new Dimension(30,40));
		  namelab.setText(Constant.SCORE_NAME);
		  p1.add(namelab);
		  
		  nametext=new JTextField();
		  nametext.setPreferredSize(new Dimension(100,40));
		  p1.add(nametext);
		  
		  JLabel bjlab=new JLabel();
		  bjlab.setPreferredSize(new Dimension(30,40));
		  bjlab.setText(Constant.SCORE_BJ);
		  p1.add(bjlab);
		  
		  combox=new JComboBox();
		  combox.setPreferredSize(new Dimension(100,40));
		
		  bjlist= bjDao.searchAll();

		  combox.addItem(Constant.BJ_PROMOT_SELECT);
		  for(BanJi bj:bjlist)
		  {
			  combox.addItem(bj.getName());
		  }
    	 
    	  combox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int index=combox.getSelectedIndex();
				if(index>0)
				{
					coulist=bjDao.searchByCourse(bjlist.get(index-1));
				}
				else
				{
					coulist=couDao.searchAll();
				}
				refashCourseBox(coulist);
			}
		});
    	  p1.add(combox);
    	  
		  JLabel coulab=new JLabel();
		  coulab.setPreferredSize(new Dimension(30,40));
		  coulab.setText(Constant.SCORE_COU);
		  p1.add(coulab);
		  
		  combox2=new JComboBox();
		  combox2.setPreferredSize(new Dimension(100,40));
		  coulist= couDao.searchAll();
  
		  refashCourseBox(coulist);
		
		  p1.add(combox2);
		  
		  JButton selectbtn=new JButton();
		  selectbtn.setPreferredSize(new Dimension(70,40));
		  selectbtn.setText(Constant.SCORE_SELECT);
		  selectbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				  
				Score sc=new Score();
				
				Student stu=new Student();		
			    stu.setName(nametext.getText());	
			
				BanJi bj=new BanJi();
				int index=combox.getSelectedIndex();
				int bjid=-1;
				if(index>0)
				{
					bjid=bjlist.get(index-1).getId();
				}
				 bj.setId(bjid);
				 stu.setBj(bj);
				 sc.setStu(stu);
				Course cu=new Course();
				
				int index2=combox2.getSelectedIndex();
				int couid=-1;
				if(index2>0)
				{
					couid=coulist.get(index2-1).getId();
				}
				 cu.setId(couid);
			    sc.setCou(cu);
				list=scDao.searchCondition(sc);
				refreshTable(list);
			}
		});
		  p1.add(selectbtn);
		  
		  
		  table=new JTable();
		  list=scDao.searchAll();
		  model = new ScoreTableModel(list,saveset);
		  table.setModel(model);
		  
		  
		  JScrollPane scoll=new JScrollPane(table);
		  scoll.setPreferredSize(new Dimension(600,400));
		  p2.add(scoll);
		  
		  JButton savebtn =new JButton();
		  savebtn.setPreferredSize(new Dimension(100,50));
		  savebtn.setText(Constant.SCORE_SAVE);
		  savebtn.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
                 boolean flag=scDao.save(saveset);
                 ShowMessage.show(flag,Constant.SCORE_SAVE);
                 saveset.clear();
	             refreshTable();
			}
		});
		  p3.add(savebtn);
    	  frame.setVisible(true);
      }
    private void refashCourseBox(List<Course> coulist2) {
          combox2.removeAllItems();
          combox2.addItem(Constant.BJ_PROMOT_SELECT);
		  for(Course cou:coulist2)
		  {
			  combox2.addItem(cou.getName());
		  }
	}
	public void refreshTable() {
  		list=scDao.searchAll();
  		model.setData(list);
  		model.fireTableDataChanged();
  	}
  	public void refreshTable(List<Score> list) {
  		model.setData(list);
  		model.fireTableDataChanged();
  	}
}
