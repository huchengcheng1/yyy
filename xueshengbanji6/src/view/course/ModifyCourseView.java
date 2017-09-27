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

public class ModifyCourseView {
	JTextField nameText;
	JFrame frame;
	CallBack callBack;
	Course cou;
	CourseDao couDao = new CourseDao();

	private static ModifyCourseView instance;

	public static ModifyCourseView getInstance() {

		if (instance == null) {
			instance = new ModifyCourseView();
		}
		return instance;
	}

	public void createFrame( CallBack callBack, int id) {
		this.callBack = callBack;
		this.cou = couDao.searchById(id);
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			frame.setVisible(true);
		}
		setTexts(cou);
	}

	public void setTexts(Course cou) {
		this.cou = cou;
		nameText.setText(cou.getName());

	}

	public void init() {

		frame.setSize(Constant.COURSE_MODIFY_FRAME_WIDTH, Constant.COURSE_MODIFY_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.COURSE_MODIFY_TITTLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		// Ãû³Æ
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.COURSE_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		nameText.setText(cou.getName());
		panel1.add(nameText);
		
		JButton saveBtn = new JButton();
		saveBtn.setText(Constant.MES_SAVE);
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel2.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				cou.setName(name);
				boolean rs = couDao.update(cou);
				ShowMessage.show(rs, Constant.MES_MODIFY);
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);

	}

}
