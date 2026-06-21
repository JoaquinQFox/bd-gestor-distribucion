package unsa.bd.model;

import java.math.BigDecimal;

public class CompraDet {
    private String comDetCabNum;
    private String comDetProCod;
    private int comDetCan;
    private BigDecimal comDetPre;
    private BigDecimal comDetTot;
    private String comDetEstReg;

    public CompraDet() {}

    public CompraDet(String comDetCabNum, String comDetProCod, int comDetCan, BigDecimal comDetPre, BigDecimal comDetTot, String comDetEstReg) {
        this.comDetCabNum = comDetCabNum;
        this.comDetProCod = comDetProCod;
        this.comDetCan = comDetCan;
        this.comDetPre = comDetPre;
        this.comDetTot = comDetTot;
        this.comDetEstReg = comDetEstReg;
    }

    public String getComDetCabNum() {
        return comDetCabNum;
    }

    public void setComDetCabNum(String comDetCabNum) {
        this.comDetCabNum = comDetCabNum;
    }

    public String getComDetProCod() {
        return comDetProCod;
    }

    public void setComDetProCod(String comDetProCod) {
        this.comDetProCod = comDetProCod;
    }

    public int getComDetCan() {
        return comDetCan;
    }

    public void setComDetCan(int comDetCan) {
        this.comDetCan = comDetCan;
    }

    public BigDecimal getComDetPre() {
        return comDetPre;
    }

    public void setComDetPre(BigDecimal comDetPre) {
        this.comDetPre = comDetPre;
    }

    public BigDecimal getComDetTot() {
        return comDetTot;
    }

    public void setComDetTot(BigDecimal comDetTot) {
        this.comDetTot = comDetTot;
    }

    public String getComDetEstReg() {
        return comDetEstReg;
    }

    public void setComDetEstReg(String comDetEstReg) {
        this.comDetEstReg = comDetEstReg;
    }
}
