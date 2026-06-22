package unsa.bd.modules.seguridad.movusuario;

import java.time.LocalTime;

public class MovUsuario {
    private String movUsuCod;
    private String movUsuUsuCod;
    private LocalTime movUsuHor;
    private String movUsuDes;
    private int movUsuDia;
    private int movUsuMes;
    private int movUsuYea;
    private String movUsuAccCod;
    private String movUsuEstReg;

    public MovUsuario() {}

    public MovUsuario(String movUsuCod, String movUsuUsuCod, LocalTime movUsuHor, String movUsuDes, int movUsuDia, int movUsuMes, int movUsuYea, String movUsuAccCod, String movUsuEstReg) {
        this.movUsuCod = movUsuCod;
        this.movUsuUsuCod = movUsuUsuCod;
        this.movUsuHor = movUsuHor;
        this.movUsuDes = movUsuDes;
        this.movUsuDia = movUsuDia;
        this.movUsuMes = movUsuMes;
        this.movUsuYea = movUsuYea;
        this.movUsuAccCod = movUsuAccCod;
        this.movUsuEstReg = movUsuEstReg;
    }

    public String getMovUsuCod() {
        return movUsuCod;
    }

    public void setMovUsuCod(String movUsuCod) {
        this.movUsuCod = movUsuCod;
    }

    public String getMovUsuUsuCod() {
        return movUsuUsuCod;
    }

    public void setMovUsuUsuCod(String movUsuUsuCod) {
        this.movUsuUsuCod = movUsuUsuCod;
    }

    public LocalTime getMovUsuHor() {
        return movUsuHor;
    }

    public void setMovUsuHor(LocalTime movUsuHor) {
        this.movUsuHor = movUsuHor;
    }

    public String getMovUsuDes() {
        return movUsuDes;
    }

    public void setMovUsuDes(String movUsuDes) {
        this.movUsuDes = movUsuDes;
    }

    public int getMovUsuDia() {
        return movUsuDia;
    }

    public void setMovUsuDia(int movUsuDia) {
        this.movUsuDia = movUsuDia;
    }

    public int getMovUsuMes() {
        return movUsuMes;
    }

    public void setMovUsuMes(int movUsuMes) {
        this.movUsuMes = movUsuMes;
    }

    public int getMovUsuYea() {
        return movUsuYea;
    }

    public void setMovUsuYea(int movUsuYea) {
        this.movUsuYea = movUsuYea;
    }

    public String getMovUsuAccCod() {
        return movUsuAccCod;
    }

    public void setMovUsuAccCod(String movUsuAccCod) {
        this.movUsuAccCod = movUsuAccCod;
    }

    public String getMovUsuEstReg() {
        return movUsuEstReg;
    }

    public void setMovUsuEstReg(String movUsuEstReg) {
        this.movUsuEstReg = movUsuEstReg;
    }
}
