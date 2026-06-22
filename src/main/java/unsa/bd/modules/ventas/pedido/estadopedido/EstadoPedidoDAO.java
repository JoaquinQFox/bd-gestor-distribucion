package unsa.bd.modules.ventas.pedido.estadopedido;

import unsa.bd.config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoPedidoDAO {

    public void agregar(EstadoPedido estPed) throws Exception {
        String sql = """
                INSERT INTO "ESTADO_PEDIDO" ("EstPedCod", "EstPedNom", "EstPedEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estPed.getEstPedCod());
            ps.setString(2, estPed.getEstPedNom());
            ps.executeUpdate();
        }
    }

    public void modificar(EstadoPedido estPed) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedNom" = ? WHERE "EstPedCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estPed.getEstPedNom());
            ps.setString(2, estPed.getEstPedCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String estPedCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedEstReg" = '*' WHERE "EstPedCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estPedCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String estPedCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedEstReg" = 'I' WHERE "EstPedCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estPedCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String estPedCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedEstReg" = 'A' WHERE "EstPedCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estPedCod);
            ps.executeUpdate();
        }
    }

    public List<EstadoPedido> listarTodo() throws Exception {
        List<EstadoPedido> lista = new ArrayList<>();
        String sql = """
                SELECT "EstPedCod", "EstPedNom", "EstPedEstReg" FROM "ESTADO_PEDIDO" ORDER BY "EstPedCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EstadoPedido e = new EstadoPedido();
                e.setEstPedCod(rs.getString("EstPedCod"));
                e.setEstPedNom(rs.getString("EstPedNom"));
                e.setEstPedEstReg(rs.getString("EstPedEstReg"));
                lista.add(e);
            }
        }
        return lista;
    }

}
