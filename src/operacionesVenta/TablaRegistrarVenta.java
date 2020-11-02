package operacionesVenta;

import calsesPadre.Tabla;
import clasesUtilidadGeneral.OperacionesUtiles;
import entidades.PrecioProducto;
import escritorios.PrincipalVenta;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * @author Hasper Franco
 */
public class TablaRegistrarVenta extends Tabla {

    public TablaRegistrarVenta() {
        setEstadoConsulta(0);
    }

    private PrincipalVenta principalVenta;
    //se usa para manejar la id.
    private List<Integer> listaResutladosActuales = new ArrayList<Integer>();

    public PrincipalVenta getPrincipalVenta() {
        return principalVenta;
    }

    public void setPrincipalVenta(PrincipalVenta principalVenta) {
        this.principalVenta = principalVenta;
    }

    public List<Integer> getListaResutladosActuales() {
        return listaResutladosActuales;
    }

    public void setListaResutladosActuales(List<Integer> listaResutladosActuales) {
        this.listaResutladosActuales = listaResutladosActuales;
    }
    
    
    
    
    
    @Override
    public void ejecutarRellenarTabla() {
        setTabla(principalVenta.getRegistrarVenta().getTablaGraficaProductosDisponibles());
        setStringConsulta("from PrecioProducto");
        evaluarEstadoConsulta();
        setCampoTexto(principalVenta.getRegistrarVenta().getTxtBuscar());
        rellenarTabla(getCampoTexto().getText());
    }

    @Override
    public void rellenarTabla(String valorBusqueda) {
        DefaultTableModel tablaProducto = (DefaultTableModel) getTabla().getModel();
        List lista = this.getListaResultados();
        operacionesUtilidad.removerFilas(tablaProducto);
        
        try {
            this.listaResutladosActuales.clear();
        } catch (NullPointerException e) {
        }
         
        for (Object o : lista) {
            PrecioProducto pr = (PrecioProducto) o;
            Vector<Object> fila = new Vector<>();
            boolean resultadoComparacion = OperacionesUtiles.convertirResultado(pr.getCodigoProducto().getNombre(), valorBusqueda);
            if (pr.getCodigoEstado().getIdEstado().equals(1)
                    && pr.getCodigoProducto().getCodigoEstado().getIdEstado().equals(1) && resultadoComparacion) {
                this.listaResutladosActuales.add(0, pr.getCodigoProducto().getIdProducto());
                fila.add(pr.getCodigoProducto().getNombre());
                fila.add(pr.getCodigoProducto().getDescripcion());
                fila.add(pr.getPrecioTotal());
                tablaProducto.addRow(fila);
            }
        }
        OperacionesUtiles.ordenarLista(listaResutladosActuales);
    }

    
    
    @Override
    public Integer obtenerIdFilaSeleccionada() {
         try {
            Integer totalFilas = principalVenta.getRegistrarVenta().getTablaGraficaProductosDisponibles().getRowCount();
            Integer filasSeleccionada = principalVenta.getRegistrarVenta().getTablaGraficaProductosDisponibles().getSelectedRow();
            List<Integer> listaResutadosActualesThis = principalVenta.getRegistrarVenta().getTablaRegistrarVenta().getListaResutladosActuales();
            Integer id = operacionesUtilidad.obtenerId(listaResutadosActualesThis, totalFilas, filasSeleccionada);
            this.setIdTabla(id);
        } catch (Exception e) {
        }
        return idTabla;
    }

    @Deprecated
    @Override
    public boolean verificarFilaSeleccionada() {
        return true;
    }

}
