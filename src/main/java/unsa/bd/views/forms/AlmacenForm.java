package unsa.bd.views.forms;

import unsa.bd.dao.AlmacenDAO;
import unsa.bd.dao.CiudadDAO;
import unsa.bd.dao.RegionDAO;
import unsa.bd.model.Almacen;
import unsa.bd.model.Ciudad;
import unsa.bd.model.Region;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class AlmacenForm extends BaseForm {

    private JTextField        codField;
    private JTextField        nomField;
    private JTextField        dirField;
    private JComboBox<Region> regComboBox;
    private JComboBox<Ciudad> ciuComboBox;
    private JTextField        estRegField;

    private static final int COL_EST_REG = 5;

    public AlmacenForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "ALMACEN";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Dirección", "Región", "Ciudad", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        nomField = makeStyledField(30);
        addFieldRowToForm(form, "Nombre:", nomField);

        dirField = makeStyledField(50);
        addFieldRowToForm(form, "Dirección:", dirField);

        regComboBox = makeStyledComboBox(Region::getRegNom);
        loadRegionesInComboBox(regComboBox);
        addFieldRowToForm(form, "Región:", regComboBox);

        ciuComboBox = makeStyledComboBox(Ciudad::getCiuNom);
        addFieldRowToForm(form, "Ciudad:", ciuComboBox);

        actualizarCiudadesPorRegion();
        regComboBox.addActionListener(e -> actualizarCiudadesPorRegion());

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        AlmacenDAO dao    = new AlmacenDAO();
        RegionDAO  regDao = new RegionDAO();
        CiudadDAO  ciuDao = new CiudadDAO();
        try {
            List<Almacen> almacenes = dao.listarTodo();
            List<Region>  regiones  = regDao.listarTodo();
            List<Ciudad>  ciudades  = ciuDao.listarTodo();

            for (Almacen a : almacenes) {
                String nomReg = "";
                for (Region r : regiones) {
                    if (r.getRegCod().equals(a.getAlmRegCod())) { nomReg = r.getRegNom(); break; }
                }
                String nomCiu = "";
                for (Ciudad c : ciudades) {
                    if (c.getCiuCod().equals(a.getAlmCiuCod())) { nomCiu = c.getCiuNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        a.getAlmCod(),
                        a.getAlmNom(),
                        a.getAlmDir(),
                        nomReg,
                        nomCiu,
                        a.getAlmEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Almacen a = buildFromFields();
        if (a.getAlmCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!a.getAlmCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new AlmacenDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new AlmacenDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new AlmacenDAO().eliminar(buildFromFields().getAlmCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new AlmacenDAO().inactivar(buildFromFields().getAlmCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new AlmacenDAO().reactivar(buildFromFields().getAlmCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        dirField.setText(tableModel.getValueAt(row, 2).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomReg = tableModel.getValueAt(row, 3).toString();
        for (int i = 0; i < regComboBox.getItemCount(); i++) {
            if (regComboBox.getItemAt(i).getRegNom().equals(nomReg)) {
                regComboBox.setSelectedIndex(i);
                break;
            }
        }

        actualizarCiudadesPorRegion();
        String nomCiu = tableModel.getValueAt(row, 4).toString();
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
        nomField.setText("");
        dirField.setText("");
        estRegField.setText("");
        if (regComboBox.getItemCount() > 0) regComboBox.setSelectedIndex(0);
        actualizarCiudadesPorRegion();
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(dirField, editable);
        setComboEditable(regComboBox, editable);
        setComboEditable(ciuComboBox, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Almacen buildFromFields() {
        String cod    = codField.getText().trim();
        String nom    = nomField.getText().trim();
        String dir    = dirField.getText().trim();
        String estReg = estRegField.getText().trim();

        Region rSel  = (Region) regComboBox.getSelectedItem();
        Ciudad cSel  = (Ciudad) ciuComboBox.getSelectedItem();
        String regCod = rSel != null ? rSel.getRegCod() : "";
        String ciuCod = cSel != null ? cSel.getCiuCod() : "";

        return new Almacen(cod, nom, dir, ciuCod, regCod, estReg);
    }

    private void loadRegionesInComboBox(JComboBox<Region> box) {
        try {
            for (Region r : new RegionDAO().listarTodo()) box.addItem(r);
        } catch (Exception e) {
            System.err.println("Error al cargar regiones en AlmacenForm");
        }
    }

    private void actualizarCiudadesPorRegion() {
        try {
            Region regSel = (Region) regComboBox.getSelectedItem();
            if (regSel == null) return;
            List<Ciudad> ciudades = new CiudadDAO().listarTodo();
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