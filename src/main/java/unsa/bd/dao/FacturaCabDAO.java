package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.FacturaCab;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FacturaCabDAO {

    public void agregar(FacturaCab facturaCab) throws Exception {
        String sql = """
                INSERT INTO "FACTURA_CAB" (
                    "FacCabNum", "FacCabCliCod", "FacCabRepCod", "FacCabPedNum", "FacCabTipCod", 
                    "FacCabDia", "FacCabMes", "FacCabYea", "FacCabEstCod", "FacCabSubTot", 
                    "FacCabIGV", "FacCabImp", "FacCabDiaVen", "FacCabMesVen", "FacCabYeaVen", "FacCabEstReg"
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, facturaCab.getFacCabNum());
            ps.setString(2, facturaCab.getFacCabCliCod());
            ps.setString(3, facturaCab.getFacCabRepCod());
            ps.setString(4, facturaCab.getFacCabPedNum());
            ps.setString(5, facturaCab.getFacCabTipCod());
            ps.setInt(6, facturaCab.getFacCabDia());
            ps.setInt(7, facturaCab.getFacCabMes());
            ps.setInt(8, facturaCab.getFacCabYea());
            ps.setString(9, facturaCab.getFacCabEstCod());
            ps.setBigDecimal(10, facturaCab.getFacCabSubTot());
            ps.setBigDecimal(11, facturaCab.getFacCabIGV());
            ps.setBigDecimal(12, facturaCab.getFacCabImp());
            ps.setInt(13, facturaCab.getFacCabDiaVen());
            ps.setInt(14, facturaCab.getFacCabMesVen());
            ps.setInt(15, facturaCab.getFacCabYeaVen());
            ps.executeUpdate();
        }
    }

    public void modificar(FacturaCab facturaCab) throws Exception {
        String sql = """
                UPDATE "FACTURA_CAB" SET 
                    "FacCabCliCod" = ?, "FacCabRepCod" = ?, "FacCabPedNum" = ?, "FacCabTipCod" = ?, 
                    "FacCabDia" = ?, "FacCabMes" = ?, "FacCabYea" = ?, "FacCabEstCod" = ?, 
                    "FacCabSubTot" = ?, "FacCabIGV" = ?, "FacCabImp" = ?, "FacCabDiaVen" = ?, 
                    "FacCabMesVen" = ?, "FacCabYeaVen" = ?
                WHERE "FacCabNum" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, facturaCab.getFacCabCliCod());
            ps.setString(2, facturaCab.getFacCabRepCod());
            ps.setString(3, facturaCab.getFacCabPedNum());
            ps.setString(4, facturaCab.getFacCabTipCod());
            ps.setInt(5, facturaCab.getFacCabDia());
            ps.setInt(6, facturaCab.getFacCabMes());
            ps.setInt(7, facturaCab.getFacCabYea());
            ps.setString(8, facturaCab.getFacCabEstCod());
            ps.setBigDecimal(9, facturaCab.getFacCabSubTot());
            ps.setBigDecimal(10, facturaCab.getFacCabIGV());
            ps.setBigDecimal(11, facturaCab.getFacCabImp());
            ps.setInt(12, facturaCab.getFacCabDiaVen());
            ps.setInt(13, facturaCab.getFacCabMesVen());
            ps.setInt(14, facturaCab.getFacCabYeaVen());
            ps.setString(15, facturaCab.getFacCabNum());
            ps.executeUpdate();
        }
    }

    public void eliminar(String facCabNum) throws Exception {
        String sql = """
                UPDATE "FACTURA_CAB" SET "FacCabEstReg" = '*' WHERE "FacCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, facCabNum);
            ps.executeUpdate();
        }
    }

    public void inactivar(String facCabNum) throws Exception {
        String sql = """
                UPDATE "FACTURA_CAB" SET "FacCabEstReg" = 'I' WHERE "FacCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, facCabNum);
            ps.executeUpdate();
        }
    }

    public void reactivar(String facCabNum) throws Exception {
        String sql = """
                UPDATE "FACTURA_CAB" SET "FacCabEstReg" = 'A' WHERE "FacCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, facCabNum);
            ps.executeUpdate();
        }
    }

    public List<FacturaCab> listarTodo() throws Exception {
        List<FacturaCab> lista = new ArrayList<>();
        String sql = """
                SELECT "FacCabNum", "FacCabCliCod", "FacCabRepCod", "FacCabPedNum", "FacCabTipCod", 
                       "FacCabDia", "FacCabMes", "FacCabYea", "FacCabEstCod", "FacCabSubTot", 
                       "FacCabIGV", "FacCabImp", "FacCabDiaVen", "FacCabMesVen", "FacCabYeaVen", "FacCabEstReg"
                FROM "FACTURA_CAB" ORDER BY "FacCabNum" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FacturaCab f = new FacturaCab();
                f.setFacCabNum(rs.getString("FacCabNum"));
                f.setFacCabCliCod(rs.getString("FacCabCliCod"));
                f.setFacCabRepCod(rs.getString("FacCabRepCod"));
                f.setFacCabPedNum(rs.getString("FacCabPedNum"));
                f.setFacCabTipCod(rs.getString("FacCabTipCod"));
                f.setFacCabDia(rs.getInt("FacCabDia"));
                f.setFacCabMes(rs.getInt("FacCabMes"));
                f.setFacCabYea(rs.getInt("FacCabYea"));
                f.setFacCabEstCod(rs.getString("FacCabEstCod"));
                f.setFacCabSubTot(rs.getBigDecimal("FacCabSubTot"));
                f.setFacCabIGV(rs.getBigDecimal("FacCabIGV"));
                f.setFacCabImp(rs.getBigDecimal("FacCabImp"));
                f.setFacCabDiaVen(rs.getInt("FacCabDiaVen"));
                f.setFacCabMesVen(rs.getInt("FacCabMesVen"));
                f.setFacCabYeaVen(rs.getInt("FacCabYeaVen"));
                f.setFacCabEstReg(rs.getString("FacCabEstReg"));
                lista.add(f);
            }
        }
        return lista;
    }
}
