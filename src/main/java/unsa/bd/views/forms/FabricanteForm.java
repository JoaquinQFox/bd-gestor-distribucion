package unsa.bd.views.forms;

import unsa.bd.dao.FabricanteDAO;
import unsa.bd.model.Fabricante;
import unsa.bd.views.forms.util.FormMode;
import javax.swing.*;
import java.util.List;

public class FabricanteForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField estRegField;

    public FabricanteForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "FABRICANTE";
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
        FabricanteDAO dao = new FabricanteDAO();
        try {
            List<Fabricante> fabricantes = dao.listarTodo();
            for (Fabricante f : fabricantes) {
                tableModel.addRow(new Object[]{
                        f.getFabCod(),
                        f.getFabNom(),
                        f.getFabEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Fabricante f = buildFromFields();
        if (f.getFabCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!f.getFabCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new FabricanteDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new FabricanteDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new FabricanteDAO().eliminar(buildFromFields().getFabCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new FabricanteDAO().inactivar(buildFromFields().getFabCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new FabricanteDAO().reactivar(buildFromFields().getFabCod());
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

    private Fabricante buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        String estReg = estRegField.getText().trim();
        return new Fabricante(cod, nom, estReg);
    }
}