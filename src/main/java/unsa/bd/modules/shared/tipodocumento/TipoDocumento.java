package unsa.bd.modules.shared.tipodocumento;

public class TipoDocumento {
    private String tipDocCod;
    private String tipDocNom;
    private String tipDocEstReg;

    public TipoDocumento() {}

    public TipoDocumento(String tipDocCod, String tipDocNom, String tipDocEstReg) {
        this.tipDocCod = tipDocCod;
        this.tipDocNom = tipDocNom;
        this.tipDocEstReg = tipDocEstReg;
    }

    public String getTipDocCod() {
        return tipDocCod;
    }

    public void setTipDocCod(String tipDocCod) {
        this.tipDocCod = tipDocCod;
    }

    public String getTipDocNom() {
        return tipDocNom;
    }

    public void setTipDocNom(String tipDocNom) {
        this.tipDocNom = tipDocNom;
    }

    public String getTipDocEstReg() {
        return tipDocEstReg;
    }

    public void setTipDocEstReg(String tipDocEstReg) {
        this.tipDocEstReg = tipDocEstReg;
    }
}
