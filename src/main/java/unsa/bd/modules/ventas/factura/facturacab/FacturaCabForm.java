package unsa.bd.modules.ventas.factura.facturacab;

import unsa.bd.modules.cliente.cliente.Cliente;
import unsa.bd.modules.cliente.cliente.ClienteDAO;
import unsa.bd.modules.personal.repventa.RepVenta;
import unsa.bd.modules.personal.repventa.RepVentaDAO;
import unsa.bd.modules.ventas.factura.estadofactura.EstadoFactura;
import unsa.bd.modules.ventas.factura.estadofactura.EstadoFacturaDAO;
import unsa.bd.modules.ventas.factura.tipofactura.TipoFactura;
import unsa.bd.modules.ventas.factura.tipofactura.TipoFacturaDAO;
import unsa.bd.modules.ventas.pedido.pedidocab.PedidoCab;
import unsa.bd.modules.ventas.pedido.pedidocab.PedidoCabDAO;
import unsa.bd.commons.utility.BaseForm;
import unsa.bd.commons.utility.FormMode;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class FacturaCabForm extends BaseForm
{

    private JTextField                  numField;
    private JComboBox<Cliente>          cliComboBox;
    private JComboBox<RepVenta>         repComboBox;
    private JComboBox<PedidoCab>        pedComboBox;
    private JComboBox<TipoFactura>      tipComboBox;
    private JTextField                  diaField;
    private JTextField                  mesField;
    private JTextField                  yeaField;
    private JComboBox<EstadoFactura>    estFacComboBox;
    private JTextField                  subTotField;
    private JTextField                  igvField;
    private JTextField                  impField;
    private JTextField                  diaVenField;
    private JTextField                  mesVenField;
    private JTextField                  yeaVenField;
    private JTextField                  estRegField;

    private static final int COL_EST_REG = 15;

    public FacturaCabForm(String title) {
        super(title);
    }

    @Override
    protected String getHeaderTitle() {
        return "FACTURA - CABECERA";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
            "Número", "Cliente", "Rep. Ventas", "Pedido", "Tipo",
            "Día", "Mes", "Año", "Estado Factura", "Subtotal",
            "IGV", "Importe", "Día Ven.", "Mes Ven.", "Año Ven.", "Est. Reg."
        };
    }

    @Override
    protected JPanel buildFormFields(JPanel form) {
        numField = makeStyledField(10);
        addFieldRowToForm(form, "Número:", numField);

        cliComboBox = makeStyledComboBox(Cliente::getCliCod);
        loadClientesInComboBox(cliComboBox);
        addFieldRowToForm(form, "Cliente:", cliComboBox);

        repComboBox = makeStyledComboBox(RepVenta::getRepVenNom);
        loadRepVentasInComboBox(repComboBox);
        addFieldRowToForm(form, "Rep. Ventas:", repComboBox);

        pedComboBox = makeStyledComboBox(PedidoCab::getPedCabNum);
        loadPedidosInComboBox(pedComboBox);
        addFieldRowToForm(form, "Pedido:", pedComboBox);

        tipComboBox = makeStyledComboBox(TipoFactura::getTipFacNom);
        loadTiposFacturaInComboBox(tipComboBox);
        addFieldRowToForm(form, "Tipo:", tipComboBox);

        diaField = makeStyledField(5);
        addFieldRowToForm(form, "Día:", diaField);

        mesField = makeStyledField(5);
        addFieldRowToForm(form, "Mes:", mesField);

        yeaField = makeStyledField(5);
        addFieldRowToForm(form, "Año:", yeaField);

        estFacComboBox = makeStyledComboBox(EstadoFactura::getEstFacNom);
        loadEstadosFacturaInComboBox(estFacComboBox);
        addFieldRowToForm(form, "Estado Factura:", estFacComboBox);

        subTotField = makeStyledField(15);
        addFieldRowToForm(form, "Subtotal:", subTotField);

        igvField = makeStyledField(15);
        addFieldRowToForm(form, "IGV:", igvField);

        impField = makeStyledField(15);
        addFieldRowToForm(form, "Importe:", impField);

        diaVenField = makeStyledField(5);
        addFieldRowToForm(form, "Día Venc.:", diaVenField);

        mesVenField = makeStyledField(5);
        addFieldRowToForm(form, "Mes Venc.:", mesVenField);

        yeaVenField = makeStyledField(5);
        addFieldRowToForm(form, "Año Venc.:", yeaVenField);

        estRegField = makeStyledField(5);
        addFieldRowToForm(form, "Est. Reg.:", estRegField);

        return form;
    }

    @Override
    protected void loadTableData() {
        FacturaCabDAO dao       = new FacturaCabDAO();
        ClienteDAO cliDao    = new ClienteDAO();
        RepVentaDAO repDao    = new RepVentaDAO();
        TipoFacturaDAO tipDao    = new TipoFacturaDAO();
        EstadoFacturaDAO estFacDao = new EstadoFacturaDAO();
        try {
            List<FacturaCab>    facturas  = dao.listarTodo();
            List<Cliente>       clientes  = cliDao.listarTodo();
            List<RepVenta>      repVentas = repDao.listarTodo();
            List<TipoFactura>   tipos     = tipDao.listarTodo();
            List<EstadoFactura> estados   = estFacDao.listarTodo();

            for (FacturaCab f : facturas) {
                String codCli = f.getFacCabCliCod();

                String nomRep = "";
                for (RepVenta r : repVentas) {
                    if (r.getRepVenCod().equals(f.getFacCabRepCod())) { nomRep = r.getRepVenNom(); break; }
                }
                String nomTip = "";
                for (TipoFactura t : tipos) {
                    if (t.getTipFacCod().equals(f.getFacCabTipCod())) { nomTip = t.getTipFacNom(); break; }
                }
                String nomEst = "";
                for (EstadoFactura e : estados) {
                    if (e.getEstFacCod().equals(f.getFacCabEstCod())) { nomEst = e.getEstFacNom(); break; }
                }

                tableModel.addRow(new Object[]{
                        f.getFacCabNum(),
                        codCli,
                        nomRep,
                        f.getFacCabPedNum(),
                        nomTip,
                        f.getFacCabDia(),
                        f.getFacCabMes(),
                        f.getFacCabYea(),
                        nomEst,
                        f.getFacCabSubTot(),
                        f.getFacCabIGV(),
                        f.getFacCabImp(),
                        f.getFacCabDiaVen(),
                        f.getFacCabMesVen(),
                        f.getFacCabYeaVen(),
                        f.getFacCabEstReg()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() throws Exception {
        FacturaCab f = buildFromFields();
        if (f.getFacCabNum().isEmpty()) throw new Exception("Llenar el campo número");
        if (!f.getFacCabNum().matches("\\d+")) throw new Exception("El número solo acepta dígitos");
        if (f.getFacCabCliCod().isEmpty()) throw new Exception("Seleccionar un cliente");
        new FacturaCabDAO().agregar(f);
    }

    @Override
    protected void onModify() throws Exception {
        new FacturaCabDAO().modificar(buildFromFields());
    }

    @Override
    protected void onDelete() throws Exception {
        new FacturaCabDAO().eliminar(buildFromFields().getFacCabNum());
    }

    @Override
    protected void onInactivate() throws Exception {
        new FacturaCabDAO().inactivar(buildFromFields().getFacCabNum());
    }

    @Override
    protected void onReactivate() throws Exception {
        new FacturaCabDAO().reactivar(buildFromFields().getFacCabNum());
    }

    @Override
    protected void fillFormFromRow(int row) {
        numField.setText(tableModel.getValueAt(row, 0).toString());
        diaField.setText(tableModel.getValueAt(row, 5).toString());
        mesField.setText(tableModel.getValueAt(row, 6).toString());
        yeaField.setText(tableModel.getValueAt(row, 7).toString());
        subTotField.setText(tableModel.getValueAt(row, 9).toString());
        igvField.setText(tableModel.getValueAt(row, 10).toString());
        impField.setText(tableModel.getValueAt(row, 11).toString());
        diaVenField.setText(tableModel.getValueAt(row, 12).toString());
        mesVenField.setText(tableModel.getValueAt(row, 13).toString());
        yeaVenField.setText(tableModel.getValueAt(row, 14).toString());
        estRegField.setText(tableModel.getValueAt(row, COL_EST_REG).toString());

        String codCli = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < cliComboBox.getItemCount(); i++) {
            if (cliComboBox.getItemAt(i).getCliCod().equals(codCli)) {
                cliComboBox.setSelectedIndex(i); break;
            }
        }

        String nomRep = tableModel.getValueAt(row, 2).toString();
        for (int i = 0; i < repComboBox.getItemCount(); i++) {
            if (repComboBox.getItemAt(i).getRepVenNom().equals(nomRep)) {
                repComboBox.setSelectedIndex(i); break;
            }
        }

        String numPed = tableModel.getValueAt(row, 3).toString();
        for (int i = 0; i < pedComboBox.getItemCount(); i++) {
            if (pedComboBox.getItemAt(i).getPedCabNum().equals(numPed)) {
                pedComboBox.setSelectedIndex(i); break;
            }
        }

        String nomTip = tableModel.getValueAt(row, 4).toString();
        for (int i = 0; i < tipComboBox.getItemCount(); i++) {
            if (tipComboBox.getItemAt(i).getTipFacNom().equals(nomTip)) {
                tipComboBox.setSelectedIndex(i); break;
            }
        }

        String nomEst = tableModel.getValueAt(row, 8).toString();
        for (int i = 0; i < estFacComboBox.getItemCount(); i++) {
            if (estFacComboBox.getItemAt(i).getEstFacNom().equals(nomEst)) {
                estFacComboBox.setSelectedIndex(i); break;
            }
        }
    }

    @Override
    protected void clearFields() {
        numField.setText("");
        diaField.setText("");
        mesField.setText("");
        yeaField.setText("");
        subTotField.setText("");
        igvField.setText("");
        impField.setText("");
        diaVenField.setText("");
        mesVenField.setText("");
        yeaVenField.setText("");
        estRegField.setText("");
        if (cliComboBox.getItemCount()    > 0) cliComboBox.setSelectedIndex(0);
        if (repComboBox.getItemCount()    > 0) repComboBox.setSelectedIndex(0);
        if (pedComboBox.getItemCount()    > 0) pedComboBox.setSelectedIndex(0);
        if (tipComboBox.getItemCount()    > 0) tipComboBox.setSelectedIndex(0);
        if (estFacComboBox.getItemCount() > 0) estFacComboBox.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    protected void setFieldsEditable(boolean editable) {
        setFieldEditable(numField, editable && modo.equals(FormMode.ADD));
        setComboEditable(cliComboBox, editable);
        setComboEditable(repComboBox, editable);
        setComboEditable(pedComboBox, editable);
        setComboEditable(tipComboBox, editable);
        setFieldEditable(diaField, editable);
        setFieldEditable(mesField, editable);
        setFieldEditable(yeaField, editable);
        setComboEditable(estFacComboBox, editable);
        setFieldEditable(subTotField, editable);
        setFieldEditable(igvField, editable);
        setFieldEditable(impField, editable);
        setFieldEditable(diaVenField, editable);
        setFieldEditable(mesVenField, editable);
        setFieldEditable(yeaVenField, editable);
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

    private FacturaCab buildFromFields() {
        String num = numField.getText().trim();

        String subTotStr = subTotField.getText().trim();
        BigDecimal subTot = subTotStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(subTotStr);

        String igvStr = igvField.getText().trim();
        BigDecimal igv = igvStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(igvStr);

        String impStr = impField.getText().trim();
        BigDecimal imp = impStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(impStr);

        String estReg = estRegField.getText().trim();

        int dia = diaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(diaField.getText().trim());
        int mes = mesField.getText().trim().isEmpty() ? 0 : Integer.parseInt(mesField.getText().trim());
        int yea = yeaField.getText().trim().isEmpty() ? 0 : Integer.parseInt(yeaField.getText().trim());

        int diaVen = diaVenField.getText().trim().isEmpty() ? 0 : Integer.parseInt(diaVenField.getText().trim());
        int mesVen = mesVenField.getText().trim().isEmpty() ? 0 : Integer.parseInt(mesVenField.getText().trim());
        int yeaVen = yeaVenField.getText().trim().isEmpty() ? 0 : Integer.parseInt(yeaVenField.getText().trim());

        Cliente       cSel = (Cliente)      cliComboBox.getSelectedItem();
        RepVenta      rSel = (RepVenta)     repComboBox.getSelectedItem();
        PedidoCab     pSel = (PedidoCab)    pedComboBox.getSelectedItem();
        TipoFactura   tSel = (TipoFactura)  tipComboBox.getSelectedItem();
        EstadoFactura eSel = (EstadoFactura) estFacComboBox.getSelectedItem();

        String cliCod = cSel != null ? cSel.getCliCod()    : "";
        String repCod = rSel != null ? rSel.getRepVenCod() : "";
        String pedNum = pSel != null ? pSel.getPedCabNum() : "";
        String tipCod = tSel != null ? tSel.getTipFacCod()    : "";
        String estCod = eSel != null ? eSel.getEstFacCod() : "";

        return new FacturaCab(num, cliCod, repCod, pedNum, tipCod, dia, mes, yea, estCod, subTot, igv, imp, diaVen, mesVen, yeaVen, estReg);
    }

    private void loadClientesInComboBox(JComboBox<Cliente> box) {
        try {
            Cliente n = new Cliente();
            n.setCliCod("Elegir cliente");
            box.addItem(n);
            for (Cliente c : new ClienteDAO().listarTodo()) box.addItem(c);
        } catch (Exception e) {
            System.err.println("Error al cargar clientes en FacturaCabForm");
        }
    }

    private void loadRepVentasInComboBox(JComboBox<RepVenta> box) {
        try {
            RepVenta n = new RepVenta();
            n.setRepVenNom("");
            box.addItem(n);
            for (RepVenta r : new RepVentaDAO().listarTodo()) box.addItem(r);
        } catch (Exception e) {
            System.err.println("Error al cargar rep. ventas en FacturaCabForm");
        }
    }

    private void loadPedidosInComboBox(JComboBox<PedidoCab> box) {
        try {
            PedidoCab n = new PedidoCab();
            n.setPedCabNum("");
            box.addItem(n);
            for (PedidoCab p : new PedidoCabDAO().listarTodo()) box.addItem(p);
        } catch (Exception e) {
            System.err.println("Error al cargar pedidos en FacturaCabForm");
        }
    }

    private void loadTiposFacturaInComboBox(JComboBox<TipoFactura> box) {
        try {
            TipoFactura n = new TipoFactura();
            n.setTipFacNom("");
            box.addItem(n);
            for (TipoFactura t : new TipoFacturaDAO().listarTodo()) box.addItem(t);
        } catch (Exception e) {
            System.err.println("Error al cargar tipos de factura en FacturaCabForm");
        }
    }

    private void loadEstadosFacturaInComboBox(JComboBox<EstadoFactura> box) {
        try {
            EstadoFactura n = new EstadoFactura();
            n.setEstFacNom("");
            box.addItem(n);
            for (EstadoFactura e : new EstadoFacturaDAO().listarTodo()) box.addItem(e);
        } catch (Exception e) {
            System.err.println("Error al cargar estados de factura en FacturaCabForm");
        }
    }
}