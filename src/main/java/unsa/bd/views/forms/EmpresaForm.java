package unsa.bd.views.forms;

import unsa.bd.dao.EmpresaDAO;
import unsa.bd.model.Empresa;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.util.List;

public class EmpresaForm extends BaseForm {

    private JTextField cliCodField;
    private JTextField nomField;
    private JTextField rucField;
    private JTextField razSocField;
    private JTextField estRegField;

    private static final int COL_EST_REG = 4;

    public EmpresaForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "EMPRESA";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Cód. Cliente", "Nombre", "RUC", "Razón Social", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        cliCodField = makeStyledField(10);
        addFieldRowToForm(form, "Cód. Cliente:", cliCodField);

        nomField = makeStyledField(30);
        addFieldRowToForm(form, "Nombre:", nomField);

        rucField = makeStyledField(15);
        addFieldRowToForm(form, "RUC:", rucField);

        razSocField = makeStyledField(50);
        addFieldRowToForm(form, "Razón Social:", razSocField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        EmpresaDAO dao = new EmpresaDAO();
        try {
            List<Empresa> empresas = dao.listarTodo();
            for (Empresa e : empresas) {
                tableModel.addRow(new Object[]{
                        e.getEmpCliCod(),
                        e.getEmpNom(),
                        e.getEmpRUC(),
                        e.getEmpRazSoc(),
                        e.getEmpEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Empresa e = buildFromFields();
        if (e.getEmpCliCod().isEmpty()) throw new Exception("Llenar el campo de código de cliente");
        if (!e.getEmpCliCod().matches("\\d+")) throw new Exception("El código de cliente solo acepta números");
        if (e.getEmpRUC().isEmpty()) throw new Exception("Llenar el campo RUC");
        new EmpresaDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new EmpresaDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new EmpresaDAO().eliminar(buildFromFields().getEmpCliCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new EmpresaDAO().inactivar(buildFromFields().getEmpCliCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new EmpresaDAO().reactivar(buildFromFields().getEmpCliCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        cliCodField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        rucField.setText(tableModel.getValueAt(row, 2).toString());
        razSocField.setText(tableModel.getValueAt(row, 3).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());
    }

    @Override
    protected void clearFields() {
        cliCodField.setText("");
        nomField.setText("");
        rucField.setText("");
        razSocField.setText("");
        estRegField.setText("");
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(cliCodField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(rucField, editable);
        setFieldEditable(razSocField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Empresa buildFromFields() {
        return new Empresa(
                cliCodField.getText().trim(),
                nomField.getText().trim(),
                rucField.getText().trim(),
                razSocField.getText().trim(),
                estRegField.getText().trim()
        );
    }
}