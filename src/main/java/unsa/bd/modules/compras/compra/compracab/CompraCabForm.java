package unsa.bd.modules.compras.compra.compracab;

import unsa.bd.modules.compras.proveedor.ProveedorDAO;
import unsa.bd.modules.inventario.almacen.AlmacenDAO;
import unsa.bd.modules.compras.proveedor.Proveedor;
import unsa.bd.modules.inventario.almacen.Almacen;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class CompraCabForm extends BaseForm
{

    private JTextField numField;
    private JComboBox<Proveedor> prvComboBox;
    private JComboBox<Almacen> almComboBox;
    private JTextField diaField;
    private JTextField mesField;
    private JTextField yeaField;
    private JTextField impField;
    private JTextField estRegField;

    private static final int COL_EST_REG = 7;

    public CompraCabForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "COMPRA - CABECERA";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"Número", "Proveedor", "Almacén", "Día", "Mes", "Año", "Importe", "Est. Reg."};
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        numField = makeStyledField(10);
        addFieldRowToForm(form, "Número:", numField);

        prvComboBox = makeStyledComboBox(Proveedor::getPrvNom);
        loadProveedoresInComboBox(prvComboBox);
        addFieldRowToForm(form, "Proveedor:", prvComboBox);
        prvComboBox.setPreferredSize(new Dimension(250, prvComboBox.getPreferredSize().height));

        almComboBox = makeStyledComboBox(Almacen::getAlmNom);
        loadAlmacenesInComboBox(almComboBox);
        addFieldRowToForm(form, "Almacén:", almComboBox);

        diaField = makeStyledField(5);
        addFieldRowToForm(form, "Día:", diaField);

        mesField = makeStyledField(5);
        addFieldRowToForm(form, "Mes:", mesField);

        yeaField = makeStyledField(5);
        addFieldRowToForm(form, "Año:", yeaField);

        impField = makeStyledField(15);
        addFieldRowToForm(form, "Importe:", impField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        CompraCabDAO dao = new CompraCabDAO();
        ProveedorDAO prvDao = new ProveedorDAO();
        AlmacenDAO almDao = new AlmacenDAO();
        try {
            List<CompraCab> compras = dao.listarTodo();
            List<Proveedor> proveedores = prvDao.listarTodo();
            List<Almacen> almacenes = almDao.listarTodo();

            for (CompraCab c : compras) {
                String nomPrv = "";
                for (Proveedor p : proveedores) {
                    if (p.getPrvCod().equals(c.getComCabPrvCod())) {
                        nomPrv = p.getPrvNom();
                        break;
                    }
                }
                String nomAlm = "";
                for (Almacen a : almacenes) {
                    if (a.getAlmCod().equals(c.getComCabAlmCod())) {
                        nomAlm = a.getAlmNom();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        c.getComCabNum(),
                        nomPrv,
                        nomAlm,
                        c.getComCabDia(),
                        c.getComCabMes(),
                        c.getComCabYea(),
                        c.getComCabImp(),
                        c.getComCabEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        CompraCab c = buildFromFields();
        if (c.getComCabNum().isEmpty()) throw new Exception("Llenar el campo número");
        if (!c.getComCabNum().matches("\\d+")) throw new Exception("El número solo acepta dígitos");
        if (c.getComCabPrvCod().isEmpty()) throw new Exception("Seleccionar un proveedor");
        if (c.getComCabAlmCod().isEmpty()) throw new Exception("Seleccionar un almacén");
        new CompraCabDAO().agregar(c);
    }

    @Override
    protected void onModify() throws Exception {
        new CompraCabDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new CompraCabDAO().eliminar(buildFromFields().getComCabNum());
    }

    @Override
    protected void onInactivate() throws Exception {
        new CompraCabDAO().inactivar(buildFromFields().getComCabNum());
    }

    @Override
    protected void onReactivate() throws Exception {
        new CompraCabDAO().reactivar(buildFromFields().getComCabNum());
    }

    @Override
    protected void fillFormFromRow(int row) {
        numField.setText(tableModel.getValueAt(row, 0).toString());
        diaField.setText(tableModel.getValueAt(row, 3).toString());
        mesField.setText(tableModel.getValueAt(row, 4).toString());
        yeaField.setText(tableModel.getValueAt(row, 5).toString());
        impField.setText(tableModel.getValueAt(row, 6).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String nomPrv = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < prvComboBox.getItemCount(); i++) {
            if (prvComboBox.getItemAt(i).getPrvNom().equals(nomPrv)) {
                prvComboBox.setSelectedIndex(i);
                break;
            }
        }

        String nomAlm = tableModel.getValueAt(row, 2).toString();
        for (int i = 0; i < almComboBox.getItemCount(); i++) {
            if (almComboBox.getItemAt(i).getAlmNom().equals(nomAlm)) {
                almComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearFields() {
        numField.setText("");
        diaField.setText("");
        mesField.setText("");
        yeaField.setText("");
        impField.setText("");
        estRegField.setText("");
        if (prvComboBox.getItemCount() > 0) prvComboBox.setSelectedIndex(0);
        if (almComboBox.getItemCount() > 0) almComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(numField, editable && modo.equals(FormMode.ADD));
        setComboEditable(prvComboBox, editable);
        setComboEditable(almComboBox, editable);
        setFieldEditable(diaField, editable);
        setFieldEditable(mesField, editable);
        setFieldEditable(yeaField, editable);
        setFieldEditable(impField, editable);
        setFieldEditable(estRegField, false);
        if (editable && modo.equals(FormMode.ADD)) {
            estRegField.setText("A");
            Calendar cal = Calendar.getInstance();
            diaField.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            mesField.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
            yeaField.setText(String.valueOf(cal.get(Calendar.YEAR)));
        }
    }

    @Override
    protected String getEstadoFromRow(int row) {
        Object val = tableModel.getValueAt(row, COL_EST_REG);
        return val != null ? val.toString().trim() : "";
    }

    private CompraCab buildFromFields() {
        String num = numField.getText().trim();
        String impStr = impField.getText().trim();
        BigDecimal imp = impStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(impStr);
        String estReg = estRegField.getText().trim();
        int dia = diaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(diaField.getText().trim());
        int mes = mesField.getText().trim().isEmpty() ? 0 : Integer.parseInt(mesField.getText().trim());
        int yea = yeaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(yeaField.getText().trim());

        Proveedor pSel = (Proveedor) prvComboBox.getSelectedItem();
        Almacen aSel = (Almacen) almComboBox.getSelectedItem();

        String prvCod = pSel != null ? pSel.getPrvCod() : "";
        String almCod = aSel != null ? aSel.getAlmCod() : "";

        return new CompraCab(num, prvCod, almCod, dia, mes, yea, imp, estReg);
    }

    private void loadProveedoresInComboBox(JComboBox<Proveedor> box) {
        try {
            Proveedor n = new Proveedor();
            n.setPrvNom("");
            box.addItem(n);
            for (Proveedor p : new ProveedorDAO().listarTodo()) box.addItem(p);
        } catch (Exception e) {
            System.err.println("Error al cargar proveedores en CompraCabForm");
        }
    }

    private void loadAlmacenesInComboBox(JComboBox<Almacen> box) {
        try {
            Almacen n = new Almacen();
            n.setAlmNom("");
            box.addItem(n);
            for (Almacen a : new AlmacenDAO().listarTodo()) box.addItem(a);
        } catch (Exception e) {
            System.err.println("Error al cargar almacenes en CompraCabForm");
        }
    }
}