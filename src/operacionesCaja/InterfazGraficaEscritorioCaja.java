package operacionesCaja;

import calsesPadre.InterfazGraficaEscritorio;
import clasesUtilidadGeneral.TextPrompt;
import escritorios.PrincipalCaja;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 * @author Hasper Franco
 */
public class InterfazGraficaEscritorioCaja extends InterfazGraficaEscritorio{

    @Override
    public void nuevaVentana() {
       if (principalAdministrador.getEscritorio().estacerrado(principalAdministrador.getCaja())) {
            PrincipalCaja principalCaja = new PrincipalCaja();
            principalAdministrador.setCaja(principalCaja);
            BasicInternalFrameTitlePane menupanel = (BasicInternalFrameTitlePane) ((BasicInternalFrameUI) principalCaja.getUI()).getNorthPane();
            int width = principalAdministrador.getEscritorio().getWidth();
            int Height = principalAdministrador.getEscritorio().getHeight();
            principalAdministrador.getCaja().remove(menupanel);
            principalAdministrador.getCaja().setSize(width, Height);
            principalAdministrador.getEscritorio().add(principalAdministrador.getCaja());
            infoTextPrompt();
            TablaCaja tablaCaja = new TablaCaja();
            tablaCaja.setPrincipalCaja(principalAdministrador.getCaja());
            tablaCaja.ejecutarRellenarTabla();
            principalAdministrador.getCaja().setTablaCaja(tablaCaja);
            InterfazGraficaFormularioRegistrarCorte formularioRegistrar = new InterfazGraficaFormularioRegistrarCorte();
            InterfazGraficaFormularioEditarCorte formularioEditar = new InterfazGraficaFormularioEditarCorte();
            principalAdministrador.getCaja().setFormularioRegistrar(formularioRegistrar);
            principalAdministrador.getCaja().setFormularioEditar(formularioEditar);
            principalAdministrador.getCaja().show();
        }
        colorInterfazEscritorio();
        principalAdministrador.getCaja().toFront();
    }

    @Override
    public void infoTextPrompt() {
        principalAdministrador.getCaja().getPanelPrincipalTop().setBackground(principalAdministrador.getPanelPrincipalTop().getBackground());
        principalAdministrador.getCaja().getTablaGrafica().setForeground(principalAdministrador.getPanelPrincipalTop().getBackground());
        principalAdministrador.getCaja().getTablaGrafica().setSelectionBackground(principalAdministrador.getPanelPrincipalTop().getBackground());
    }

    @Override
    public void colorInterfazEscritorio() {
       new TextPrompt("BUSCAR POR NOMBRE", principalAdministrador.getCaja().getTxtBuscar());
        principalAdministrador.getCaja().getTxtBuscar().grabFocus();
    }

}
