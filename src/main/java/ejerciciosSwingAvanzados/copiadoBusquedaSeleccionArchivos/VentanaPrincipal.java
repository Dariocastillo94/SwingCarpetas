package ejerciciosSwingAvanzados.copiadoBusquedaSeleccionArchivos;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ejerciciosSwingAvanzados.utils.Apariencia;

public class VentanaPrincipal extends JFrame{

	static {
		Apariencia.setAparienciasOrdenadas(Apariencia.aparienciasOrdenadas);
	}
	public VentanaPrincipal() {
		super("Manejo de ficheros - Entrada y salida");
		this.setBounds(0, 0, 500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Establezco el panel principal de la aplicación y la barra de herramientas
		this.setLayout(new BorderLayout());
		this.add(getTabbedPane(), BorderLayout.CENTER);
	}
	private JTabbedPane getTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		//para añadir la pestaña  le damos un titulo y le damos el panel que queremos mostrar a continuación
		tabbedPane.add("Copiado de carpetas",new PanelCopiarCarpetas());
		tabbedPane.add("Buscador de ficheros",new PanelBusquedaDeArchivos());
		tabbedPane.add("Buscador de ficheros por tamaño",new PanelBusquedaPorTamaño());
		
		return tabbedPane;
	}
	public static void main(String[] args) {
		VentanaPrincipal ventana = new VentanaPrincipal();
		ventana.setVisible(true);

	}

}
