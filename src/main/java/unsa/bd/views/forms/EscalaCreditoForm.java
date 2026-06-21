package unsa.bd.views.forms;

import unsa.bd.dao.EscalaCreditoDAO;
import unsa.bd.model.EscalaCredito;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

public class EscalaCreditoForm extends BaseForm {

    private JTextField codField;
    private JTextField nomField;
    private JTextField limCreField;
    private JTextField estRegField;

    public EscalaCreditoForm() {
        super("EG001 - ESCALA CREDITO");
    }

    @Override
    protected String getHeaderTitle() {
        return "ESCALA CREDITO";
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Nombre
        nomField = makeStyledField(20);
        addFieldRowToForm(form, "Nombre:", nomField);

        // Limite credito
        limCreField = makeStyledField(10);
        addFieldRowToForm(form, "Límite Credito:", limCreField);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Límite Credito", "Estado de Registro"};
    }

    @Override
    protected void loadTableData() {
        EscalaCreditoDAO dao = new EscalaCreditoDAO();
        try {
            List<EscalaCredito> es = dao.listarTodo();

            for (EscalaCredito e : es) {
                tableModel.addRow(new Object[]{
                        e.getEscCreCod(),
                        e.getEscCreNom(),
                        e.getEscCreLimCre().toString(),
                        e.getEscCreEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        EscalaCredito e = buildFromFields();
        if (e.getEscCreCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!e.getEscCreCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new EscalaCreditoDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new EscalaCreditoDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new EscalaCreditoDAO().eliminar(buildFromFields().getEscCreCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new EscalaCreditoDAO().inactivar(buildFromFields().getEscCreCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new EscalaCreditoDAO().reactivar(buildFromFields().getEscCreCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        limCreField.setText(tableModel.getValueAt(row, 2).toString());
        estRegField.setText(tableModel.getValueAt(row, 3).toString());
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        nomField.setText("");
        limCreField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(limCreField, editable);
        setFieldEditable(estRegField, false);

        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        return table.getValueAt(row, 3).toString().trim();
    }

    private EscalaCredito buildFromFields() {
        String cod = codField.getText().trim();
        String nom = nomField.getText().trim();
        BigDecimal limCre = new BigDecimal(limCreField.getText().trim());
        String estReg = estRegField.getText().trim();
        return new EscalaCredito(cod, nom, limCre, estReg);
    }
}