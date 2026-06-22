package unsa.bd.modules.ventas.pedido.pedidodet;

import java.math.BigDecimal;

public class PedidoDet {
    private String pedDetCabNum;
    private String pedDetProCod;
    private int pedDetCan;
    private BigDecimal pedDetPre;
    private BigDecimal pedDetTot;
    private String pedDetEstReg;

    public PedidoDet() {}

    public PedidoDet(String pedDetCabNum, String pedDetProCod, int pedDetCan, BigDecimal pedDetPre, BigDecimal pedDetTot, String pedDetEstReg) {
        this.pedDetCabNum = pedDetCabNum;
        this.pedDetProCod = pedDetProCod;
        this.pedDetCan = pedDetCan;
        this.pedDetPre = pedDetPre;
        this.pedDetTot = pedDetTot;
        this.pedDetEstReg = pedDetEstReg;
    }

    public String getPedDetCabNum() {
        return pedDetCabNum;
    }

    public void setPedDetCabNum(String pedDetCabNum) {
        this.pedDetCabNum = pedDetCabNum;
    }

    public String getPedDetProCod() {
        return pedDetProCod;
    }

    public void setPedDetProCod(String pedDetProCod) {
        this.pedDetProCod = pedDetProCod;
    }

    public int getPedDetCan() {
        return pedDetCan;
    }

    public void setPedDetCan(int pedDetCan) {
        this.pedDetCan = pedDetCan;
    }

    public BigDecimal getPedDetPre() {
        return pedDetPre;
    }

    public void setPedDetPre(BigDecimal pedDetPre) {
        this.pedDetPre = pedDetPre;
    }

    public BigDecimal getPedDetTot() {
        return pedDetTot;
    }

    public void setPedDetTot(BigDecimal pedDetTot) {
        this.pedDetTot = pedDetTot;
    }

    public String getPedDetEstReg() {
        return pedDetEstReg;
    }

    public void setPedDetEstReg(String pedDetEstReg) {
        this.pedDetEstReg = pedDetEstReg;
    }
}
