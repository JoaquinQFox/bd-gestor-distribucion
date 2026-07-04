package unsa.bd.modules.cliente.persona;

import unsa.bd.modules.shared.tipodocumento.TipoDocumentoDAO;
import unsa.bd.modules.shared.tipodocumento.TipoDocumento;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.util.List;

public class PersonaForm extends BaseForm
{

    private JTextField                  cliCodField;
    private JTextField                  nomField;
    private JComboBox<TipoDocumento>    tipDocComboBox;
    private JTextField                  numDocField;
    private JTextField                  estRegField;

    private static final int COL_EST_REG = 4;

    public PersonaForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "PERSONA";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Cód. Cliente", "Nombre", "Tipo Doc.", "Num. Doc.", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        cliCodField = makeStyledField(10);
        addFieldRowToForm(form, "Cód. Cliente:", cliCodField);

        nomField = makeStyledField(30);
        addFieldRowToForm(form, "Nombre:", nomField);

        tipDocComboBox = makeStyledComboBox(TipoDocumento::getTipDocNom);
        loadTipoDocumentosInComboBox(tipDocComboBox);
        addFieldRowToForm(form, "Tipo Doc.:", tipDocComboBox);

        numDocField = makeStyledField(15);
        addFieldRowToForm(form, "Num. Doc.:", numDocField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        PersonaDAO       dao       = new PersonaDAO();
        TipoDocumentoDAO tipDocDao = new TipoDocumentoDAO();
        try {
            List<Persona>       personas  = dao.listarTodo();
            List<TipoDocumento> tipoDocs  = tipDocDao.listarTodo();

            for (Persona p : personas) {
                String nomTipDoc = "";
                for (TipoDocumento t : tipoDocs) {
                    if (t.getTipDocCod().equals(p.getPerTipDocCod())) { nomTipDoc = t.getTipDocNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        p.getPerCliCod(),
                        p.getPerNom(),
                        nomTipDoc,
                        p.getPerNumDoc(),
                        p.getPerEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Persona p = buildFromFields();
        if (p.getPerCliCod().isEmpty()) throw new Exception("Llenar el campo de código de cliente");
        if (!p.getPerCliCod().matches("\\d+")) throw new Exception("El código de cliente solo acepta números");
        if (p.getPerNom().isEmpty()) throw new Exception("Llenar el campo de nombre");
        if (p.getPerNumDoc().isEmpty()) throw new Exception("Llenar el campo de número de documento");
        new PersonaDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new PersonaDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new PersonaDAO().eliminar(buildFromFields().getPerCliCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new PersonaDAO().inactivar(buildFromFields().getPerCliCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new PersonaDAO().reactivar(buildFromFields().getPerCliCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        cliCodField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        numDocField.setText(tableModel.getValueAt(row, 3).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomTipDoc = tableModel.getValueAt(row, 2).toString();
        for (int i = 0; i < tipDocComboBox.getItemCount(); i++) {
            if (tipDocComboBox.getItemAt(i).getTipDocNom().equals(nomTipDoc)) {
                tipDocComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        cliCodField.setText("");
        nomField.setText("");
        numDocField.setText("");
        estRegField.setText("");
        if (tipDocComboBox.getItemCount() > 0) tipDocComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(cliCodField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setComboEditable(tipDocComboBox, editable);
        setFieldEditable(numDocField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Persona buildFromFields() {
        TipoDocumento tSel   = (TipoDocumento) tipDocComboBox.getSelectedItem();
        String        tipCod = tSel != null ? tSel.getTipDocCod() : "";
        return new Persona(
                cliCodField.getText().trim(),
                nomField.getText().trim(),
                tipCod,
                numDocField.getText().trim(),
                estRegField.getText().trim()
        );
    }

    private void loadTipoDocumentosInComboBox(JComboBox<TipoDocumento> box) {
        try {
            TipoDocumento n = new TipoDocumento();
            n.setTipDocNom("");
            box.addItem(n);
            for (TipoDocumento t : new TipoDocumentoDAO().listarTodo()) box.addItem(t);
        } catch (Exception e) {
            System.err.println("Error al cargar tipos de documento en PersonaForm");
        }
    }
}