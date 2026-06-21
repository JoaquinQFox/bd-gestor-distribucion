package unsa.bd.model;

public class Usuario {
    private String usuCod;
    private String usuRepCod;
    private String usuNomUsu;
    private String usuCon;
    private String usuRolUsu;
    private String usuEstReg;

    public Usuario() {}

    public Usuario(String usuCod, String usuRepCod, String usuNomUsu, String usuCon, String usuRolUsu, String usuEstReg) {
        this.usuCod = usuCod;
        this.usuRepCod = usuRepCod;
        this.usuNomUsu = usuNomUsu;
        this.usuCon = usuCon;
        this.usuRolUsu = usuRolUsu;
        this.usuEstReg = usuEstReg;
    }

    public String getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(String usuCod) {
        this.usuCod = usuCod;
    }

    public String getUsuRepCod() {
        return usuRepCod;
    }

    public void setUsuRepCod(String usuRepCod) {
        this.usuRepCod = usuRepCod;
    }

    public String getUsuNomUsu() {
        return usuNomUsu;
    }

    public void setUsuNomUsu(String usuNomUsu) {
        this.usuNomUsu = usuNomUsu;
    }

    public String getUsuCon() {
        return usuCon;
    }

    public void setUsuCon(String usuCon) {
        this.usuCon = usuCon;
    }

    public String getUsuRolUsu() {
        return usuRolUsu;
    }

    public void setUsuRolUsu(String usuRolUsu) {
        this.usuRolUsu = usuRolUsu;
    }

    public String getUsuEstReg() {
        return usuEstReg;
    }

    public void setUsuEstReg(String usuEstReg) {
        this.usuEstReg = usuEstReg;
    }
}
