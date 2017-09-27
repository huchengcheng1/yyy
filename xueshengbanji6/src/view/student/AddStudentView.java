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

public class AddStudentView {
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JComboBox  bjbox;
	JFrame frame;
	CallBack callBack;
	List<BanJi> bjlist;
	StudentDao stuDao = new StudentDao();
	BanJiDao bjDao = new BanJiDao();
	private static AddStudentView instance;

	public static AddStudentView getInstance(
			) {

		if (instance == null) {
			instance = new AddStudentView( );
		}
		return instance;
	}

	private AddStudentView() {}

	public void createFrame(CallBack callBack) {
		this.callBack = callBack;
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
			nameText.setText("");
			sexText.setText("");
			ageText.setText("");
			bjbox.setSelectedIndex(0);
			frame.setVisible(true);
		}

	}

	public void init() {

		frame.setSize(Constant.STU_ADD_FRAME_WIDTH, Constant.STU_ADD_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.STU_ADD_TITTLE);

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
		panel1.add(nameText);
		// 性别
		JLabel sexLabel = new JLabel();
		sexLabel.setText(Constant.STU_SEX);
		panel2.add(sexLabel);

		sexText = new JTextField();
		sexText.setPreferredSize(new Dimension(120, 30));
		panel2.add(sexText);
		// 年龄
		JLabel ageLabel = new JLabel();
		ageLabel.setText(Constant.STU_AGE);
		panel3.add(ageLabel);

		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(120, 30));
		panel3.add(ageText);
		//班级
		JLabel bjidLabel = new JLabel();
		bjidLabel.setText(Constant.STU_BJ);
		panel4.add(bjidLabel);

		bjbox = new JComboBox();
		bjbox.setPreferredSize(new Dimension(120, 30));
		panel4.add(bjbox);
		
		bjlist= bjDao.searchAll();
		bjbox.addItem(Constant.BJ_PROMOT_SELECT);
		for(BanJi bj:bjlist)
		{
			bjbox.addItem(bj.getName());
		}

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
				int index=bjbox.getSelectedIndex();
				BanJi bj=new BanJi();
				if(index>0)
				{
					bj=bjlist.get(index-1);
				}
				Student stu = new Student();
				stu.setName(name);
				stu.setSex(sex);
				stu.setAge(age);
				stu.setBj(bj);
				boolean flag = stuDao.add(stu);
				ShowMessage.show(flag, Constant.MES_ADD); 
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);

	}

}
