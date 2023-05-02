package mvccrudpackage.model.bean;

public class Employee {
	 protected int Eid;
	 protected String Ename;
	 protected int Eage;
	 public Employee() {}
	 public Employee(String Ename, int Eage) {
	 this.Ename = Ename;
	 this.Eage = Eage;
	 }
	 public Employee(int Eid, String Ename, int Eage) {
	 this.Eid = Eid;
	 this.Ename = Ename;
	 this.Eage = Eage;
	 }
	 public int getEid() {
	 return Eid;
	 }
	 public void setEid(int Eid) {
	 this.Eid = Eid;
	 }
	 public String getEname() {
	 return Ename;
	 }
	 public void setEname(String Ename) {
	 this.Ename = Ename;
	 }
	 public int getEage() {
	 return Eage;
	 }
	 public void setEage(int Eage) {
	 this.Eage = Eage;
	 }
	}
