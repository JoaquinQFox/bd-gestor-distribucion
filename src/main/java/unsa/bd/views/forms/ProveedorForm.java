package unsa.bd.views.forms;

import unsa.bd.dao.ProveedorDAO;
import unsa.bd.model.Proveedor;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class ProveedorForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField rucField;
    private JTextField corField;
    private JTextField estRegField;

    private static final int COL_EST_REG = 4;

    public ProveedorForm() {
        super("EG013 - PROVEEDOR");
    }

    @Override
    protected String getHeaderTitle() {
        return "PROVEEDOR";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "RUC", "Correo", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        nomField = makeStyledField(30);
        addFieldRowToForm(form, "Nombre:", nomField);

        rucField = makeStyledField(15);
        addFieldRowToForm(form, "RUC:", rucField);

        corField = makeStyledField(50);
        addFieldRowToForm(form, "Correo:", corField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        ProveedorDAO dao = new ProveedorDAO();
        try {
            List<Proveedor> proveedores = dao.listarTodo();
            for (Proveedor p : proveedores) {
                tableModel.addRow(new Object[]{
                        p.getPrvCod(),
                        p.getPrvNom(),
                        p.getPrvRUC(),
                        p.getPrvCor(),
                        p.getPrvEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Proveedor p = buildFromFields();
        if (p.getPrvCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!p.getPrvCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        if (p.getPrvNom().isEmpty()) throw new Exception("Llenar el campo de nombre");
        if (p.getPrvRUC().isEmpty()) throw new Exception("Llenar el campo de RUC");
        new ProveedorDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new ProveedorDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new ProveedorDAO().eliminar(buildFromFields().getPrvCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new ProveedorDAO().inactivar(buildFromFields().getPrvCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new ProveedorDAO().reactivar(buildFromFields().getPrvCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        rucField.setText(tableModel.getValueAt(row, 2).toString());
        corField.setText(tableModel.getValueAt(row, 3).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        nomField.setText("");
        rucField.setText("");
        corField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(rucField, editable);
        setFieldEditable(corField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Proveedor buildFromFields() {
        return new Proveedor(
                codField.getText().trim(),
                nomField.getText().trim(),
                rucField.getText().trim(),
                corField.getText().trim(),
                estRegField.getText().trim()
        );
    }
}