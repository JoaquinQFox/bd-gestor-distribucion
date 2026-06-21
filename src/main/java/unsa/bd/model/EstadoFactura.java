package unsa.bd.model;

public class EstadoFactura {
    private String estFacCod;
    private String estFacNom;
    private String estFacEstReg;

    public EstadoFactura() {
    }

    public EstadoFactura(String estFacCod, String estFacNom, String estFacEstReg) {
        this.estFacCod = estFacCod;
        this.estFacNom = estFacNom;
        this.estFacEstReg = estFacEstReg;
    }

    public String getEstFacCod() {
        return estFacCod;
    }

    public void setEstFacCod(String estFacCod) {
        this.estFacCod = estFacCod;
    }

    public String getEstFacNom() {
        return estFacNom;
    }

    public void setEstFacNom(String estFacNom) {
        this.estFacNom = estFacNom;
    }

    public String getEstFacEstReg() {
        return estFacEstReg;
    }

    public void setEstFacEstReg(String estFacEstReg) {
        this.estFacEstReg = estFacEstReg;
    }
}
