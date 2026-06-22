package unsa.bd.modules.cliente.cliente;

import unsa.bd.modules.personal.repventa.RepVentaDAO;
import unsa.bd.modules.shared.departamento.DepartamentoDAO;
import unsa.bd.modules.cliente.tipocliente.TipoClienteDAO;
import unsa.bd.modules.cliente.escalacredito.EscalaCreditoDAO;
import unsa.bd.modules.personal.repventa.RepVenta;
import unsa.bd.modules.shared.departamento.Departamento;
import unsa.bd.modules.cliente.tipocliente.TipoCliente;
import unsa.bd.modules.cliente.escalacredito.EscalaCredito;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.util.List;

public class ClienteForm extends BaseForm
{

    private JTextField               codField;
    private JTextField               dirField;
    private JTextField               corField;
    private JComboBox<RepVenta>      repComboBox;
    private JComboBox<Departamento>  depComboBox;
    private JComboBox<TipoCliente>   tipCliComboBox;
    private JComboBox<EscalaCredito> escCreComboBox;
    private JTextField               estRegField;

    private static final int COL_EST_REG = 7;

    public ClienteForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "CLIENTE";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Dirección", "Correo", "Rep. Ventas", "Departamento", "Tipo Cliente", "Esc. Crédito", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        dirField = makeStyledField(50);
        addFieldRowToForm(form, "Dirección:", dirField);

        corField = makeStyledField(50);
        addFieldRowToForm(form, "Correo:", corField);

        // Rep. Ventas muestra nombre
        repComboBox = makeStyledComboBox(RepVenta::getRepVenNom);
        loadRepVentasInComboBox(repComboBox);
        addFieldRowToForm(form, "Rep. Ventas:", repComboBox);

        depComboBox = makeStyledComboBox(Departamento::getDepNom);
        loadDepartamentosInComboBox(depComboBox);
        addFieldRowToForm(form, "Departamento:", depComboBox);

        tipCliComboBox = makeStyledComboBox(TipoCliente::getTipCliNom);
        loadTipoClienteInComboBox(tipCliComboBox);
        addFieldRowToForm(form, "Tipo Cliente:", tipCliComboBox);

        escCreComboBox = makeStyledComboBox(EscalaCredito::getEscCreNom);
        loadEscalaCreditoInComboBox(escCreComboBox);
        addFieldRowToForm(form, "Esc. Crédito:", escCreComboBox);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        ClienteDAO       dao       = new ClienteDAO();
        RepVentaDAO      repDao    = new RepVentaDAO();
        DepartamentoDAO  depDao    = new DepartamentoDAO();
        TipoClienteDAO   tipCliDao = new TipoClienteDAO();
        EscalaCreditoDAO escCreDao = new EscalaCreditoDAO();
        try {
            List<Cliente>       clientes    = dao.listarTodo();
            List<RepVenta>      repVentas   = repDao.listarTodo();
            List<Departamento>  deptos      = depDao.listarTodo();
            List<TipoCliente>   tipClientes = tipCliDao.listarTodo();
            List<EscalaCredito> escCreditos = escCreDao.listarTodo();

            for (Cliente c : clientes) {
                String nomRep = "";
                for (RepVenta r : repVentas) {
                    if (r.getRepVenCod().equals(c.getCliRepCod())) { nomRep = r.getRepVenNom(); break; }
                }
                String nomDep = "";
                for (Departamento d : deptos) {
                    if (d.getDepCod().equals(c.getCliDep())) { nomDep = d.getDepNom(); break; }
                }
                String nomTipCli = "";
                for (TipoCliente t : tipClientes) {
                    if (t.getTipCliCod().equals(c.getCliTipCliCod())) { nomTipCli = t.getTipCliNom(); break; }
                }
                String nomEscCre = "";
                for (EscalaCredito e : escCreditos) {
                    if (e.getEscCreCod().equals(c.getCliEscCreCod())) { nomEscCre = e.getEscCreNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        c.getCliCod(),
                        c.getCliDir(),
                        c.getCliCor(),
                        nomRep,
                        nomDep,
                        nomTipCli,
                        nomEscCre,
                        c.getCliEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Cliente c = buildFromFields();
        if (c.getCliCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!c.getCliCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        new ClienteDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new ClienteDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new ClienteDAO().eliminar(buildFromFields().getCliCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new ClienteDAO().inactivar(buildFromFields().getCliCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new ClienteDAO().reactivar(buildFromFields().getCliCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        dirField.setText(tableModel.getValueAt(row, 1).toString());
        corField.setText(tableModel.getValueAt(row, 2).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomRep = tableModel.getValueAt(row, 3).toString();
        for (int i = 0; i < repComboBox.getItemCount(); i++) {
            if (repComboBox.getItemAt(i).getRepVenNom().equals(nomRep)) {
                repComboBox.setSelectedIndex(i); break;
            }
        }

        String nomDep = tableModel.getValueAt(row, 4).toString();
        for (int i = 0; i < depComboBox.getItemCount(); i++) {
            if (depComboBox.getItemAt(i).getDepNom().equals(nomDep)) {
                depComboBox.setSelectedIndex(i); break;
            }
        }

        String nomTipCli = tableModel.getValueAt(row, 5).toString();
        for (int i = 0; i < tipCliComboBox.getItemCount(); i++) {
            if (tipCliComboBox.getItemAt(i).getTipCliNom().equals(nomTipCli)) {
                tipCliComboBox.setSelectedIndex(i); break;
            }
        }

        String nomEscCre = tableModel.getValueAt(row, 6).toString();
        for (int i = 0; i < escCreComboBox.getItemCount(); i++) {
            if (escCreComboBox.getItemAt(i).getEscCreNom().equals(nomEscCre)) {
                escCreComboBox.setSelectedIndex(i); break;
            }
        }
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        dirField.setText("");
        corField.setText("");
        estRegField.setText("");
        if (repComboBox.getItemCount()    > 0) repComboBox.setSelectedIndex(0);
        if (depComboBox.getItemCount()    > 0) depComboBox.setSelectedIndex(0);
        if (tipCliComboBox.getItemCount() > 0) tipCliComboBox.setSelectedIndex(0);
        if (escCreComboBox.getItemCount() > 0) escCreComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(dirField, editable);
        setFieldEditable(corField, editable);
        setComboEditable(repComboBox, editable);
        setComboEditable(depComboBox, editable);
        setComboEditable(tipCliComboBox, editable);
        setComboEditable(escCreComboBox, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Cliente buildFromFields() {
        String cod    = codField.getText().trim();
        String dir    = dirField.getText().trim();
        String cor    = corField.getText().trim();
        String estReg = estRegField.getText().trim();

        RepVenta     rSel = (RepVenta)     repComboBox.getSelectedItem();
        Departamento dSel = (Departamento) depComboBox.getSelectedItem();
        TipoCliente  tSel = (TipoCliente)  tipCliComboBox.getSelectedItem();
        EscalaCredito eSel = (EscalaCredito) escCreComboBox.getSelectedItem();

        String repCod    = rSel != null ? rSel.getRepVenCod()  : "";
        String depCod    = dSel != null ? dSel.getDepCod()     : "";
        String tipCliCod = tSel != null ? tSel.getTipCliCod()  : "";
        String escCreCod = eSel != null ? eSel.getEscCreCod()  : "";

        return new Cliente(cod, dir, cor, repCod, depCod, tipCliCod, escCreCod, estReg);
    }

    private void loadRepVentasInComboBox(JComboBox<RepVenta> box) {
        try {
            for (RepVenta r : new RepVentaDAO().listarTodo()) box.addItem(r);
        } catch (Exception e) {
            System.err.println("Error al cargar rep. ventas en ClienteForm");
        }
    }

    private void loadDepartamentosInComboBox(JComboBox<Departamento> box) {
        try {
            for (Departamento d : new DepartamentoDAO().listarTodo()) box.addItem(d);
        } catch (Exception e) {
            System.err.println("Error al cargar departamentos en ClienteForm");
        }
    }

    private void loadTipoClienteInComboBox(JComboBox<TipoCliente> box) {
        try {
            for (TipoCliente t : new TipoClienteDAO().listarTodo()) box.addItem(t);
        } catch (Exception e) {
            System.err.println("Error al cargar tipos de cliente en ClienteForm");
        }
    }

    private void loadEscalaCreditoInComboBox(JComboBox<EscalaCredito> box) {
        try {
            for (EscalaCredito e : new EscalaCreditoDAO().listarTodo()) box.addItem(e);
        } catch (Exception e) {
            System.err.println("Error al cargar escalas de crédito en ClienteForm");
        }
    }
}