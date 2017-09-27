package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import until.Constant;
import view.banji.BanJiView;
import view.course.CourseView;
import view.score.ScoreView;
import view.student.StudentView;

public class MainView extends JFrame {
       
	public void init()
	{    
		this.setSize(Constant.MAINVIEW_WIDTH, Constant.MAINVIEW_HIGH);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		String path="beijing.jpg";
		ImageIcon beijing= new ImageIcon(path);
		JLabel lable=new JLabel(beijing);
		lable.setBounds(0, 0,this.getWidth(), this.getHeight());
		
		
		JPanel mainpan= (JPanel) this.getContentPane();
		mainpan.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		JButton stubtn=new JButton(Constant.MAINVIEW_STUDENT);
		stubtn.setPreferredSize(new Dimension(120,70));
		stubtn.setFont(new Font(null,Font.BOLD,20));
		JButton bjbtn=new JButton(Constant.MAINVIEW_BANJI);
		bjbtn.setPreferredSize(new Dimension(120,70));
		bjbtn.setFont(new Font(null,Font.BOLD,20));
		JButton coubtn=new JButton(Constant.MAINVIEW_COURSE);
		coubtn.setPreferredSize(new Dimension(120,70));
		coubtn.setFont(new Font(null,Font.BOLD,20));
		JButton scbtn=new JButton(Constant.MAINVIEW_SC);
		scbtn.setPreferredSize(new Dimension(120,70));
		scbtn.setFont(new Font(null,Font.BOLD,20));
		 
		mainpan.add(stubtn);
		mainpan.add(bjbtn);
		mainpan.add(coubtn);
		mainpan.add(scbtn);
		mainpan.setOpaque(false);
		this.getLayeredPane().add(lable,new Integer(Integer.MIN_VALUE));
		
		
		stubtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentView sv = StudentView.getInstance();
				sv.CreatFrame();
				
			}
		});
		bjbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BanJiView bv=BanJiView.getInstance();
				bv.CreatFrame();
				
			}
		});
		coubtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CourseView cv =CourseView.getInstance();
				cv.CreatFrame();
				
			}
		});
		scbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ScoreView scv=ScoreView.getInstance();
				scv.CreatFrame();
				
			}
		});	
		this.setVisible(true);
	}
	public static void main(String[] args) {
		  new MainView().init();
	}
}
