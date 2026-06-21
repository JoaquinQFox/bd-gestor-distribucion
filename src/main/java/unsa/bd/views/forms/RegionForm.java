package unsa.bd.views.forms;

import unsa.bd.dao.RegionDAO;
import unsa.bd.model.Region;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class RegionForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public RegionForm() {
        super("EG001 - REGIÓN");
    }

    @Override
    protected String getHeaderTitle() {
        return "R2-REGION";
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Nombre
        nomField = makeStyledField(20);
        addFieldRowToForm(form, "Nombre:", nomField);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Estado de Registro"};
    }

    @Override
    protected void loadTableData() {
        RegionDAO dao = new RegionDAO();
        try {
            List<Region> regiones = dao.listarTodo();

            for (Region r : regiones) {
                tableModel.addRow(new Object[]{
                        r.getRegCod(),
                        r.getRegNom(),
                        r.getRegEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Region r = buildFromFields();
        if (r.getRegCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!r.getRegCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new RegionDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new RegionDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new RegionDAO().eliminar(buildFromFields().getRegCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new RegionDAO().inactivar(buildFromFields().getRegCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new RegionDAO().reactivar(buildFromFields().getRegCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        estRegField.setText(tableModel.getValueAt(row, 2).toString());
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        nomField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(estRegField, false);

        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        return table.getValueAt(row, 2).toString().trim();
    }

    private Region buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new Region(cod, nom, estReg);
    }
}