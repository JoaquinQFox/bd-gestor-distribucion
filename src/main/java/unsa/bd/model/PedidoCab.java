package unsa.bd.model;

import java.math.BigDecimal;

public class PedidoCab {
    private String pedCabNum;
    private String pedCabCliCod;
    private String pedCabRepCod;
    private int pedCabDia;
    private int pedCabMes;
    private int pedCabYea;
    private String pedCabEstCod;
    private BigDecimal pedCabTot;
    private String pedCabCiuCod;
    private String pedCabRegCod;
    private String pedCabEstReg;

    public PedidoCab() {}

    public PedidoCab(String pedCabNum, String pedCabCliCod, String pedCabRepCod, int pedCabDia, int pedCabMes, int pedCabYea, String pedCabEstCod, BigDecimal pedCabTot, String pedCabCiuCod, String pedCabRegCod, String pedCabEstReg) {
        this.pedCabNum = pedCabNum;
        this.pedCabCliCod = pedCabCliCod;
        this.pedCabRepCod = pedCabRepCod;
        this.pedCabDia = pedCabDia;
        this.pedCabMes = pedCabMes;
        this.pedCabYea = pedCabYea;
        this.pedCabEstCod = pedCabEstCod;
        this.pedCabTot = pedCabTot;
        this.pedCabCiuCod = pedCabCiuCod;
        this.pedCabRegCod = pedCabRegCod;
        this.pedCabEstReg = pedCabEstReg;
    }

    public String getPedCabNum() {
        return pedCabNum;
    }

    public void setPedCabNum(String pedCabNum) {
        this.pedCabNum = pedCabNum;
    }

    public String getPedCabCliCod() {
        return pedCabCliCod;
    }

    public void setPedCabCliCod(String pedCabCliCod) {
        this.pedCabCliCod = pedCabCliCod;
    }

    public String getPedCabRepCod() {
        return pedCabRepCod;
    }

    public void setPedCabRepCod(String pedCabRepCod) {
        this.pedCabRepCod = pedCabRepCod;
    }

    public int getPedCabDia() {
        return pedCabDia;
    }

    public void setPedCabDia(int pedCabDia) {
        this.pedCabDia = pedCabDia;
    }

    public int getPedCabMes() {
        return pedCabMes;
    }

    public void setPedCabMes(int pedCabMes) {
        this.pedCabMes = pedCabMes;
    }

    public int getPedCabYea() {
        return pedCabYea;
    }

    public void setPedCabYea(int pedCabYea) {
        this.pedCabYea = pedCabYea;
    }

    public String getPedCabEstCod() {
        return pedCabEstCod;
    }

    public void setPedCabEstCod(String pedCabEstCod) {
        this.pedCabEstCod = pedCabEstCod;
    }

    public BigDecimal getPedCabTot() {
        return pedCabTot;
    }

    public void setPedCabTot(BigDecimal pedCabTot) {
        this.pedCabTot = pedCabTot;
    }

    public String getPedCabCiuCod() {
        return pedCabCiuCod;
    }

    public void setPedCabCiuCod(String pedCabCiuCod) {
        this.pedCabCiuCod = pedCabCiuCod;
    }

    public String getPedCabRegCod() {
        return pedCabRegCod;
    }

    public void setPedCabRegCod(String pedCabRegCod) {
        this.pedCabRegCod = pedCabRegCod;
    }

    public String getPedCabEstReg() {
        return pedCabEstReg;
    }

    public void setPedCabEstReg(String pedCabEstReg) {
        this.pedCabEstReg = pedCabEstReg;
    }
}
