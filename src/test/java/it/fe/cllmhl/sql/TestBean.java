package it.fe.cllmhl.sql;


public final class TestBean extends BaseDaoBean {

	private java.lang.Integer chiave;
	private java.lang.String stringa;
	private java.math.BigDecimal importo;
	private java.lang.Integer qty;
	private java.sql.Date data, datato; 


	public java.lang.Integer getChiave() {
		return chiave;
	}
	public void setChiave(java.lang.Integer chiave) {
		this.chiave = chiave;
	}

	public java.lang.String getStringa() {
		return stringa;
	}
	public void setStringa(java.lang.String stringa) {
		this.stringa = stringa;
	}

	public java.math.BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(java.math.BigDecimal importo) {
		this.importo = importo;
	}

	public java.lang.Integer getQty() {
		return qty;
	}
	public void setQty(java.lang.Integer qty) {
		this.qty = qty;
	}

	public java.sql.Date getData() {
		return data;
	}
	public void setData(java.sql.Date data) {
		this.data = data;
	}
 
	public java.sql.Date getDatato() {
		return datato;
	}
	public void setDatato(java.sql.Date datato) {
		this.datato = datato;
	} 
}