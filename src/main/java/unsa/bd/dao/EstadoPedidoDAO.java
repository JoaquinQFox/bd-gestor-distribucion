package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.EstadoPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoPedidoDAO {

    public void agregar(EstadoPedido estPed) throws Exception {
        String sql = """
                INSERT INTO "ESTADO_PEDIDO" ("EstPedNom", "EstPedEstReg") VALUES (?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estPed.getEstPedNom());
            ps.executeUpdate();
        }
    }

    public void modificar(EstadoPedido estPed) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedNom" = ? WHERE "EstPedCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estPed.getEstPedNom());
            ps.setInt(2, estPed.getEstPedCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(int estPedCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedEstReg" = '*' WHERE "EstPedCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estPedCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int estPedCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedEstReg" = 'I' WHERE "EstPedCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estPedCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int estPedCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_PEDIDO" SET "EstPedEstReg" = 'A' WHERE "EstPedCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estPedCod);
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
                e.setEstPedCod(rs.getInt("EstPedCod"));
                e.setEstPedNom(rs.getString("EstPedNom"));
                e.setEstPedEstReg(rs.getString("EstPedEstReg"));
                lista.add(e);
            }
        }
        return lista;
    }
}
