package unsa.bd.views.forms;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class ExampleForm extends BaseForm {

    // Campos de formulario
    JTextField codigo;
    JTextField nombre;
    JTextField estado;

    public ExampleForm() {
        super("Window Title - Example", "EJEMPLO");
    }

    @Override
    protected void buildFormFields(JPanel formPanel) {
        // --- DATOS PRINCIPALES ---
        addFormRow(formPanel, "Código:", 10);
        addFormRow(formPanel, "Nombre:", 0);      // 0 para que este se expanda horizontalmente
        addFormRow(formPanel, "Apellido Pat.:", 0);
        addFormRow(formPanel, "Apellido Mat.:", 0);
        addFormRow(formPanel, "DNI / RUC:", 12);

        // --- DATOS DE CONTACTO ---
        addFormRow(formPanel, "Teléfono:", 15);
        addFormRow(formPanel, "Celular:", 15);
        addFormRow(formPanel, "Correo Elec.:", 0);

        // --- DIRECCIÓN ---
        addFormRow(formPanel, "Dirección:", 0);
        addFormRow(formPanel, "Distrito:", 20);
        addFormRow(formPanel, "Provincia:", 20);
        addFormRow(formPanel, "Departamento:", 20);

        // --- INFORMACIÓN ADICIONAL ---
        addFormRow(formPanel, "Cargo / Ocup.:", 25);
        addFormRow(formPanel, "Área / Secc.:", 25);
        addFormRow(formPanel, "Observaciones:", 0);

        // --- ESTADO Y AUDITORÍA ---
        addFormRow(formPanel, "Est. Reg.:", 5);
        addFormRow(formPanel, "Creado por:", 15);
        addFormRow(formPanel, "Fec. Creación:", 12);
        addFormRow(formPanel, "Modificado por:", 15);
        addFormRow(formPanel, "Fec. Modif.:", 12);
    }
//    protected void buildFormFields(JPanel formPanel) {
//        codigo = addFormRow(formPanel, "Código:", 10);
//        nombre = addFormRow(formPanel, "Nombre:", 20);
//        estado = addFormRow(formPanel, "Est. Reg.:", 5);
//    }
}
