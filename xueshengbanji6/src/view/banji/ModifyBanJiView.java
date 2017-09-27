package view.banji;

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
import dao.BanJiDao;
import entity.BanJi;

public class ModifyBanJiView {
	JTextField nameText;
	JFrame frame;
	CallBack callBack;
	BanJi bj;
	BanJiDao bjDao = new BanJiDao();

	private static ModifyBanJiView instance;

	public static ModifyBanJiView getInstance() {

		if (instance == null) {
			instance = new ModifyBanJiView();
		}
		return instance;
	}

	public void createFrame( CallBack callBack, int id) {
		this.callBack = callBack;
		this.bj = bjDao.searchById(id);
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			frame.setVisible(true);
		}
		setTexts(bj);
	}

	public void setTexts(BanJi bj) {
		this.bj = bj;
		nameText.setText(bj.getName());

	}

	public void init() {

		// frame = new JFrame();
		frame.setSize(Constant.BJ_MODIFY_FRAME_WIDTH, Constant.BJ_ADD_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.BJ_MODIFY_TITTLE);

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
		// Ãû³Æ
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.BJ_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		nameText.setText(bj.getName());
		panel1.add(nameText);
		
		JButton saveBtn = new JButton();
		saveBtn.setText(Constant.MES_SAVE);
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel4.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				bj.setName(name);
				boolean rs = bjDao.update(bj);
				ShowMessage.show(rs, Constant.MES_MODIFY);
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);

	}

}
