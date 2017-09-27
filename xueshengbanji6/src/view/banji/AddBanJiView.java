package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import until.CallBack;
import until.Constant;
import view.ShowMessage;
import dao.BanJiDao;
import entity.BanJi;

public class AddBanJiView {
	JTextField nameText;
	JFrame frame;
	CallBack callBack;
	BanJiDao bjDao = new BanJiDao();
	private static AddBanJiView instance;

	public static AddBanJiView getInstance(
			) {

		if (instance == null) {
			instance = new AddBanJiView( );
		}
		return instance;
	}

	private AddBanJiView() {}

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

		frame.setSize(Constant.BJ_ADD_FRAME_WIDTH,Constant.BJ_ADD_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.BJ_ADD_TITTLE);

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
		// °à¼¶Ãû³Æ
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.BJ_NAME);
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
				BanJi bj = new BanJi();
				bj.setName(name);
				boolean flag = bjDao.add(bj);
				ShowMessage.show(flag, Constant.MES_ADD);
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);

	}

}
