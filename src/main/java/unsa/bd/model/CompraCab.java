package unsa.bd.model;

import java.math.BigDecimal;

public class CompraCab {
    private String comCabNum;
    private String comCabPrvCod;
    private String comCabAlmCod;
    private int comCabDia;
    private int comCabMes;
    private int comCabYea;
    private BigDecimal comCabImp;
    private String comCabEstReg;

    public CompraCab() {}

    public CompraCab(String comCabNum, String comCabPrvCod, String comCabAlmCod, int comCabDia, int comCabMes, int comCabYea, BigDecimal comCabImp, String comCabEstReg) {
        this.comCabNum = comCabNum;
        this.comCabPrvCod = comCabPrvCod;
        this.comCabAlmCod = comCabAlmCod;
        this.comCabDia = comCabDia;
        this.comCabMes = comCabMes;
        this.comCabYea = comCabYea;
        this.comCabImp = comCabImp;
        this.comCabEstReg = comCabEstReg;
    }

    public String getComCabNum() {
        return comCabNum;
    }

    public void setComCabNum(String comCabNum) {
        this.comCabNum = comCabNum;
    }

    public String getComCabPrvCod() {
        return comCabPrvCod;
    }

    public void setComCabPrvCod(String comCabPrvCod) {
        this.comCabPrvCod = comCabPrvCod;
    }

    public String getComCabAlmCod() {
        return comCabAlmCod;
    }

    public void setComCabAlmCod(String comCabAlmCod) {
        this.comCabAlmCod = comCabAlmCod;
    }

    public int getComCabDia() {
        return comCabDia;
    }

    public void setComCabDia(int comCabDia) {
        this.comCabDia = comCabDia;
    }

    public int getComCabMes() {
        return comCabMes;
    }

    public void setComCabMes(int comCabMes) {
        this.comCabMes = comCabMes;
    }

    public int getComCabYea() {
        return comCabYea;
    }

    public void setComCabYea(int comCabYea) {
        this.comCabYea = comCabYea;
    }

    public BigDecimal getComCabImp() {
        return comCabImp;
    }

    public void setComCabImp(BigDecimal comCabImp) {
        this.comCabImp = comCabImp;
    }

    public String getComCabEstReg() {
        return comCabEstReg;
    }

    public void setComCabEstReg(String comCabEstReg) {
        this.comCabEstReg = comCabEstReg;
    }
}
