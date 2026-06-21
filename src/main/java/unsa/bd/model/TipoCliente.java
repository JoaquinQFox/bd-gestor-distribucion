package unsa.bd.model;

public class TipoCliente {
    private String tipCliCod;
    private String tipCliNom;
    private String tipCliEstReg;

    public TipoCliente() {}

    public TipoCliente(String tipCliCod, String tipCliNom, String tipCliEstReg) {
        this.tipCliCod = tipCliCod;
        this.tipCliNom = tipCliNom;
        this.tipCliEstReg = tipCliEstReg;
    }

    public String getTipCliCod() {
        return tipCliCod;
    }

    public void setTipCliCod(String tipCliCod) {
        this.tipCliCod = tipCliCod;
    }

    public String getTipCliNom() {
        return tipCliNom;
    }

    public void setTipCliNom(String tipCliNom) {
        this.tipCliNom = tipCliNom;
    }

    public String getTipCliEstReg() {
        return tipCliEstReg;
    }

    public void setTipCliEstReg(String tipCliEstReg) {
        this.tipCliEstReg = tipCliEstReg;
    }
}
