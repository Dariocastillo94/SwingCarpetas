package ejerciciosSwingAvanzados.copiadoBusquedaSeleccionArchivos;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class PanelBusquedaPorTamaño extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField jtfSouceFolder;
	private JScrollPane scrollPane = new JScrollPane();
	private JSlider jsSelectedSize;
	private JLabel lblInfo = new JLabel("0 > KB");
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy || HH:mm:ss");

	/**
	 * Create the panel.
	 */
	public PanelBusquedaPorTamaño() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Búsqueda de ficheros por tamaño");
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
		
		jtfSouceFolder = new JTextField();
		GridBagConstraints gbc_jtfSouceFolder = new GridBagConstraints();
		gbc_jtfSouceFolder.insets = new Insets(0, 0, 5, 5);
		gbc_jtfSouceFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_jtfSouceFolder.gridx = 1;
		gbc_jtfSouceFolder.gridy = 1;
		add(jtfSouceFolder, gbc_jtfSouceFolder);
		jtfSouceFolder.setColumns(10);
		
		JButton btnNewButton = new JButton("Selecciona carpeta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtfSouceFolder.setText(FileUtils.selectFolder("C:\\Users\\Dario", JFileChooser.DIRECTORIES_ONLY));
				selectFilesBySize();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Tamaño del fichero:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		/////////////////////////////////////////////////////////////////////////////
		//Modificamos el jslider
		jsSelectedSize = new JSlider();
		//lo establecemos a 0
		jsSelectedSize.setValue(0);
		//Al slider le agregamos un changelistener que escucha los cambios en su valor,cuando hay cambios se ejecuta
		//el stateChanged, que actualiza la seleccion de archivos basada en el tamaño
		jsSelectedSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				selectFilesBySize();
			}
		});
		//Aqui establecemos el valor aximo del jslider que es 5
		jsSelectedSize.setMaximum(5);
		
		//Esto configura al slider para que enganche a los valores de las marcas
		jsSelectedSize.setSnapToTicks(true);
		
		//Configura al slider para mostrar las marcas de las divisiones
		jsSelectedSize.setPaintTicks(true);
		
		//Configura al slider para mostrar las etiquetas entre las divisiones
		jsSelectedSize.setPaintLabels(true);
		
		GridBagConstraints gbc_jsSelectedSize = new GridBagConstraints();
		gbc_jsSelectedSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_jsSelectedSize.insets = new Insets(0, 0, 5, 5);
		gbc_jsSelectedSize.gridx = 1;
		gbc_jsSelectedSize.gridy = 2;
		add(jsSelectedSize, gbc_jsSelectedSize);
		
		lblInfo = new JLabel("0 > KB");
		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.insets = new Insets(0, 0, 5, 0);
		gbc_lblInfo.gridx = 2;
		gbc_lblInfo.gridy = 2;
		add(lblInfo, gbc_lblInfo);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);

	}
	
	private void selectFilesBySize() {
		//declaramos una variable int que almacenara el tamaño mínimo de los archivos seleccionados 
		int filesLengSelection = 0;
		//usar switch nos permite controlar el valor actual del control deslizante del JSlider y lo representamos 
		//en 6 rangos
		switch (jsSelectedSize.getValue()) {
		case 0:
			//Cuando el vaor es 0 el lbl actualiza su contenido con el texto que hemos dado a ese rango
			lblInfo.setText(" > 0 KB");
			//Comprobamos el tamaño de los archivos con la siguiente instruccion
			filesLengSelection=0;break;
		case 1:
			lblInfo.setText("> 100 KB");
			filesLengSelection = 100;break;
		case 2:
			lblInfo.setText("> 1 MB");
			filesLengSelection = 1024;break;
		case 3:
			lblInfo.setText("> 10 MB");
			filesLengSelection = 10240;break;
		case 4:
			lblInfo.setText("> 100 MB");
			filesLengSelection = 102400;break;
		case 5:
			lblInfo.setText("> 1 GB");
			filesLengSelection = 1048576;break;
				
		}
		//obtenemos los archivos  y los metemos en una lista
		List<File>files = FileUtils.getFilesIntoFolder(this.jtfSouceFolder.getText());
		//creamos una lista que ira almacenado 
		List<File>filteredFiles = new ArrayList<File>();
		//iteramos sobre la primera lista y los archivos cuyo tamaño sea mayor o igual al tamaño minino seleccionado
		//se agregaran a la nueva lista de filtrados
		for (File f : files) {
			if(f.length()>=(filesLengSelection*1024)) {
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

				JTable table = new JTable(tableData, 
						new String[] {"Nombre", "Tamaño", "Última modificación"});
				//esto actualiza el lbl para mostrar info de los archivos 
				lblInfo.setText(lblInfo.getText()+"("+filteredFiles.size()+" ficheros)");
				//
				this.scrollPane.setViewportView(table);
	}

}
