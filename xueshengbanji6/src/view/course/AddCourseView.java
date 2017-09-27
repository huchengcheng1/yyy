package view.course;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import until.CallBack;
import until.Constant;
import view.ShowMessage;
import dao.CourseDao;
import entity.Course;

public class AddCourseView {
	JTextField nameText;
	JFrame frame;
	CallBack callBack;
	CourseDao couDao = new CourseDao();
	private static AddCourseView instance;

	public static AddCourseView getInstance(
			) {

		if (instance == null) {
			instance = new AddCourseView( );
		}
		return instance;
	}

	private AddCourseView() {}

	public void createFrame(CallBack callBack) {
		this.callBack = callBack;
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			nameText.setText("");
			frame.setVisible(true);
		}

	}

	public void init() {

		frame.setSize(Constant.COURSE_ADD_FRAME_WIDTH,Constant.COURSE_ADD_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.COURSE_ADD_TITTLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(panel4);
		// ¿Î³ÌÃû³Æ
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.COURSE_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(nameText);

		JButton saveBtn = new JButton();
		saveBtn.setText(Constant.MES_SAVE);
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel4.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				Course cou = new Course();
				cou.setName(name);
				boolean flag = couDao.add(cou);
				
				ShowMessage.show(flag, Constant.MES_ADD);  
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);

	}

}
