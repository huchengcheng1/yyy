package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import dao.BanJiDao;
import until.CallBack;
import until.Constant;
import view.ShowMessage;
import view.course.CourseTableModel;
import entity.BanJi;
import entity.Course;

public class ManageCourseView {
	
	List <Course> list=new ArrayList<>();
	List <Course> list2=new ArrayList<>();
	JFrame frame;
	JTable table;
	JComboBox combox;
	CourseTableModel model=null;
	CallBack callback;
	BanJi bj;
	BanJiDao bjDao = new BanJiDao();
	JLabel showlab;
    private static  ManageCourseView instance;
    private ManageCourseView()
    {	
    }
    public static ManageCourseView getInstance()
    {
    	if(instance==null)
    	{
    		instance=new ManageCourseView();
    	}
    	return instance;
    }
    public void CreatFrame(CallBack callback,int id)
    {
    	this.callback=callback;
    	this.bj=bjDao.searchById(id);
    	if(frame==null)
    	{
    		frame=new JFrame();
    		init();
    	}
    	else
    	{    
    		showlab.setText(bj.getName());
    		list.clear();
    		list=bjDao.searchByCourse(bj);
    		model=new CourseTableModel(list);
    		table.setModel(model);
    		
    		list2.clear();
    		list2=bjDao.searchNotInCouse(bj);
    		combox.removeAllItems();
    		for(Course cou:list2)
    		{
    			combox.addItem(cou.getName());
    		}
    		model.fireTableDataChanged();
    		frame.setVisible(true);
    	}
    }
	public void init()
	{
		frame.setSize(Constant.MANAGECOURSE_WIDTH, Constant.MANAGECOURSE_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(Constant.MANAGECOURSE_TITTLE);
		
		JPanel mainpanel=(JPanel) frame.getContentPane();
		BoxLayout boxlayout=new BoxLayout(mainpanel, BoxLayout.Y_AXIS);
		mainpanel.setLayout(boxlayout);
		
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel(new FlowLayout(FlowLayout.CENTER,50,20));
		mainpanel.add(p1);
		mainpanel.add(p2);
		mainpanel.add(p3);
		
		showlab=new JLabel();
		showlab.setText(bj.getName());
		showlab.setFont(new Font("¿¬Ìå", 1, 20));
		showlab.setHorizontalAlignment(SwingConstants.CENTER);
		showlab.setPreferredSize(new Dimension(370,60));
		p1.add(showlab);
		
		list=bjDao.searchByCourse(bj);
		table=new JTable();
		model=new CourseTableModel(list);
		table.setModel(model);

		
		JScrollPane scorll=new JScrollPane(table);
		scorll.setPreferredSize(new Dimension(370,270));
		p2.add(scorll);
		
		list2=bjDao.searchNotInCouse(bj);
		combox=new JComboBox();
//		combox.addItem(Constant.BJ_PROMOT_SELECT);
		for(Course cou:list2)
		{
			combox.addItem(cou.getName());
		}
		p3.add(combox);
		
		JButton addbtn=new JButton();
		addbtn.setText(Constant.MANAGECOURSE_ADDCOURSE);
		addbtn.setPreferredSize(new Dimension(120,40));
		addbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				 int index=combox.getSelectedIndex();
				 int cid=list2.get(index).getId();
				 int bjid=bj.getId();
				 boolean rs=bjDao.addCourse(bjid, cid);
				 ShowMessage.show(rs, Constant.MES_ADD);
                 reflashTable();
			}
		});
		p3.add(addbtn);
		
		
		JButton deletebtn=new JButton();
		deletebtn.setText(Constant.MES_DELETE);
		deletebtn.setPreferredSize(new Dimension(70,40));
		deletebtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				 int index=table.getRowCount();
				 if (index == -1) {
				JOptionPane.showMessageDialog(frame, Constant.MES_PROMOT_NODATE);

				} 
				 else{
			     int type = JOptionPane.showConfirmDialog(null, Constant.MES_PROMOT_DELETE);
				 if(type==0){
			     int cid=list.get(index-1).getId();//Ë¼¿¼
				 int bjid=bj.getId();
				 boolean rs=bjDao.deleteCourse(bjid, cid);
				 ShowMessage.show(rs, Constant.MES_DELETE);
				 reflashTable();
				 }
				 }
				
			}
		});
		p3.add(deletebtn);
		
		frame.setVisible(true);
	}
	public void reflashTable()
	{    
		list.clear();
		list=bjDao.searchByCourse(bj);
		 
		list2.clear();
 		list2=bjDao.searchNotInCouse(bj);
 		combox.removeAllItems();
 		for(Course cou:list2)
 		{
 			combox.addItem(cou.getName());
 		}
		 model.setData(list);
		 model.fireTableDataChanged();
	}
}
