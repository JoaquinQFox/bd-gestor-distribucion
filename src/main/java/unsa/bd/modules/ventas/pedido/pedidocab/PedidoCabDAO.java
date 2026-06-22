package unsa.bd.modules.ventas.pedido.pedidocab;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoCabDAO {

    public void agregar(PedidoCab pedidoCab) throws Exception {
        String sql = """
                INSERT INTO "PEDIDO_CAB" ("PedCabNum", "PedCabCliCod", "PedCabRepCod", "PedCabDia", "PedCabMes", "PedCabYea", "PedCabEstCod", "PedCabTot", "PedCabCiuCod", "PedCabRegCod", "PedCabEstReg")
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pedidoCab.getPedCabNum());
            ps.setString(2, pedidoCab.getPedCabCliCod());
            ps.setString(3, pedidoCab.getPedCabRepCod());
            ps.setInt(4, pedidoCab.getPedCabDia());
            ps.setInt(5, pedidoCab.getPedCabMes());
            ps.setInt(6, pedidoCab.getPedCabYea());
            ps.setString(7, pedidoCab.getPedCabEstCod());
            ps.setBigDecimal(8, pedidoCab.getPedCabTot());
            ps.setString(9, pedidoCab.getPedCabCiuCod());
            ps.setString(10, pedidoCab.getPedCabRegCod());
            ps.executeUpdate();
        }
    }

    public void modificar(PedidoCab pedidoCab) throws Exception {
        String sql = """
                UPDATE "PEDIDO_CAB" SET "PedCabCliCod" = ?, "PedCabRepCod" = ?, "PedCabDia" = ?, "PedCabMes" = ?, "PedCabYea" = ?, "PedCabEstCod" = ?, "PedCabTot" = ?, "PedCabCiuCod" = ?, "PedCabRegCod" = ?
                WHERE "PedCabNum" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pedidoCab.getPedCabCliCod());
            ps.setString(2, pedidoCab.getPedCabRepCod());
            ps.setInt(3, pedidoCab.getPedCabDia());
            ps.setInt(4, pedidoCab.getPedCabMes());
            ps.setInt(5, pedidoCab.getPedCabYea());
            ps.setString(6, pedidoCab.getPedCabEstCod());
            ps.setBigDecimal(7, pedidoCab.getPedCabTot());
            ps.setString(8, pedidoCab.getPedCabCiuCod());
            ps.setString(9, pedidoCab.getPedCabRegCod());
            ps.setString(10, pedidoCab.getPedCabNum());
            ps.executeUpdate();
        }
    }

    public void eliminar(String pedCabNum) throws Exception {
        String sql = """
                UPDATE "PEDIDO_CAB" SET "PedCabEstReg" = '*' WHERE "PedCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pedCabNum);
            ps.executeUpdate();
        }
    }

    public void inactivar(String pedCabNum) throws Exception {
        String sql = """
                UPDATE "PEDIDO_CAB" SET "PedCabEstReg" = 'I' WHERE "PedCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pedCabNum);
            ps.executeUpdate();
        }
    }

    public void reactivar(String pedCabNum) throws Exception {
        String sql = """
                UPDATE "PEDIDO_CAB" SET "PedCabEstReg" = 'A' WHERE "PedCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pedCabNum);
            ps.executeUpdate();
        }
    }

    public List<PedidoCab> listarTodo() throws Exception {
        List<PedidoCab> lista = new ArrayList<>();
        String sql = """
                SELECT "PedCabNum", "PedCabCliCod", "PedCabRepCod", "PedCabDia", "PedCabMes", "PedCabYea", "PedCabEstCod", "PedCabTot", "PedCabCiuCod", "PedCabRegCod", "PedCabEstReg"
                FROM "PEDIDO_CAB" ORDER BY "PedCabNum" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PedidoCab p = new PedidoCab();
                p.setPedCabNum(rs.getString("PedCabNum"));
                p.setPedCabCliCod(rs.getString("PedCabCliCod"));
                p.setPedCabRepCod(rs.getString("PedCabRepCod"));
                p.setPedCabDia(rs.getInt("PedCabDia"));
                p.setPedCabMes(rs.getInt("PedCabMes"));
                p.setPedCabYea(rs.getInt("PedCabYea"));
                p.setPedCabEstCod(rs.getString("PedCabEstCod"));
                p.setPedCabTot(rs.getBigDecimal("PedCabTot"));
                p.setPedCabCiuCod(rs.getString("PedCabCiuCod"));
                p.setPedCabRegCod(rs.getString("PedCabRegCod"));
                p.setPedCabEstReg(rs.getString("PedCabEstReg"));
                lista.add(p);
            }
        }
        return lista;
    }
}