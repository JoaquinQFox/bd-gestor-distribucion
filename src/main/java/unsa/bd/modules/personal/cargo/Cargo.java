package unsa.bd.modules.personal.cargo;

public class Cargo {
    private String carCod;
    private String carNom;
    private String carEstReg;

    public Cargo() {
    }

    public Cargo(String carCod, String carNom, String carEstReg) {
        this.carCod = carCod;
        this.carNom = carNom;
        this.carEstReg = carEstReg;
    }

    public String getCarCod() {
        return carCod;
    }

    public void setCarCod(String carCod) {
        this.carCod = carCod;
    }

    public String getCarNom() {
        return carNom;
    }

    public void setCarNom(String carNom) {
        this.carNom = carNom;
    }

    public String getCarEstReg() {
        return carEstReg;
    }

    public void setCarEstReg(String carEstReg) {
        this.carEstReg = carEstReg;
    }

    @Override
    public String toString() {
        return "Cargo{" +
               "carCod=" + carCod +
               ", carNom='" + carNom + '\'' +
               ", carEstReg='" + carEstReg + '\'' +
               '}';
    }
}