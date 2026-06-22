package unsa.bd.views.forms;

import unsa.bd.dao.AlmacenDAO;
import unsa.bd.dao.ProductoDAO;
import unsa.bd.dao.StockDAO;
import unsa.bd.model.Almacen;
import unsa.bd.model.Producto;
import unsa.bd.model.Stock;
import unsa.bd.views.forms.util.FormMode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StockForm extends BaseForm {

    private JComboBox<Producto> proComboBox;
    private JComboBox<Almacen>  almComboBox;
    private JTextField          canDisField;
    private JTextField          canMinField;
    private JTextField          estRegField;

    private static final int COL_EST_REG = 4;

    public StockForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "STOCK";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Producto", "Almacén", "Can. Disponible", "Can. Mínima", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        proComboBox = makeStyledComboBox(Producto::getProDes);
        loadProductosInComboBox(proComboBox);
        addFieldRowToForm(form, "Producto:", proComboBox);
        proComboBox.setPreferredSize(new Dimension(250, proComboBox.getPreferredSize().height));

        almComboBox = makeStyledComboBox(Almacen::getAlmNom);
        loadAlmacenesInComboBox(almComboBox);
        addFieldRowToForm(form, "Almacén:", almComboBox);

        canDisField = makeStyledField(10);
        addFieldRowToForm(form, "Can. Disponible:", canDisField);

        canMinField = makeStyledField(10);
        addFieldRowToForm(form, "Can. Mínima:", canMinField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        StockDAO    dao    = new StockDAO();
        ProductoDAO proDao = new ProductoDAO();
        AlmacenDAO  almDao = new AlmacenDAO();
        try {
            List<Stock>    stocks    = dao.listarTodo();
            List<Producto> productos = proDao.listarTodo();
            List<Almacen>  almacenes = almDao.listarTodo();

            for (Stock s : stocks) {
                String nomPro = "";
                for (Producto p : productos) {
                    if (p.getProCod().equals(s.getStoProCod())) { nomPro = p.getProDes(); break; }
                }
                String nomAlm = "";
                for (Almacen a : almacenes) {
                    if (a.getAlmCod().equals(s.getStoAlmCod())) { nomAlm = a.getAlmNom(); break; }
                }
                tableModel.addRow(new Object[]{
                        nomPro,
                        nomAlm,
                        s.getStoCanDis(),
                        s.getStoCanMin(),
                        s.getStoEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        Stock s = buildFromFields();
        if (s.getStoProCod().isEmpty()) throw new Exception("Seleccionar un producto");
        if (s.getStoAlmCod().isEmpty()) throw new Exception("Seleccionar un almacén");
        new StockDAO().agregar(s);
    }

    @Override
    protected void onModify() throws Exception {
        new StockDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        Stock s = buildFromFields();
        new StockDAO().eliminar(s.getStoProCod(), s.getStoAlmCod());
    }

    @Override
    protected void onInactivate() throws Exception {
        Stock s = buildFromFields();
        new StockDAO().inactivar(s.getStoProCod(), s.getStoAlmCod());
    }

    @Override
    protected void onReactivate() throws Exception {
        Stock s = buildFromFields();
        new StockDAO().reactivar(s.getStoProCod(), s.getStoAlmCod());
    }

    @Override
    protected void fillFormFromRow(int row) {
        canDisField.setText(tableModel.getValueAt(row, 2).toString());
        canMinField.setText(tableModel.getValueAt(row, 3).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomPro = tableModel.getValueAt(row, 0).toString();
        for (int i = 0; i < proComboBox.getItemCount(); i++) {
            if (proComboBox.getItemAt(i).getProDes().equals(nomPro)) {
                proComboBox.setSelectedIndex(i); break;
            }
        }

        String nomAlm = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < almComboBox.getItemCount(); i++) {
            if (almComboBox.getItemAt(i).getAlmNom().equals(nomAlm)) {
                almComboBox.setSelectedIndex(i); break;
            }
        }
    }

    @Override
    protected void clearFields() {
        canDisField.setText("");
        canMinField.setText("");
        estRegField.setText("");
        if (proComboBox.getItemCount() > 0) proComboBox.setSelectedIndex(0);
        if (almComboBox.getItemCount() > 0) almComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        // En ADD ambos combos son editables; en MODIFY se bloquean porque son PK
        setComboEditable(proComboBox, editable && modo.equals(FormMode.ADD));
        setComboEditable(almComboBox, editable && modo.equals(FormMode.ADD));
        setFieldEditable(canDisField, editable);
        setFieldEditable(canMinField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD))
            estRegField.setText("A");
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private Stock buildFromFields() {
        Producto pSel  = (Producto) proComboBox.getSelectedItem();
        Almacen  aSel  = (Almacen)  almComboBox.getSelectedItem();
        String proCod  = pSel != null ? pSel.getProCod()  : "";
        String almCod  = aSel != null ? aSel.getAlmCod()  : "";

        String disStr  = canDisField.getText().trim();
        String minStr  = canMinField.getText().trim();
        int canDis     = disStr.isEmpty() ? 0 : Integer.parseInt(disStr);
        int canMin     = minStr.isEmpty() ? 0 : Integer.parseInt(minStr);

        return new Stock(proCod, almCod, canDis, canMin, estRegField.getText().trim());
    }

    private void loadProductosInComboBox(JComboBox<Producto> box) {
        try {
            for (Producto p : new ProductoDAO().listarTodo()) box.addItem(p);
        } catch (Exception e) {
            System.err.println("Error al cargar productos en StockForm");
        }
    }

    private void loadAlmacenesInComboBox(JComboBox<Almacen> box) {
        try {
            for (Almacen a : new AlmacenDAO().listarTodo()) box.addItem(a);
        } catch (Exception e) {
            System.err.println("Error al cargar almacenes en StockForm");
        }
    }
}