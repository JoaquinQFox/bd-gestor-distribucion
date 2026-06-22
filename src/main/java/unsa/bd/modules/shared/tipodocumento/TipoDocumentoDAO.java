package unsa.bd.modules.shared.tipodocumento;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoDAO {

    public void agregar(TipoDocumento tipDoc) throws Exception {
        String sql = """
                INSERT INTO "TIPO_DOCUMENTO" ("TipDocCod", "TipDocNom", "TipDocEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tipDoc.getTipDocCod());
            ps.setString(2, tipDoc.getTipDocNom());
            ps.executeUpdate();
        }
    }

    public void modificar(TipoDocumento tipDoc) throws Exception {
        String sql = """
                UPDATE "TIPO_DOCUMENTO" SET "TipDocNom" = ? WHERE "TipDocCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tipDoc.getTipDocNom());
            ps.setString(2, tipDoc.getTipDocCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String tipDocCod) throws Exception {
        String sql = """
                UPDATE "TIPO_DOCUMENTO" SET "TipDocEstReg" = '*' WHERE "TipDocCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipDocCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String tipDocCod) throws Exception {
        String sql = """
                UPDATE "TIPO_DOCUMENTO" SET "TipDocEstReg" = 'I' WHERE "TipDocCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipDocCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String tipDocCod) throws Exception {
        String sql = """
                UPDATE "TIPO_DOCUMENTO" SET "TipDocEstReg" = 'A' WHERE "TipDocCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipDocCod);
            ps.executeUpdate();
        }
    }

    public List<TipoDocumento> listarTodo() throws Exception {
        List<TipoDocumento> lista = new ArrayList<>();
        String sql = """
                SELECT "TipDocCod", "TipDocNom", "TipDocEstReg" FROM "TIPO_DOCUMENTO" ORDER BY "TipDocCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TipoDocumento t = new TipoDocumento();
                t.setTipDocCod(rs.getString("TipDocCod"));
                t.setTipDocNom(rs.getString("TipDocNom"));
                t.setTipDocEstReg(rs.getString("TipDocEstReg"));
                lista.add(t);
            }
        }
        return lista;
    }
}