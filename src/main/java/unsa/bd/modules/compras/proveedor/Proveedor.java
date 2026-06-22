package unsa.bd.modules.compras.proveedor;

public class Proveedor {
    private String prvCod;
    private String prvNom;
    private String prvRUC;
    private String prvCor;
    private String prvEstReg;

    public Proveedor() {}

    public Proveedor(String prvCod, String prvNom, String prvRUC, String prvCor, String prvEstReg) {
        this.prvCod = prvCod;
        this.prvNom = prvNom;
        this.prvRUC = prvRUC;
        this.prvCor = prvCor;
        this.prvEstReg = prvEstReg;
    }

    public String getPrvCod() {
        return prvCod;
    }

    public void setPrvCod(String prvCod) {
        this.prvCod = prvCod;
    }

    public String getPrvNom() {
        return prvNom;
    }

    public void setPrvNom(String prvNom) {
        this.prvNom = prvNom;
    }

    public String getPrvRUC() {
        return prvRUC;
    }

    public void setPrvRUC(String prvRUC) {
        this.prvRUC = prvRUC;
    }

    public String getPrvCor() {
        return prvCor;
    }

    public void setPrvCor(String prvCor) {
        this.prvCor = prvCor;
    }

    public String getPrvEstReg() {
        return prvEstReg;
    }

    public void setPrvEstReg(String prvEstReg) {
        this.prvEstReg = prvEstReg;
    }
}
