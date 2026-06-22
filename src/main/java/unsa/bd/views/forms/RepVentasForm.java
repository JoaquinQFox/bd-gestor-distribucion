package unsa.bd.views.forms;

import unsa.bd.dao.CargoDAO;
import unsa.bd.dao.RepVentaDAO;
import unsa.bd.model.Cargo;
import unsa.bd.model.RepVenta;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class RepVentasForm extends BaseForm {

    private JTextField          codField;
    private JTextField          nomField;
    private JTextField          edaField;
    private JTextField          ofiCodField;
    private JComboBox<Cargo>    carComboBox;
    private JTextField          objVenField;
    private JTextField          estRegField;

    private static final int COL_EST_REG = 6;

    public RepVentasForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "REPRESENTANTE DE VENTAS";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Código", "Nombre", "Edad", "Cód. Oficina", "Cargo", "Obj. Venta", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        // Código
        codField = makeStyledField(10);
        addFieldRowToForm(form, "Código:", codField);

        // Nombre
        nomField = makeStyledField(30);
        addFieldRowToForm(form, "Nombre:", nomField);

        // Edad
        edaField = makeStyledField(5);
        addFieldRowToForm(form, "Edad:", edaField);

        // Código Oficina (solo texto)
        ofiCodField = makeStyledField(10);
        addFieldRowToForm(form, "Cód. Oficina:", ofiCodField);

        // Cargo (ComboBox)
        carComboBox = makeStyledComboBox(Cargo::getCarNom);
        loadCargosInComboBox(carComboBox);
        addFieldRowToForm(form, "Cargo:", carComboBox);
        carComboBox.setPreferredSize(new Dimension(250, carComboBox.getPreferredSize().height));

        // Objetivo de Venta
        objVenField = makeStyledField(15);
        addFieldRowToForm(form, "Obj. Venta:", objVenField);

        // Estado de Registro
        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        RepVentaDAO dao    = new RepVentaDAO();
        CargoDAO     carDao = new CargoDAO();
        try {
            List<RepVenta> repVentas = dao.listarTodo();
            List<Cargo>     cargos    = carDao.listarTodo();

            for (RepVenta r : repVentas) {
                String nomCar = "";
                for (Cargo c : cargos) {
                    if (c.getCarCod().equals(r.getRepVenCarCod())) { nomCar = c.getCarNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        r.getRepVenCod(),
                        r.getRepVenNom(),
                        r.getRepVenEda(),
                        r.getRepVenOfiCod(),
                        nomCar,
                        r.getRepVenObjVen(),
                        r.getRepVenEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        RepVenta r = buildFromFields();
        if (r.getRepVenCod().isEmpty()) throw new Exception("Llenar el campo de código");
        if (!r.getRepVenCod().matches("\\d+")) throw new Exception("El campo código solo acepta números");
        if (r.getRepVenOfiCod().isEmpty()) throw new Exception("Llenar el código de oficina");
        new RepVentaDAO().agregar(buildFromFields());
    }

    @Override
    protected void onModify() throws Exception {
        new RepVentaDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new RepVentaDAO().eliminar(buildFromFields().getRepVenCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        new RepVentaDAO().inactivar(buildFromFields().getRepVenCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        new RepVentaDAO().reactivar(buildFromFields().getRepVenCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        codField.setText(tableModel.getValueAt(row, 0).toString());
        nomField.setText(tableModel.getValueAt(row, 1).toString());
        edaField.setText(tableModel.getValueAt(row, 2).toString());
        ofiCodField.setText(tableModel.getValueAt(row, 3).toString());
        objVenField.setText(tableModel.getValueAt(row, 5).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomCar = tableModel.getValueAt(row, 4).toString();
        for (int i = 0; i < carComboBox.getItemCount(); i++) {
            if (carComboBox.getItemAt(i).getCarNom().equals(nomCar)) {
                carComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        codField.setText("");
        nomField.setText("");
        edaField.setText("");
        ofiCodField.setText("");
        objVenField.setText("");
        estRegField.setText("");
        if (carComboBox.getItemCount() > 0) carComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(codField, editable && modo.equals(FormMode.ADD));
        setFieldEditable(nomField, editable);
        setFieldEditable(edaField, editable);
        setFieldEditable(ofiCodField, editable);
        setComboEditable(carComboBox, editable);
        setFieldEditable(objVenField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private RepVenta buildFromFields() {
        String cod    = codField.getText().trim();
        String nom    = nomField.getText().trim();
        String edaStr = edaField.getText().trim();
        String ofiCod = ofiCodField.getText().trim();
        BigDecimal objVen = new BigDecimal(objVenField.getText().trim());
        String estReg = estRegField.getText().trim();
        int    eda    = edaStr.isEmpty() ? 0 : Integer.parseInt(edaStr);

        Cargo  cSel   = (Cargo) carComboBox.getSelectedItem();
        String carCod = cSel != null ? cSel.getCarCod() : "";

        return new RepVenta(cod, nom, eda, ofiCod, carCod, objVen, estReg);
    }

    private void loadCargosInComboBox(JComboBox<Cargo> box) {
        try {
            for (Cargo c : new CargoDAO().listarTodo()) box.addItem(c);
        } catch (Exception e) {
            System.err.println("Error al cargar cargos en RepVentasForm");
        }
    }
}