package view.student;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.BanJiDao;
import until.Constant;
import entity.BanJi;
import entity.Student;

public class StudentTableModel extends AbstractTableModel {
	List<Student> list = null;
	BanJiDao bjDao=new BanJiDao();
	String[] columnNames = {Constant.STU_ID, Constant.STU_NAME,Constant.STU_SEX, Constant.STU_AGE ,Constant.STU_BJ};
	public StudentTableModel(List<Student> list) {
		this.list = list;

	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public String getColumnName(int columnIndex) {

		return columnNames[columnIndex];

	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return list.get(rowIndex).getId();
		}
		 if (columnIndex == 1) {
			return list.get(rowIndex).getName();
		}  if (columnIndex == 2) {
			return list.get(rowIndex).getSex();

		}  if (columnIndex == 3) {
			return list.get(rowIndex).getAge();
		}
		 if (columnIndex == 4) {
			 return list.get(rowIndex).getBj().getName();
		}
			return null;
	}

	public void setData(List<Student> list) {
		this.list = list;
	}

}
