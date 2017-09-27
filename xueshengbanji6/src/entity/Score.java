package entity;
public class Score {
  private int id;
  private Student stu;
  private Course cou;
  private int score;
  private String grade;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Student getStu() {
	return stu;
}
public void setStu(Student stu) {
	this.stu = stu;
}
public Course getCou() {
	return cou;
}
public void setCou(Course cou) {
	this.cou = cou;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}
public String getGrade() {
	return grade;
}
public void setGrade(String grade) {
	this.grade = grade;
}
}
