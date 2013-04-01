package it.fe.cllmhl.sql;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public final class TestBean extends BaseDaoBean {

	private Integer chiave;
	private String stringa;
	private BigDecimal importo;
	private Integer qty;
	private Date data, datato; 


	public Integer getChiave() {
		return chiave;
	}
	public void setChiave(Integer chiave) {
		this.chiave = chiave;
	}

	public String getStringa() {
		return stringa;
	}
	public void setStringa(String stringa) {
		this.stringa = stringa;
	}

	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
 
	public Date getDatato() {
		return datato;
	}
	public void setDatato(Date datato) {
		this.datato = datato;
	} 
}