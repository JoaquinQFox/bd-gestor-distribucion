package unsa.bd.modules.ventas.factura.facturadet;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FacturaDetDAO {

    public void agregar(FacturaDet facturaDet) throws Exception {
        String sql = """
                INSERT INTO "FACTURA_DET" ("FacDetCabNum", "FacDetProCod", "FacDetCan", "FacDetPre", "FacDetTot", "FacDetEstReg")
                VALUES (?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, facturaDet.getFacDetCabNum());
            ps.setString(2, facturaDet.getFacDetProCod());
            ps.setInt(3, facturaDet.getFacDetCan());
            ps.setBigDecimal(4, facturaDet.getFacDetPre());
            ps.setBigDecimal(5, facturaDet.getFacDetTot());
            ps.executeUpdate();
        }
    }

    public void modificar(FacturaDet facturaDet) throws Exception {
        String sql = """
                UPDATE "FACTURA_DET" SET "FacDetCan" = ?, "FacDetPre" = ?, "FacDetTot" = ?
                WHERE "FacDetCabNum" = ? AND "FacDetProCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, facturaDet.getFacDetCan());
            ps.setBigDecimal(2, facturaDet.getFacDetPre());
            ps.setBigDecimal(3, facturaDet.getFacDetTot());
            ps.setString(4, facturaDet.getFacDetCabNum());
            ps.setString(5, facturaDet.getFacDetProCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String facDetCabNum, String facDetProCod) throws Exception {
        String sql = """
                UPDATE "FACTURA_DET" SET "FacDetEstReg" = '*' WHERE "FacDetCabNum" = ? AND "FacDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, facDetCabNum);
            ps.setString(2, facDetProCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String facDetCabNum, String facDetProCod) throws Exception {
        String sql = """
                UPDATE "FACTURA_DET" SET "FacDetEstReg" = 'I' WHERE "FacDetCabNum" = ? AND "FacDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, facDetCabNum);
            ps.setString(2, facDetProCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String facDetCabNum, String facDetProCod) throws Exception {
        String sql = """
                UPDATE "FACTURA_DET" SET "FacDetEstReg" = 'A' WHERE "FacDetCabNum" = ? AND "FacDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, facDetCabNum);
            ps.setString(2, facDetProCod);
            ps.executeUpdate();
        }
    }

    public List<FacturaDet> listarTodo() throws Exception {
        List<FacturaDet> lista = new ArrayList<>();
        String sql = """
                SELECT "FacDetCabNum", "FacDetProCod", "FacDetCan", "FacDetPre", "FacDetTot", "FacDetEstReg"
                FROM "FACTURA_DET" ORDER BY "FacDetCabNum" ASC, "FacDetProCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FacturaDet f = new FacturaDet();
                f.setFacDetCabNum(rs.getString("FacDetCabNum"));
                f.setFacDetProCod(rs.getString("FacDetProCod"));
                f.setFacDetCan(rs.getInt("FacDetCan"));
                f.setFacDetPre(rs.getBigDecimal("FacDetPre"));
                f.setFacDetTot(rs.getBigDecimal("FacDetTot"));
                f.setFacDetEstReg(rs.getString("FacDetEstReg"));
                lista.add(f);
            }
        }
        return lista;
    }
}
