package operacionesCliente;

import calsesPadre.InterfazGraficaFormularioEditar;
import clasesUtilidadGeneral.TextPrompt;
import conexion.ConexionHibernate;
import entidades.Cliente;
import entidades.Gasto;
import escritorios.PrincipalCliente;
import formularios.FormularioEditarCliente;
import java.awt.HeadlessException;
import java.util.Date;
import org.hibernate.Session;

/**
 * @author Hasper Franco
 */
public class InterfazGraficaFormularioEditarCliente extends InterfazGraficaFormularioEditar {

    public InterfazGraficaFormularioEditarCliente() {
        setEstadoConsulta(0);
    }

    protected PrincipalCliente principalCliente;
    protected TablaCliente tablaCliente;

    public void setPrincipalCliente(PrincipalCliente principalCliente) {
        this.principalCliente = principalCliente;
    }

    public TablaCliente getTablaCliente() {
        return tablaCliente;
    }

    public void setTablaCliente(TablaCliente tablaCliente) {
        this.tablaCliente = tablaCliente;
    }

    @Override
    public void nuevoFormularioEditar() {
        if (principalCliente.getEditarCliente() == null) {
            FormularioEditarCliente formularioEditar = new FormularioEditarCliente(frame, true);
            formularioEditar.setPrincipalCliente(principalCliente);
            principalCliente.setEditarCliente(formularioEditar);
            colorTema();
            agregarBoxes();
            rellenarBoxes();
            estadoRazonSocial();
            infoTextPrompt();
            transferirDatos();
        }
        principalCliente.getEditarCliente().setVisible(true);
        principalCliente.setEditarCliente(null);
    }

    private void estadoRazonSocial() {
        cambiarEstadoRazonSocial(obtenerDatos());
    }

    private Integer obtenerDatos() {
        Integer idRazonSocial = 0;
        Integer id = principalCliente.getTablaCliente().obtenerIdFilaSeleccionada();
        Session miSesion = ConexionHibernate.tomarConexion();
        try {
            miSesion.beginTransaction();
            Cliente c = (Cliente) miSesion.get(Cliente.class, id);
            idRazonSocial = c.getCodigoRazonSocial().getIdRazonSocial();
            miSesion.getTransaction().commit();
        } catch (HeadlessException | NumberFormatException e) {
        }
        return idRazonSocial;
    }

    private void cambiarEstadoRazonSocial(Integer idRazonSocial) {
        if (idRazonSocial.equals(1)) {
            principalCliente.getEditarCliente().getRadioButon().setSelected(false);
            principalCliente.getEditarCliente().getTxtRazonSocial().setEnabled(false);
        } else {
            principalCliente.getEditarCliente().getRadioButon().setSelected(true);
            principalCliente.getEditarCliente().getTxtRazonSocial().setEnabled(true);
        }

    }

    @Override
    public void colorTema() {
        principalCliente.getEditarCliente().getPanelPrincipalTop().setBackground(principalCliente.getPanelPrincipalTop().getBackground());
    }

    @Override
    public void transferirDatos() {
        new TablaCliente().setPrincipalCliente(principalCliente);
        int fila = principalCliente.getTablaGrafica().getSelectedRow();
        principalCliente.getEditarCliente().getTxtNombre().setText(principalCliente.getTablaGrafica().getValueAt(fila, 0).toString());
        principalCliente.getEditarCliente().getTxtApellido().setText(principalCliente.getTablaGrafica().getValueAt(fila, 1).toString());
        principalCliente.getEditarCliente().getTxtRazonSocial().setText(principalCliente.getTablaGrafica().getValueAt(fila, 2).toString());
        principalCliente.getEditarCliente().getTxtDireccion().setText(principalCliente.getTablaGrafica().getValueAt(fila, 3).toString());
        principalCliente.getEditarCliente().getTxtnuemroDireccion().setText(principalCliente.getTablaGrafica().getValueAt(fila, 4).toString());
        principalCliente.getEditarCliente().getTxtTelefono().setText(principalCliente.getTablaGrafica().getValueAt(fila, 7).toString());

        String localidad = principalCliente.getTablaGrafica().getValueAt(fila, 5).toString();
        String provincia = principalCliente.getTablaGrafica().getValueAt(fila, 6).toString();
        String tipotelefono = principalCliente.getTablaGrafica().getValueAt(fila, 8).toString();
        autoSelectBox(provincia, localidad, tipotelefono);
    }

    private void autoSelectBox(String provincia, String localidad, String tipotelefono) {
        principalCliente.getEditarCliente().getBoxProvincia().setSelectedItem(provincia);
        principalCliente.getEditarCliente().getBoxLocalidad().setSelectedItem(localidad);
        principalCliente.getEditarCliente().getBoxTipoTelefono().setSelectedItem(tipotelefono);
    }

    public void infoTextPrompt() {
        new TextPrompt("NOMBRE", principalCliente.getEditarCliente().getTxtNombre());
        new TextPrompt("APELLIDO", principalCliente.getEditarCliente().getTxtApellido());
        new TextPrompt("DIRECCION", principalCliente.getEditarCliente().getTxtDireccion());
        new TextPrompt("RAZON SOCIAL", principalCliente.getEditarCliente().getTxtRazonSocial());
        new TextPrompt("TELEFONO", principalCliente.getEditarCliente().getTxtTelefono());
        new TextPrompt("NUMERO DE DIRECCION", principalCliente.getEditarCliente().getTxtnuemroDireccion());
        principalCliente.getEditarCliente().getTxtNombre().grabFocus();
    }

    @Override
    public void agregarBoxes() {
        this.setBoxLocalidad(principalCliente.getEditarCliente().getBoxLocalidad());
        this.setBoxProvincia(principalCliente.getEditarCliente().getBoxProvincia());
        this.setBoxTipoTelefono(principalCliente.getEditarCliente().getBoxTipoTelefono());
    }

    @Override
    public void rellenarBoxes() {
        this.consultaRellenarProvincia();
        this.consultaRellenarLocalidad();
        this.consultaRellenarTipoTelefono();
    }

}
