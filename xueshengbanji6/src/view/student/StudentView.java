package view.student;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;

import until.CallBack;
import until.Constant;
import view.ShowMessage;
import dao.BanJiDao;
import dao.StudentDao;
import entity.BanJi;
import entity.Student;

public class StudentView {

	List<Student> list = new ArrayList<Student>();
	List<BanJi> bjlist;
	StudentTableModel model = null;
	JTable table;
	JComboBox bjbox;
	StudentDao stuDao = new StudentDao();
	BanJiDao bjDao=new BanJiDao();
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JFrame frame;
    
	private static StudentView instance;
	private StudentView()
	{
		
	}
	public static StudentView getInstance()
	{
		if(instance==null)
		{
			instance=new StudentView();
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
		{   
			bjlist.clear();
			bjbox.removeAllItems();
			bjlist= bjDao.searchAll();
			bjbox.addItem(Constant.BJ_PROMOT_SELECT);
			for(BanJi bj:bjlist)
			{
			  bjbox.addItem(bj.getName());
			}
	        bjbox.addItem(Constant.BJ_NO_SET);
			refreshTable();
			frame.setVisible(true);
		}
	}
	public void init() {

		list = stuDao.searchAll();

		frame.setSize(Constant.STU_FRAME_WIDTH,Constant.STU_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(Constant.STU_FRAME_TITTLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// 姓名查询
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.STU_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(70, 30));
		panel1.add(nameText);
		// 性别查询
		JLabel sexLabel = new JLabel();
		sexLabel.setText(Constant.STU_SEX);
		panel1.add(sexLabel);

		sexText = new JTextField();
		sexText.setPreferredSize(new Dimension(70, 30));
		panel1.add(sexText);
		// 年龄查询
		JLabel ageLabel = new JLabel();
		ageLabel.setText(Constant.STU_AGE);
		panel1.add(ageLabel);

		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(70, 30));
		panel1.add(ageText);
		
		//班级查询
		JLabel bjLabel = new JLabel();
		bjLabel.setText(Constant.STU_BJ);
		panel1.add(bjLabel);

	    bjbox = new JComboBox();
		bjbox.setPreferredSize(new Dimension(100, 30));
		panel1.add(bjbox);
				
		bjlist= bjDao.searchAll();
		bjbox.addItem(Constant.BJ_PROMOT_SELECT);
		for(BanJi bj:bjlist)
		{
		  bjbox.addItem(bj.getName());
		}
        bjbox.addItem(Constant.BJ_NO_SET);
		JButton searchBtn = new JButton();
		searchBtn.setText(Constant.MES_SELECT);
		searchBtn.setPreferredSize(new Dimension(70, 30));
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = nameText.getText();
				String sex = sexText.getText();
				int age = -1;
				if (!ageText.getText().equals("")) {
					age = Integer.parseInt(ageText.getText());
				}
				
				Student condition=new Student();
				condition.setName(name);
				condition.setSex(sex);
				condition.setAge(age);
				BanJi bj=new BanJi();
				int index=bjbox.getSelectedIndex();
				if(index>0)
				{   if(index<=bjlist.size())
				{
					bj=bjlist.get(index-1);
				}
				else 
				{
					bj.setId(-1);
				}
				}
				
				condition.setBj(bj);
				list=stuDao.searchByCondition(condition);
				refreshTable(list);
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new StudentTableModel(list);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		// scroll.setViewportView(table);
		panel2.add(scroll);
		
  //  增加
		JButton addBtn = new JButton();
		addBtn.setText(Constant.MES_ADD);
		addBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(addBtn);
        
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddStudentView asv = AddStudentView.getInstance();

				asv.createFrame(new CallBack() {
					@Override
					public void call() {
						refreshTable();
					}
				});
			}
		});
		
//修改
		JButton modifyBtn = new JButton();
		modifyBtn.setText(Constant.MES_MODIFY);
		modifyBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(modifyBtn);
		modifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                 
				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(frame, Constant.MES_PROMOT_NODATE);

				} else {
					ModifyStudentView msv = ModifyStudentView.getInstance();

					msv.createFrame( new CallBack() {
						@Override
						public void call() {
							refreshTable();
						}

					}, list.get(index).getId());
				}
			}
		});
		//删除
		JButton deleteBtn = new JButton();
		deleteBtn.setText(Constant.MES_DELETE);
		deleteBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null, Constant.MES_PROMOT_NODATE);
				} else {
					int type = JOptionPane.showConfirmDialog(null, Constant.MES_PROMOT_DELETE);
					if (type == 0) {
						boolean  rs=stuDao.delete(list.get(index));
						ShowMessage.show(rs, Constant.MES_DELETE);
						refreshTable();

					}
				}

			}
		});
		frame.setVisible(true);

	}
	public void refreshTable() {
		list=stuDao.searchAll();
		model.setData(list);
		model.fireTableDataChanged();
	}
	public void refreshTable(List<Student> list) {
		model.setData(list);
		model.fireTableDataChanged();
	}
}
