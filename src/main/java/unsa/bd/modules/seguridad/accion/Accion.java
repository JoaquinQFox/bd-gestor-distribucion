package unsa.bd.modules.seguridad.accion;

public class Accion {
    private String accCod;
    private String accNom;
    private String accEstReg;

    // Constructor vacío
    public Accion() {
    }

    public Accion(String accCod, String accNom, String accEstReg) {
        this.accCod = accCod;
        this.accNom = accNom;
        this.accEstReg = accEstReg;
    }

    public String getAccCod() {
        return accCod;
    }

    public void setAccCod(String accCod) {
        this.accCod = accCod;
    }

    public String getAccNom() {
        return accNom;
    }

    public void setAccNom(String accNom) {
        this.accNom = accNom;
    }

    public String getAccEstReg() {
        return accEstReg;
    }

    public void setAccEstReg(String accEstReg) {
        this.accEstReg = accEstReg;
    }

    @Override
    public String toString() {
        return "Accion{" +
               "accCod=" + accCod +
               ", accNom='" + accNom + '\'' +
               ", accEstReg='" + accEstReg + '\'' +
               '}';
    }
}