package unsa.bd.modules.ventas.pedido.pedidodet;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoDetDAO {

    public void agregar(PedidoDet pedidoDet) throws Exception {
        String sql = """
                INSERT INTO "PEDIDO_DET" ("PedDetCabNum", "PedDetProCod", "PedDetPre", "PedDetCan", "PedDetTot", "PedDetEstReg")
                VALUES (?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pedidoDet.getPedDetCabNum());
            ps.setString(2, pedidoDet.getPedDetProCod());
            ps.setBigDecimal(3, pedidoDet.getPedDetPre());
            ps.setInt(4, pedidoDet.getPedDetCan());
            ps.setBigDecimal(5, pedidoDet.getPedDetTot());
            ps.executeUpdate();
        }
    }

    public void modificar(PedidoDet pedidoDet) throws Exception {
        String sql = """
                UPDATE "PEDIDO_DET" SET "PedDetCan" = ?, "PedDetTot" = ?, "PedDetPre" = ?
                WHERE "PedDetCabNum" = ? AND "PedDetProCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pedidoDet.getPedDetCan());
            ps.setBigDecimal(2, pedidoDet.getPedDetTot());
            ps.setBigDecimal(3, pedidoDet.getPedDetPre());
            ps.setString(4, pedidoDet.getPedDetCabNum());
            ps.setString(5, pedidoDet.getPedDetProCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String pedDetCabNum, String pedDetProCod) throws Exception {
        String sql = """
                UPDATE "PEDIDO_DET" SET "PedDetEstReg" = '*' WHERE "PedDetCabNum" = ? AND "PedDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pedDetCabNum);
            ps.setString(2, pedDetProCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String pedDetCabNum, String pedDetProCod) throws Exception {
        String sql = """
                UPDATE "PEDIDO_DET" SET "PedDetEstReg" = 'I' WHERE "PedDetCabNum" = ? AND "PedDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pedDetCabNum);
            ps.setString(2, pedDetProCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String pedDetCabNum, String pedDetProCod) throws Exception {
        String sql = """
                UPDATE "PEDIDO_DET" SET "PedDetEstReg" = 'A' WHERE "PedDetCabNum" = ? AND "PedDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pedDetCabNum);
            ps.setString(2, pedDetProCod);
            ps.executeUpdate();
        }
    }

    public List<PedidoDet> listarTodo() throws Exception {
        List<PedidoDet> lista = new ArrayList<>();
        String sql = """
                SELECT "PedDetCabNum", "PedDetProCod", "PedDetCan", "PedDetTot", "PedDetEstReg", "PedDetPre"
                FROM "PEDIDO_DET" ORDER BY "PedDetCabNum", "PedDetProCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PedidoDet d = new PedidoDet();
                d.setPedDetCabNum(rs.getString("PedDetCabNum"));
                d.setPedDetProCod(rs.getString("PedDetProCod"));
                d.setPedDetCan(rs.getInt("PedDetCan"));
                d.setPedDetTot(rs.getBigDecimal("PedDetTot"));
                d.setPedDetPre(rs.getBigDecimal("PedDetPre"));
                d.setPedDetEstReg(rs.getString("PedDetEstReg"));
                lista.add(d);
            }
        }
        return lista;
    }
}