package unsa.bd.views.forms;

import unsa.bd.dao.CiudadDAO;
import unsa.bd.dao.OficinaDAO;
import unsa.bd.dao.RegionDAO;
import unsa.bd.model.Ciudad;
import unsa.bd.model.Oficina;
import unsa.bd.model.Region;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

public class OficinaForm extends BaseForm {

    private JTextField          codField;
    private JComboBox<Region>   regComboBox;
    private JComboBox<Ciudad>   ciuComboBox;
    private JTextField          objProField;
    private JTextField          venReaField;
    private JTextField          estRegField;

    private static final int COL_EST_REG = 5;

    public OficinaForm() {
        super("EG006 - OFICINA");
    }

    @Override
    protected String getHeaderTitle() {
        return "OFICINA";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Región", "Ciudad", "Obj. Proyectado", "Venta Real", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Región
        regComboBox = makeStyledComboBox(Region::getRegNom);
        loadRegionesInComboBox(regComboBox);
        addFieldRowToForm(form, "Región:", regComboBox);

        // Ciudad (filtrada por región)
        ciuComboBox = makeStyledComboBox(Ciudad::getCiuNom);
        addFieldRowToForm(form, "Ciudad:", ciuComboBox);

        // Cargar todas las ciudades y poblar el combo según región inicial
        actualizarCiudadesPorRegion();

        // Listener: al cambiar región, actualizar ciudades
        regComboBox.addActionListener(e -> actualizarCiudadesPorRegion());

        // Objetivo Proyectado
        objProField = makeStyledField(15);
        addFieldRowToForm(form, "Obj. Proyectado:", objProField);

        // Venta Realizada
        venReaField = makeStyledField(15);
        addFieldRowToForm(form, "Venta Realizada:", venReaField);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        OficinaDAO dao    = new OficinaDAO();
        RegionDAO  regDao = new RegionDAO();
        CiudadDAO  ciuDao = new CiudadDAO();
        try {
            List<Oficina> oficinas  = dao.listarTodo();
            List<Region>  regiones  = regDao.listarTodo();
            List<Ciudad>  ciudades  = ciuDao.listarTodo();

            for (Oficina o : oficinas) {
                String nomReg = "";
                for (Region r : regiones) {
                    if (r.getRegCod().equals(o.getOfiRegCod())) { nomReg = r.getRegNom(); break; }
                }
                String nomCiu = "";
                for (Ciudad c : ciudades) {
                    if (c.getCiuCod().equals(o.getOfiCiuCod())) { nomCiu = c.getCiuNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        o.getOfiCod(),
                        nomReg,
                        nomCiu,
                        o.getOfiObjPro(),
                        o.getOfiVenRea(),
                        o.getOfiEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Oficina o = buildFromFields();
        if (o.getOfiCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!o.getOfiCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new OficinaDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new OficinaDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new OficinaDAO().eliminar(buildFromFields().getOfiCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new OficinaDAO().inactivar(buildFromFields().getOfiCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new OficinaDAO().reactivar(buildFromFields().getOfiCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        objProField.setText(tableModel.getValueAt(row, 3).toString());
        venReaField.setText(tableModel.getValueAt(row, 4).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        // Seleccionar región
        String nomReg = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < regComboBox.getItemCount(); i++) {
            if (regComboBox.getItemAt(i).getRegNom().equals(nomReg)) {
                regComboBox.setSelectedIndex(i);
                break;
            }
        }

        // Actualizar ciudades filtradas y luego seleccionar la correcta
        actualizarCiudadesPorRegion();
        String nomCiu = tableModel.getValueAt(row, 2).toString();
        for (int i = 0; i < ciuComboBox.getItemCount(); i++) {
            if (ciuComboBox.getItemAt(i).getCiuNom().equals(nomCiu)) {
                ciuComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        objProField.setText("");
        venReaField.setText("");
        estRegField.setText("");
        if (regComboBox.getItemCount() > 0) regComboBox.setSelectedIndex(0);
        actualizarCiudadesPorRegion();
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setComboEditable(regComboBox, editable);
        setComboEditable(ciuComboBox, editable);
        setFieldEditable(objProField, editable);
        setFieldEditable(venReaField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Oficina buildFromFields() {
        String     cod      = codField.getText().trim();
        String     objStr   = objProField.getText().trim();
        String     venStr   = venReaField.getText().trim();
        String     estReg   = estRegField.getText().trim();
        BigDecimal objPro   = objStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(objStr);
        BigDecimal venRea   = venStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(venStr);

        Region rSel  = (Region) regComboBox.getSelectedItem();
        Ciudad cSel  = (Ciudad) ciuComboBox.getSelectedItem();
        String regCod = rSel != null ? rSel.getRegCod() : "";
        String ciuCod = cSel != null ? cSel.getCiuCod() : "";

        return new Oficina(cod, regCod, ciuCod, objPro, venRea, estReg);
    }

    private void loadRegionesInComboBox(JComboBox<Region> box) {
        try {
            for (Region r : new RegionDAO().listarTodo()) box.addItem(r);
        } catch (Exception e) {
            System.err.println("Error al cargar regiones en OficinaForm");
        }
    }

    // Filtra el ciuComboBox según la región seleccionada
    private void actualizarCiudadesPorRegion() {
        try {
            System.out.println("Metodo utilizado");
            Region regSel = (Region) regComboBox.getSelectedItem();

            CiudadDAO ciudadDAO = new CiudadDAO();
            List<Ciudad> ciudades = ciudadDAO.listarTodo();

            ciuComboBox.removeAllItems();
            for (Ciudad c : ciudades) {
                if (c.getCiuRegCod().equals(regSel.getRegCod()))
                    ciuComboBox.addItem(c);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar las ciudades de la región");
        }
    }
}