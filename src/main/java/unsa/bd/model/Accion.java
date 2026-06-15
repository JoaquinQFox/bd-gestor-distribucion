package unsa.bd.model;

public class Accion {
    private int accCod;
    private String accNom;
    private String accEstReg;

    // Constructor vacío
    public Accion() {
    }

    // Constructor completo
    public Accion(int accCod, String accNom, String accEstReg) {
        this.accCod = accCod;
        this.accNom = accNom;
        this.accEstReg = accEstReg;
    }

    // Getters y Setters
    public int getAccCod() {
        return accCod;
    }

    public void setAccCod(int accCod) {
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