package unsa.bd.views.forms;

import unsa.bd.dao.CiudadDAO;
import unsa.bd.dao.RegionDAO;
import unsa.bd.model.Ciudad;
import unsa.bd.model.Region;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CiudadForm extends BaseForm {

    private JTextField     codField;
    private JTextField     nomField;
    private JComboBox<Region> regComboBox;
    private JTextField     estRegField;

    private static final int COL_EST_REG = 3;

    public CiudadForm() {
        super("EG002-CIUDAD");
    }

    @Override
    protected String getHeaderTitle() {
        return "CIUDAD";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Región", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {

        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Nombre
        nomField = makeStyledField(20);
        addFieldRowToForm(form, "Nombre:", nomField);

        // Región
        regComboBox = makeStyledComboBox(Region::getRegNom);
        loadRegionesInComboBox(regComboBox);
        addFieldRowToForm(form, "Región:", regComboBox);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        CiudadDAO  dao    = new CiudadDAO();
        RegionDAO  regDao = new RegionDAO();
        try {
            List<Ciudad> ciudades = dao.listarTodo();
            List<Region> regiones = regDao.listarTodo();

            for (Ciudad ciudad : ciudades) {
                String nomRegion = "";
                for (Region r : regiones) {
                    if (r.getRegCod().equals(ciudad.getCiuRegCod())) {
                        nomRegion = r.getRegNom();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        ciudad.getCiuCod(),
                        ciudad.getCiuNom(),
                        nomRegion,
                        ciudad.getCiuEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        new CiudadDAO().agregar(buildCiudadFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new CiudadDAO().modificar(buildCiudadFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new CiudadDAO().eliminar(buildCiudadFromFields().getCiuCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new CiudadDAO().inactivar(buildCiudadFromFields().getCiuCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new CiudadDAO().reactivar(buildCiudadFromFields().getCiuCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomRegion = tableModel.getValueAt(row, 2).toString();
        for (int i = 0; i < regComboBox.getItemCount(); i++) {
            if (regComboBox.getItemAt(i).getRegNom().equals(nomRegion)) {
                regComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        nomField.setText("");
        estRegField.setText("");
        if (regComboBox.getItemCount() > 0) regComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(nomField, editable);
        setComboEditable(regComboBox, editable);
        // El código es editable solo al agregar, no al modificar/eliminar/etc.
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        // El estado de registro siempre es de solo lectura para el usuario
        setFieldEditable(estRegField, false);
        // Al agregar, prerellenar el estado
        if (editable && modo.equals(FormMode.ADD)) {
            estRegField.setText("A");
        }
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Ciudad buildCiudadFromFields() {
        String cod    = codField.getText().trim();
        String nom    = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        Region rSel   = (Region) regComboBox.getSelectedItem();
        String regCod = rSel.getRegCod();
        return new Ciudad(cod, nom, regCod, estReg);
    }

    private void loadRegionesInComboBox(JComboBox<Region> box) {
        try {
            for (Region r : new RegionDAO().listarTodo()) box.addItem(r);
        } catch (Exception e) {
            System.err.println("Error al cargar regiones en CiudadForm");
        }
    }
}