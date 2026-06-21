package unsa.bd.model;

public class Stock {
    private String stoProCod;
    private String stoAlmCod;
    private int stoCanDis;
    private int stoCanMin;
    private String stoEstReg;

    public Stock() {}

    public Stock(String stoProCod, String stoAlmCod, int stoCanDis, int stoCanMin, String stoEstReg) {
        this.stoProCod = stoProCod;
        this.stoAlmCod = stoAlmCod;
        this.stoCanDis = stoCanDis;
        this.stoCanMin = stoCanMin;
        this.stoEstReg = stoEstReg;
    }

    public String getStoProCod() {
        return stoProCod;
    }

    public void setStoProCod(String stoProCod) {
        this.stoProCod = stoProCod;
    }

    public String getStoAlmCod() {
        return stoAlmCod;
    }

    public void setStoAlmCod(String stoAlmCod) {
        this.stoAlmCod = stoAlmCod;
    }

    public int getStoCanDis() {
        return stoCanDis;
    }

    public void setStoCanDis(int stoCanDis) {
        this.stoCanDis = stoCanDis;
    }

    public int getStoCanMin() {
        return stoCanMin;
    }

    public void setStoCanMin(int stoCanMin) {
        this.stoCanMin = stoCanMin;
    }

    public String getStoEstReg() {
        return stoEstReg;
    }

    public void setStoEstReg(String stoEstReg) {
        this.stoEstReg = stoEstReg;
    }
}
