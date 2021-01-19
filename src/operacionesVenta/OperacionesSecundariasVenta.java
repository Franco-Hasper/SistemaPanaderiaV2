package operacionesVenta;

import calsesPadre.Consultas;
import clasesUtilidadGeneral.OperacionesUtiles;
import conexion.ConexionHibernate;
import ds.desktop.notify.DesktopNotify;
import entidades.Cliente;
import escritorios.PrincipalCliente;
import formularios.FormularioEditarVenta;
import formularios.FormularioRegistrarVenta;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;

/**
 * @author Hasper Franco
 */
public class OperacionesSecundariasVenta {

    private int tipoFormulario;
    private FormularioRegistrarVenta formularioRegistrarVenta;
    private FormularioEditarVenta formularioEditarVenta;
    private PrincipalCliente principalCliente;

    public PrincipalCliente getPrincipalCliente() {
        return principalCliente;
    }

    public void setPrincipalCliente(PrincipalCliente principalCliente) {
        this.principalCliente = principalCliente;
    }

    public int getTipoFormulario() {
        return tipoFormulario;
    }

    public void setTipoFormulario(int tipoFormulario) {
        this.tipoFormulario = tipoFormulario;
    }

    public FormularioRegistrarVenta getFormularioRegistrarVenta() {
        return formularioRegistrarVenta;
    }

    public void setFormularioRegistrarVenta(FormularioRegistrarVenta formularioRegistrarVenta) {
        this.formularioRegistrarVenta = formularioRegistrarVenta;
    }

    public FormularioEditarVenta getFormularioEditarVenta() {
        return formularioEditarVenta;
    }

    public void setFormularioEditarVenta(FormularioEditarVenta formularioEditarVenta) {
        this.formularioEditarVenta = formularioEditarVenta;
    }

    public void nuevaVentanaCliente() {

    }

    public void tipoVentaSeleccionada(String valor) {
        switch (tipoFormulario) {
            case 1:
                if (valor.equals("Pedido")) {
                    formularioRegistrarVenta.getRadButonConsumidorFinal().setEnabled(false);
                    modeloTabla();
                } else {
                    formularioRegistrarVenta.getRadButonConsumidorFinal().setEnabled(true);
                    modeloTabla();
                }
                break;
            case 2:
                if (valor.equals("Pedido")) {
                    formularioEditarVenta.getRadButonConsumidorFinal().setEnabled(false);
                    modeloTabla();
                } else {
                    formularioEditarVenta.getRadButonConsumidorFinal().setEnabled(true);
                    modeloTabla();
                }
                break;
        }
    }

    public void tipoConsumidorFinalEnabled() {
        switch (tipoFormulario) {
            case 1:
                if (formularioRegistrarVenta.getRadButonConsumidorFinal().isSelected()) {
                    formularioRegistrarVenta.getBtnBuscarCliente().setEnabled(false);
                    modeloTabla();
                    datosventaSimpleConsumidorFinal();
                } else {
                    formularioRegistrarVenta.getBtnBuscarCliente().setEnabled(true);
                    modeloTabla();
                }
                break;
            case 2:
                if (formularioEditarVenta.getRadButonConsumidorFinal().isSelected()) {
                    formularioEditarVenta.getBtnBuscarCliente().setEnabled(false);
                    modeloTabla();
                    datosventaSimpleConsumidorFinal();
                } else {
                    formularioEditarVenta.getBtnBuscarCliente().setEnabled(true);
                    modeloTabla();
                }
                break;
        }

    }

    public void rellenarTablaVentaCliente() {
        int fila = principalCliente.getTablaGrafica().getSelectedRow();
        String nombre = principalCliente.getTablaGrafica().getValueAt(fila, 0).toString() + " " + principalCliente.getTablaGrafica().getValueAt(fila, 1).toString();
        String telefono = principalCliente.getTablaGrafica().getValueAt(fila, 7).toString();
        String direccion = principalCliente.getTablaGrafica().getValueAt(fila, 3).toString() + "/" + principalCliente.getTablaGrafica().getValueAt(fila, 4).toString() + "-" + principalCliente.getTablaGrafica().getValueAt(fila, 5).toString() + "-" + principalCliente.getTablaGrafica().getValueAt(fila, 6).toString();

        switch (tipoFormulario) {
            case 1:
                DefaultTableModel tablaVentaCliente = (DefaultTableModel) formularioRegistrarVenta.getTablaCliente().getModel();
                OperacionesUtiles.removerFilas(tablaVentaCliente);
                Vector datosTabla = new Vector();
                datosTabla.add(nombre);
                datosTabla.add(telefono);
                datosTabla.add(direccion);
                tablaVentaCliente.addRow(datosTabla);
                break;
            case 2:
                DefaultTableModel tablaVentaClienteE = (DefaultTableModel) formularioEditarVenta.getTablaCliente().getModel();
                OperacionesUtiles.removerFilas(tablaVentaClienteE);
                Vector datosTablaE = new Vector();
                datosTablaE.add(nombre);
                datosTablaE.add(telefono);
                datosTablaE.add(direccion);
                tablaVentaClienteE.addRow(datosTablaE);
                break;
        }
    }

    public void datosventaSimpleConsumidorFinal() {
        switch (tipoFormulario) {
            case 1:
                DefaultTableModel tablaVentaCliente = (DefaultTableModel) formularioRegistrarVenta.getTablaCliente().getModel();
                OperacionesUtiles.removerFilas(tablaVentaCliente);
                Vector datosTabla = new Vector();
                datosTabla.add("Cons. Final");
                datosTabla.add("---");
                datosTabla.add("----");
                tablaVentaCliente.addRow(datosTabla);
                break;
            case 2:
                DefaultTableModel tablaVentaClienteE = (DefaultTableModel) formularioEditarVenta.getTablaCliente().getModel();
                OperacionesUtiles.removerFilas(tablaVentaClienteE);
                Vector datosTablaE = new Vector();
                datosTablaE.add("Cons. Final");
                datosTablaE.add("---");
                datosTablaE.add("----");
                tablaVentaClienteE.addRow(datosTablaE);
                break;
        }
    }

    private void modeloTabla() {

        switch (tipoFormulario) {
            case 1:
                Vector datosTabla = new Vector();
                Vector<String> encabezadoTabla = new Vector<>();
                encabezadoTabla.add("NOMBRE");
                encabezadoTabla.add("TELEFONO");
                encabezadoTabla.add("DIRECCION");
                formularioRegistrarVenta.getTablaCliente().setModel(new DefaultTableModel(datosTabla, encabezadoTabla));
                break;
            case 2:
                Vector datosTablaE = new Vector();
                Vector<String> encabezadoTablaE = new Vector<>();
                encabezadoTablaE.add("NOMBRE");
                encabezadoTablaE.add("TELEFONO");
                encabezadoTablaE.add("DIRECCION");
                formularioEditarVenta.getTablaCliente().setModel(new DefaultTableModel(datosTablaE, encabezadoTablaE));
                break;
        }

    }

    public void obtenerPrecioTotal() {

        switch (tipoFormulario) {
            case 1:
                try {
                    Double total = 0.0;
                    for (int i = 0; i < formularioRegistrarVenta.getTablaListarProductos().getRowCount(); i++) {
                        total = total + (Double.valueOf(formularioRegistrarVenta.getTablaListarProductos().getValueAt(i, 2).toString()));
                    }
                    formularioRegistrarVenta.getLblPrecioTotal().setText(new OperacionesUtiles().formatoDouble(total));
                } catch (ArrayIndexOutOfBoundsException e) {
                    formularioRegistrarVenta.getLblPrecioTotal().setText("0.0");
                }
                break;
            case 2:
                try {
                    Double total = 0.0;
                    for (int i = 0; i < formularioEditarVenta.getTablaListarProductos().getRowCount(); i++) {
                        total = total + (Double.valueOf(formularioEditarVenta.getTablaListarProductos().getValueAt(i, 2).toString()));
                    }
                    formularioEditarVenta.getLblPrecioTotal().setText(new OperacionesUtiles().formatoDouble(total));
                } catch (ArrayIndexOutOfBoundsException e) {
                    formularioEditarVenta.getLblPrecioTotal().setText("0.0");
                }
                break;
        }

    }

    public boolean validarListaProductos() {
        switch (tipoFormulario) {
            case 1:
                if (formularioRegistrarVenta.getTablaListarProductos().getRowCount() < 1) {

                    DesktopNotify.showDesktopMessage(" Informacion", " La tabla 'Productos Listados' debe contener almenos un producto", DesktopNotify.INFORMATION, 7000);
                    return false;
                } else {
                    return true;
                }

            case 2:
                if (formularioEditarVenta.getTablaListarProductos().getRowCount() < 1) {
                    DesktopNotify.showDesktopMessage(" Informacion", " La tabla 'Productos Listados' debe contener almenos un producto", DesktopNotify.INFORMATION, 7000);
                    return false;
                } else {
                    return true;
                }
        }
        return false;
    }

    public boolean validarFecha() {

        switch (tipoFormulario) {
            case 1:
                try {
                    if (formularioRegistrarVenta.getrSDateChooser().getDatoFecha().equals(null)
                            || formularioRegistrarVenta.getrSDateChooser().getDatoFecha().equals(" ")) {
                        DesktopNotify.showDesktopMessage(" Informacion", " Debe seleccionar una fecha", DesktopNotify.INFORMATION, 7000);
                        return false;
                    } else {
                        return true;
                    }
                } catch (NullPointerException e) {
                    DesktopNotify.showDesktopMessage(" Informacion", " Debe seleccionar una fecha", DesktopNotify.INFORMATION, 7000);
                    return false;
                }
            case 2:
                try {
                    if (formularioEditarVenta.getrSDateChooser().getDatoFecha().equals(null)
                            || formularioEditarVenta.getrSDateChooser().getDatoFecha().equals(" ")) {
                        DesktopNotify.showDesktopMessage(" Informacion", " Debe seleccionar una fecha", DesktopNotify.INFORMATION, 7000);
                        return false;
                    } else {
                        return true;
                    }
                } catch (NullPointerException e) {
                    DesktopNotify.showDesktopMessage(" Informacion", " Debe seleccionar una fecha", DesktopNotify.INFORMATION, 7000);
                    return false;
                }
        }
        return false;
    }

    public boolean validarTablaCliente() {
        switch (tipoFormulario) {
            case 1:
                if (formularioRegistrarVenta.getTablaCliente().getRowCount() < 1) {
                    DesktopNotify.showDesktopMessage(" Informacion", " Debe seleccionar un cliente o consumidor final", DesktopNotify.INFORMATION, 7000);
                    return false;
                } else {
                    return true;
                }
            case 2:
                if (formularioEditarVenta.getTablaCliente().getRowCount() < 1) {
                    DesktopNotify.showDesktopMessage(" Informacion", " Debe seleccionar un cliente o consumidor final", DesktopNotify.INFORMATION, 7000);
                    return false;
                } else {
                    return true;
                }
        }
        return false;
    }

    public void retornarFormularioVenta() {
        switch (tipoFormulario) {
            case 1:
                formularioRegistrarVenta.setPrincipalCliente(principalCliente);
                formularioRegistrarVenta.setVisible(true);
                formularioRegistrarVenta.toFront();

                break;
            case 2:
                formularioEditarVenta.setPrincipalCliente(principalCliente);
                formularioEditarVenta.setVisible(true);
                formularioEditarVenta.toFront();
                break;
        }
    }

}
