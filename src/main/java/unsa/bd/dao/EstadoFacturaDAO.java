package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.EstadoFactura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoFacturaDAO {

    public void agregar(EstadoFactura estFac) throws Exception {
        String sql = """
                INSERT INTO "ESTADO_FACTURA" ("EstFacNom", "EstFacEstReg") VALUES (?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, estFac.getEstFacNom());
            ps.executeUpdate();
        }
    }

    public void modificar(EstadoFactura estFac) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacNom" = ? WHERE "EstFacCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, estFac.getEstFacNom());
            ps.setInt(2, estFac.getEstFacCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(int estFacCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacEstReg" = '*' WHERE "EstFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estFacCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int estFacCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacEstReg" = 'I' WHERE "EstFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estFacCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int estFacCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacEstReg" = 'A' WHERE "EstFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estFacCod);
            ps.executeUpdate();
        }
    }

    public List<EstadoFactura> listarTodo() throws Exception {
        List<EstadoFactura> lista = new ArrayList<>();
        String sql = """
                SELECT "EstFacCod", "EstFacNom", "EstFacEstReg" FROM "ESTADO_FACTURA" ORDER BY "EstFacCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EstadoFactura e = new EstadoFactura();
                e.setEstFacCod(rs.getInt("EstFacCod"));
                e.setEstFacNom(rs.getInt("EstFacNom"));
                e.setEstFacEstReg(rs.getString("EstFacEstReg"));
                lista.add(e);
            }
        }
        return lista;
    }
}
