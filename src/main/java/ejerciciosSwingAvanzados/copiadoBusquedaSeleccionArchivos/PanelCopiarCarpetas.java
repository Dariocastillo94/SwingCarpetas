package ejerciciosSwingAvanzados.copiadoBusquedaSeleccionArchivos;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelCopiarCarpetas extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField jtfSourceFolder;
	private JTextField jtfTargetFolder;
	JProgressBar progressBar;
	JFileChooser jfcSourceFolder;
	JFileChooser jfcTargetFolder;

	/**
	 * Create the panel.
	 */
	public PanelCopiarCarpetas() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Copiado de carpetas");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Carpeta origen: ");
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
		
		JButton btnSourceFolder = new JButton("Seleccionar carpeta");
		btnSourceFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Seleccionamos la ruta de la carpeta
				jtfSourceFolder.setText(FileUtils.selectFolder("C:\\CarpetaOrigen", JFileChooser.DIRECTORIES_ONLY));
			}
		});
		GridBagConstraints gbc_btnSourceFolder = new GridBagConstraints();
		gbc_btnSourceFolder.insets = new Insets(0, 0, 5, 0);
		gbc_btnSourceFolder.gridx = 2;
		gbc_btnSourceFolder.gridy = 1;
		add(btnSourceFolder, gbc_btnSourceFolder);
		
		JLabel lblNewLabel_2 = new JLabel("Carpeta destino:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		jtfTargetFolder = new JTextField();
		GridBagConstraints gbc_jtfTargetFolder = new GridBagConstraints();
		gbc_jtfTargetFolder.insets = new Insets(0, 0, 5, 5);
		gbc_jtfTargetFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_jtfTargetFolder.gridx = 1;
		gbc_jtfTargetFolder.gridy = 2;
		add(jtfTargetFolder, gbc_jtfTargetFolder);
		jtfTargetFolder.setColumns(10);
		
		JButton btnTargetFolder = new JButton("Seleccionar carpeta");
		btnTargetFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//repetimos el mismo proeso que antes para seleccionar una carpeta
				jtfTargetFolder.setText(FileUtils.selectFolder("C:\\", JFileChooser.FILES_AND_DIRECTORIES));
			}
		});
		GridBagConstraints gbc_btnTargetFolder = new GridBagConstraints();
		gbc_btnTargetFolder.insets = new Insets(0, 0, 5, 0);
		gbc_btnTargetFolder.gridx = 2;
		gbc_btnTargetFolder.gridy = 2;
		add(btnTargetFolder, gbc_btnTargetFolder);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridwidth = 3;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 3;
		add(progressBar, gbc_progressBar);
		
		JButton btnCopiar = new JButton("Copiar");
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//llamamos al metodo que hemos creado de copiar
				copyFilesToFolder(FileUtils.getFilesIntoFolder(jtfSourceFolder.getText()), jtfTargetFolder.getText());
			}
		});
		GridBagConstraints gbc_btnCopiar = new GridBagConstraints();
		gbc_btnCopiar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCopiar.gridx = 1;
		gbc_btnCopiar.gridy = 4;
		add(btnCopiar, gbc_btnCopiar);

	}
	/**
	 * Método que copia cada archivo de una ubicación de origen a una ubicación de destino
	 * dentro de una carpeta específica 
	 * @param files
	 * @param targetFolder
	 */
	private void copyFilesToFolder (List<File> files, String targetFolder) {
        //creamos una carpeta que será el destino de las copias
        File carpeta = new File(targetFolder);
        // Verificar si la carpeta de destino existe y si es una carpeta
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.err.println("La carpeta de destino no existe o no es válida.");
            return;
        }
        //Dentro de este bucle iteramos o recorremos sobre la lista  de archivos que tenemos
        for (int i = 0; i < files.size(); i++) {
        	//obtenemos el archivo con (i)
        	File file = files.get(i);
        	//obtenemos la ubicacion del archivo
            Path origenPath = file.toPath();
            //Ahora creamos un nuevo objeto path que será el destino de los archivos que vamos a copiar
            //utilizando la carpeta destino "carpeta" y el nombre del archivo "file.getname"
            Path destinoPath = new File(carpeta, file.getName()).toPath();

            try {
            	//ahora se realiza la copia del archivo desde la ubicacion de origen a la de destino utilizando
            	//el metodo estático 'copy' de la clase Files
            	//[StandardCopyOption.REPLACE_EXISTING] se utiliza para reemplazar archivos que ya existan en la
            	//carpeta de destino
                Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
                
                //Se calcula el progreso de la copia del archivo como un porcentaje y se lo pasamos a un progressbar
            	int progress = Math.round((i + 1) / ((float) files.size()) * 100);
                progressBar.setValue(progress);
                
                //Por ultimo, mostramos en consola un mensaje que muestre el nombre de los archivos y donde se han
                //copiado
                System.out.println("Se ha copiado el archivo " + file.getName() + " a " + targetFolder + " - " + progress + "%");
            } catch (IOException e) {
            	//Si falla algo nos mostrara un mensaje y el nombre del archivo que fallo al copiar
                System.err.println("Error al copiar el archivo " + file.getName() + ": " + e.getMessage());
            }
        }
    }
}
