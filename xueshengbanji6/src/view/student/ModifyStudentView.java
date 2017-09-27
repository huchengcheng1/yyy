package view.student;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import until.CallBack;
import until.Constant;
import view.ShowMessage;
import dao.BanJiDao;
import dao.StudentDao;
import entity.BanJi;
import entity.Student;

public class ModifyStudentView {
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JFrame frame;
	JComboBox bjbox;
	CallBack callBack;
	List<BanJi> bjlist;
	Student stu;
	StudentDao stuDao = new StudentDao();
    BanJiDao bjDao=new BanJiDao();
	private static ModifyStudentView instance;

	public static ModifyStudentView getInstance() {

		if (instance == null) {
			instance = new ModifyStudentView();
		}
		return instance;
	}

	public void createFrame( CallBack callBack, int id) {
		this.callBack = callBack;
		this.stu = stuDao.searchById(id);
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {	
			bjlist.clear();
		    bjbox.removeAllItems();
			bjlist= bjDao.searchAll();
			bjbox.addItem(Constant.BJ_PROMOT_SELECT);
			for(BanJi bj:bjlist)
			{
				bjbox.addItem(bj.getName());
				
			}
			frame.setVisible(true);
		}
		setTexts(stu);
	}

	public void setTexts(Student stu) {
		this.stu = stu;
		nameText.setText(stu.getName());
		sexText.setText(stu.getSex());
		ageText.setText(String.valueOf(stu.getAge()));
		setComBox();

	}

	public void init() {

		frame.setSize(Constant.STU_MODIFY_FRAME_WIDTH, Constant.STU_MODIFY_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.STU_MODIFY_TITTLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(panel4);
		mainPanel.add(panel5);
		// 姓名
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.STU_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		nameText.setText(stu.getName());
		panel1.add(nameText);
		// 性别
		JLabel sexLabel = new JLabel();
		sexLabel.setText(Constant.STU_SEX);
		panel2.add(sexLabel);

		sexText = new JTextField();
		sexText.setPreferredSize(new Dimension(120, 30));
		sexText.setText(stu.getSex());
		panel2.add(sexText);
		// 年龄
		JLabel ageLabel = new JLabel();
		ageLabel.setText(Constant.STU_AGE);
		panel3.add(ageLabel);

		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(120, 30));
		ageText.setText(String.valueOf(stu.getAge()));
		panel3.add(ageText);
		//班级
		JLabel bjLabel = new JLabel();
		bjLabel.setText(Constant.STU_BJ);
		panel4.add(bjLabel);

		bjbox = new JComboBox();
		bjbox.setPreferredSize(new Dimension(120, 30));
		panel4.add(bjbox);
		
		bjlist= bjDao.searchAll();
		bjbox.addItem(Constant.BJ_PROMOT_SELECT);
		for(BanJi bj:bjlist)
		{
			bjbox.addItem(bj.getName());
			
		}
		setComBox();

		JButton saveBtn = new JButton();
		saveBtn.setText(Constant.MES_SAVE);
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel5.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				String sex = sexText.getText();
				int age = Integer.parseInt(ageText.getText());
		
				stu.setName(name);
				stu.setSex(sex);
				stu.setAge(age);
				BanJi bj=new BanJi();
				int index=bjbox.getSelectedIndex();
				if(index>0)
				{
					bj=bjlist.get(index-1);
				}
				stu.setBj(bj);
				boolean rs = stuDao.update(stu);
				ShowMessage.show(rs, Constant.MES_MODIFY);
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);

	}
	private void setComBox()
	{
		bjbox.setSelectedIndex(0);
		for(int i=0;i<bjlist.size();i++)
		{
			if(bjlist.get(i).getId()==stu.getBj().getId())
			{
				bjbox.setSelectedIndex(i+1);
				break;
			}	
		}
	}

}
