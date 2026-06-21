package unsa.bd.views.forms;

import unsa.bd.dao.DepartamentoDAO;
import unsa.bd.model.Departamento;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class DepartamentoForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public DepartamentoForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "DEPARTAMENTO";
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
        DepartamentoDAO dao = new DepartamentoDAO();
        try {
            List<Departamento> deps = dao.listarTodo();

            for (Departamento d : deps) {
                tableModel.addRow(new Object[]{
                        d.getDepCod(),
                        d.getDepNom(),
                        d.getDepEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Departamento r = buildFromFields();
        String codigo = r.getDepCod();
        if (codigo.isEmpty()) throw new Exception("Llenar el campo de código");
        if (!codigo.matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new DepartamentoDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new DepartamentoDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new DepartamentoDAO().eliminar(buildFromFields().getDepCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new DepartamentoDAO().inactivar(buildFromFields().getDepCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new DepartamentoDAO().reactivar(buildFromFields().getDepCod());
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

    private Departamento buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new Departamento(cod, nom, estReg);
    }
}