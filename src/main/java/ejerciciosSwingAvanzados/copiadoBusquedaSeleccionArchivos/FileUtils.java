package ejerciciosSwingAvanzados.copiadoBusquedaSeleccionArchivos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileUtils {

	
	/**  
	 * 
	 */
	public static String selectFolder (String currentDirectory, int fileSelectionMode) {
		
		JFileChooser jfcSelectFolder = new JFileChooser();
		
		jfcSelectFolder.setCurrentDirectory(new File(currentDirectory));
		
		// Tipo de selección que se hace en el diálogo
		jfcSelectFolder.setFileSelectionMode(fileSelectionMode); // Sólo selecciona ficheros
		
		// Filtro del tipo de ficheros que puede abrir
		jfcSelectFolder.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Carpetas";
			}
			//Aqui nos mostrará solo las carpetas (si las hay), sino aparecerá vacío
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) 
					return true;
				return false;
			}
		});
		
		// Abro el diálogo para la elección del usuario
		int seleccionUsuario = jfcSelectFolder.showOpenDialog(null);
		
		if (seleccionUsuario == JFileChooser.APPROVE_OPTION) {
			File fichero = jfcSelectFolder.getSelectedFile();
			
			// Vuelco el nombre del fichero sobre el JTextField
			return fichero.getAbsolutePath();			
		}
		
		return "";
	}
	
	
	/**
	 * 
	 * @param nombreCarpeta
	 * @return
	 */
    public static List<File> getFilesIntoFolder(String nombreCarpeta) {
        List<File> archivos = new ArrayList<>();
        File carpeta = new File(nombreCarpeta);
        
        // Verificar si la ruta especificada es una carpeta
        if (!carpeta.isDirectory()) {
            System.err.println("La ruta especificada no es una carpeta.");
            return archivos;
        }
        
        // Obtener todos los archivos dentro de la carpeta
        File[] files = carpeta.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    archivos.add(file);
                }
            }
        } else {
            System.err.println("No se pudieron obtener los archivos de la carpeta especificada.");
        }
        
        return archivos;
    }


}
