package unsa.bd.modules.ventas.factura.tipofactura;

public class TipoFactura {

    private String tipFacCod;
    private String tipFacNom;
    private String tipFacEstReg;

    public TipoFactura() {}

    public TipoFactura(String tipFacCod, String tipFacNom, String tipFacEstReg) {
        this.tipFacCod = tipFacCod;
        this.tipFacNom = tipFacNom;
        this.tipFacEstReg = tipFacEstReg;
    }

    public String getTipFacCod() {
        return tipFacCod;
    }

    public void setTipFacCod(String tipFacCod) {
        this.tipFacCod = tipFacCod;
    }

    public String getTipFacNom() {
        return tipFacNom;
    }

    public void setTipFacNom(String tipFacNom) {
        this.tipFacNom = tipFacNom;
    }

    public String getTipFacEstReg() {
        return tipFacEstReg;
    }

    public void setTipFacEstReg(String tipFacEstReg) {
        this.tipFacEstReg = tipFacEstReg;
    }
}
