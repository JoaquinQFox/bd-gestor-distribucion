package unsa.bd.modules.seguridad.rolusuario;

public class RolUsuario {
    private String rolUsuCod;
    private String rolUsuNom;
    private String rolUsuEstReg;

    public RolUsuario() {}

    public RolUsuario(String rolUsuCod, String rolUsuNom, String rolUsuEstReg) {
        this.rolUsuCod = rolUsuCod;
        this.rolUsuNom = rolUsuNom;
        this.rolUsuEstReg = rolUsuEstReg;
    }

    public String getRolUsuCod() {
        return rolUsuCod;
    }

    public void setRolUsuCod(String rolUsuCod) {
        this.rolUsuCod = rolUsuCod;
    }

    public String getRolUsuNom() {
        return rolUsuNom;
    }

    public void setRolUsuNom(String rolUsuNom) {
        this.rolUsuNom = rolUsuNom;
    }

    public String getRolUsuEstReg() {
        return rolUsuEstReg;
    }

    public void setRolUsuEstReg(String rolUsuEstReg) {
        this.rolUsuEstReg = rolUsuEstReg;
    }
}
