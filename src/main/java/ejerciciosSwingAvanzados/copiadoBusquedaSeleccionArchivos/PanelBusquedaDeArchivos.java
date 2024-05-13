package ejerciciosSwingAvanzados.copiadoBusquedaSeleccionArchivos;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class PanelBusquedaDeArchivos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField jtfSourceFolder;
	private JTextField jtfTextoIntroducido;
	JFileChooser jfcSourceFolder;
	private JScrollPane scrollPane;
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	/**
	 * Create the panel.
	 */
	public PanelBusquedaDeArchivos() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Búsqueda Ficheros");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Carpeta:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		jtfSourceFolder = new JTextField();
		GridBagConstraints gbc_jtfSourceFolder = new GridBagConstraints();
		gbc_jtfSourceFolder.insets = new Insets(0, 0, 5, 5);
		gbc_jtfSourceFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_jtfSourceFolder.gridx = 1;
		gbc_jtfSourceFolder.gridy = 1;
		add(jtfSourceFolder, gbc_jtfSourceFolder);
		jtfSourceFolder.setColumns(10);
		
		JButton btnSourceFolder = new JButton("Seleccionar Carpeta");
		btnSourceFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				jtfSourceFolder.setText(FileUtils.selectFolder("c:\\CarpetaOrigen",JFileChooser.DIRECTORIES_ONLY));
			}
		});
		GridBagConstraints gbc_btnSourceFolder = new GridBagConstraints();
		gbc_btnSourceFolder.insets = new Insets(0, 0, 5, 0);
		gbc_btnSourceFolder.gridx = 2;
		gbc_btnSourceFolder.gridy = 1;
		add(btnSourceFolder, gbc_btnSourceFolder);
		
		JLabel lblNewLabel_2 = new JLabel("Texto en nombre: ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		jtfTextoIntroducido = new JTextField();
		GridBagConstraints gbc_jtfTextoIntroducido = new GridBagConstraints();
		gbc_jtfTextoIntroducido.insets = new Insets(0, 0, 5, 5);
		gbc_jtfTextoIntroducido.fill = GridBagConstraints.HORIZONTAL;
		gbc_jtfTextoIntroducido.gridx = 1;
		gbc_jtfTextoIntroducido.gridy = 2;
		add(jtfTextoIntroducido, gbc_jtfTextoIntroducido);
		jtfTextoIntroducido.setColumns(10);
		
		JButton btnFilter = new JButton("Buscar ficheros:");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findFiles();
			}
		});
		GridBagConstraints gbc_btnFilter = new GridBagConstraints();
		gbc_btnFilter.insets = new Insets(0, 0, 5, 0);
		gbc_btnFilter.gridx = 2;
		gbc_btnFilter.gridy = 2;
		add(btnFilter, gbc_btnFilter);
		
		scrollPane = 	new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);

	}
	
	private void findFiles() {
		//Creamos una lista que tenga todos los archivos de esa carpeta seleccionada
		List<File>files=FileUtils.getFilesIntoFolder(this.jtfSourceFolder.getText());
		//declaramos una lista nueva que estará inicialmente vacia y que será la que contenga los archivos
		//filtrados
		List<File>filteredFiles = new ArrayList<File>();
		//Ahora recorreremos la primera lista para filtrar los archivos y añadirlos en la lista de filtrado
		for (File f : files) {
			//Comprobamos que lo que hemos escrito en el jtf lo contienen los archivos y si es asi los añade a la
			//nueva lista
			if(f.getName().toUpperCase().contains(this.jtfTextoIntroducido.getText().toUpperCase())) {
				filteredFiles.add(f);
			}
		}
		//Aqui vamos a crear un objeto bidimensional que contendra los archivos filtrados, y el numero de columnas
		//que tendrá la tabla que vamos a crear
		Object tableData[][] = new Object[filteredFiles.size()][3];
		//vamos a recorrer los archivos filtrados
		for (int i = 0; i < filteredFiles.size(); i++) {
			//ahora los guardaremos en una variable'f' cada archivo que recorremos y asi podemos acceder a sus 
			//propiedades(nombre,fecha,tamaño)
			File f = filteredFiles.get(i);
			tableData[i][0] = f.getName();
			tableData[i][1] = (f.length()/1024f) +  "KB";
			//para la fecha debemos crear una varible Date para asi obtener su ultima fecha de modificacion.
			Date d = new Date(f.lastModified());
			//Y para mostrarla la formateamos
			tableData[i][2] = sdf.format(d);
		}
		//Por último creamos la tabla que contendra todo lo que hemos filtrado y lo mostrará en pantalla.
		//Le pasaremos el objet tableData y las columnas donde debe colocar los atributos pedidos
		JTable table = new JTable(tableData,new String[]{"Nombre","Tamaño","Última fecha de modificación"});
		//Lo que tenemos en la tabla se lo metemos en el Scrollpane vacio que creamos y asi mostrará la info obtenida
		this.scrollPane.setViewportView(table);
		
	}
	

}
