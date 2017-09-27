package view.course;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import until.Constant;
import entity.BanJi;
import entity.Course;

public class CourseTableModel extends AbstractTableModel {
	List<Course> list = null;
	String[] columnNames = {Constant.COURSE_ID,Constant.COURSE_NAME};

	public CourseTableModel(List<Course> list) {
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
	    else if (columnIndex == 1) {
			return list.get(rowIndex).getName();
		} else {
			return null;
		}
	}
	public void setData(List<Course> list) {
		this.list = list;
	}

}
