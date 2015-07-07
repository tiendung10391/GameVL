package itpsoft.app.gamevl.entity;

import java.io.Serializable;

public class BoDeEntity implements Serializable {
	private int idBoDo;
	private String MaBoDe;
	private String TenBoDe;

	public String getMaBoDe() {
		return MaBoDe;
	}

	public int getIdBoDo() {
		return idBoDo;
	}

	public void setIdBoDo(int idBoDo) {
		this.idBoDo = idBoDo;
	}

	public void setMaBoDe(String maBoDe) {
		MaBoDe = maBoDe;
	}

	public String getTenBoDe() {
		return TenBoDe;
	}

	public void setTenBoDe(String tenBoDe) {
		TenBoDe = tenBoDe;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return TenBoDe;
	}
}
