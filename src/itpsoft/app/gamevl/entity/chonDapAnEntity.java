package itpsoft.app.gamevl.entity;

import java.io.Serializable;

public class chonDapAnEntity implements Serializable {
	private int id;
	private String MaCH;
	private String MaBoDe;
	private String CauHoi;
	private String DapAnDung;
	private String DapAnSai1;
	private String DapAnSai2;
	private String DapAnSai3;
	private String MoTa;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMaCH() {
		return MaCH;
	}
	public void setMaCH(String maCH) {
		MaCH = maCH;
	}
	public String getMaBoDe() {
		return MaBoDe;
	}
	public void setMaBoDe(String maBoDe) {
		MaBoDe = maBoDe;
	}
	public String getCauHoi() {
		return CauHoi;
	}
	public void setCauHoi(String cauHoi) {
		CauHoi = cauHoi;
	}
	public String getDapAnDung() {
		return DapAnDung;
	}
	public void setDapAnDung(String dapAnDung) {
		DapAnDung = dapAnDung;
	}
	public String getDapAnSai1() {
		return DapAnSai1;
	}
	public void setDapAnSai1(String dapAnSai1) {
		DapAnSai1 = dapAnSai1;
	}
	public String getDapAnSai2() {
		return DapAnSai2;
	}
	public void setDapAnSai2(String dapAnSai2) {
		DapAnSai2 = dapAnSai2;
	}
	public String getDapAnSai3() {
		return DapAnSai3;
	}
	public void setDapAnSai3(String dapAnSai3) {
		DapAnSai3 = dapAnSai3;
	}
	public String getMoTa() {
		return MoTa;
	}
	public void setMoTa(String moTa) {
		MoTa = moTa;
	}
	
	
	
}
