package view.score;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import until.Constant;
import entity.Score;

public class ScoreTableModel extends AbstractTableModel {
	List<Score> list = null;
	Set <Score> saveset=new HashSet<>(); 
	String[] columnNames = {Constant.SCORE_NAME,Constant.SCORE_BJ,Constant.SCORE_COU,Constant.SCORE_SCORE,Constant.SCORE_GRADE};
	public ScoreTableModel(List<Score> list,Set<Score> saveset) {
		this.list = list;
		this.saveset=saveset;
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
			return list.get(rowIndex).getStu().getName();
			
		   }  
		 if(columnIndex == 1)
			{
			 return list.get(rowIndex).getStu().getBj().getName();
			}
		 if(columnIndex == 2)
		   {
			return list.get(rowIndex).getCou().getName();
		   }
		 if(columnIndex == 3)
			{
			    if(list.get(rowIndex).getScore()==-1)
			    	return "";
				return list.get(rowIndex).getScore();
			}
		 if(columnIndex == 4)
			{
				return list.get(rowIndex).getGrade();
			}
			return null;
	}

	public void setData(List<Score> list) {
		this.list = list;
	}
	 public boolean isCellEditable(int row,int column)  
	 {      
		    if(column==3)
	        return true; //单元格可以修改
		    else 
		    return false;//单元格不可修改
	 }
	 public void setValueAt(Object obj,int rowIndex, int columnIndex)
		{
			String string=new String();
			string=String.valueOf(obj);
			list.get(rowIndex).setScore(Integer.parseInt(string));
			saveset.add(list.get(rowIndex));
		}

}
