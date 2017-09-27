package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import entity.BanJi;

public class BanJiView {

	List<BanJi> list = new ArrayList<BanJi>();
	BanJiTableModel model = null;
	JTable table;
	BanJiDao bjDao = new BanJiDao();
	JTextField nameText;
	JTextField stuNumsText;
	JFrame frame;
	private static BanJiView instance;
	private BanJiView ()
	{
		
	}
	public static BanJiView getInstance()
	{
		if(instance==null)
			instance=new BanJiView();
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
    		 refreshTable();
    		 frame.setVisible(true);
    	 }
     }
	public void init() {

		list = bjDao.searchAll();
	
		frame.setSize(Constant.BJ_FRAME_WIDTH, Constant.BJ_FRAME_HIGH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(Constant.BJ_FRAME_TITTLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// 姓名查询
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.BJ_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(nameText);
		
		// 班级人数查询
		JLabel stuNumsLabel = new JLabel();
		stuNumsLabel.setText(Constant.BJ_STUNUMS);
		panel1.add(stuNumsLabel);

		stuNumsText = new JTextField();
		stuNumsText.setPreferredSize(new Dimension(120, 30));
		panel1.add(stuNumsText);

		JButton searchBtn = new JButton();
		searchBtn.setText(Constant.MES_SELECT);
		searchBtn.setPreferredSize(new Dimension(90, 30));
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
			
				int stuNums = -1;
				if (!stuNumsText.getText().equals("")) {
					stuNums = Integer.parseInt(stuNumsText.getText());
				}
				BanJi condition=new BanJi();
				condition.setName(name);
				condition.setStuNums(stuNums);
				list=bjDao.searchByCondition(condition);
				refreshTable(list);
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new BanJiTableModel(list);
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
				AddBanJiView asv = AddBanJiView.getInstance();

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
					ModifyBanJiView msv = ModifyBanJiView.getInstance();

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
						boolean  rs=bjDao.delete(list.get(index));
						ShowMessage.show(rs, Constant.MES_DELETE);
						refreshTable();
					}
				}

			}
		});
		
		
		//课程管理
		JButton manageBtn = new JButton();
		manageBtn.setText(Constant.BJ_MANAGE_COURSE);
		manageBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(manageBtn);
		manageBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
                    int index=table.getSelectedRow();
                    if(index==-1)
                    {
                    	JOptionPane.showMessageDialog(null, Constant.MES_PROMOT_NODATE);
                    }
                    else
                    {
                    	ManageCourseView mcv=ManageCourseView.getInstance();
                    	mcv.CreatFrame(new CallBack() {
							public void call() {
								refreshTable();
							}
						},list.get(index).getId());
                    }
			}
		});
		
		
		frame.setVisible(true);

	}

	public void refreshTable() {
		list=bjDao.searchAll();
		model.setData(list);
		model.fireTableDataChanged();
	}
	public void refreshTable(List<BanJi> list) {
		model.setData(list);
		model.fireTableDataChanged();
	}

}
