package mio.photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

public class PhotoImport {

	public static void main(String[] args) {
		File dst_dir;
		File org_dir;
		
		if (args[0].equals("ALL")) {
			 
			int count = copyormove(new File("D:\\Dropbox\\Cargas de cámara de Laura"),
					new File("D:\\PHOTO\\cosas_de_casa"),
					true);
			System.out.println("Total: "+ count +"fotos...");
			
			count = copyormove(new File("D:\\Dropbox\\Camera Uploads de Pilar"),
					new File("D:\\PHOTO\\cosas_de_casa"),
					true);
			System.out.println("Total: "+ count +"fotos...");
			
			count = copyormove(new File("D:\\Dropbox\\Camera Uploads"),
					new File("D:\\PHOTO\\cosas_de_casa"),
					true);
			System.out.println("Total: "+ count +"fotos...");
				
		} else {
		
			if (args.length==2) {
				org_dir=new File(args[0]);
				if (!org_dir.exists()) {
					System.err.println("El directorio origen no existe: "+org_dir.getAbsolutePath());
					return;
				}
				dst_dir=new File(args[1]);
				if (!dst_dir.exists()) {
					System.err.println("El directorio destino no existe: "+dst_dir.getAbsolutePath());
					return;
				}
			} else {
				System.err.println("USE: PhotoImport dir_origen dir_destino");
				return;
			}
			
			//si no hemos salido por un return es que tenemos directorio origen y destino
			//y ambos existen
			int count = copyormove(org_dir, dst_dir, false);
			System.out.println("Total: "+ count +"fotos...");

		}
	}
	static int copyormove(File org_dir, File dst_dir, boolean move) {
		File[] photos=org_dir.listFiles();
		int total=0;
		for (int i=0;i<photos.length;i++) {
			if (photos[i].isDirectory()) { 
				//no copiamos subdirectorios...
				continue; 
			}
			String name=photos[i].getName();
			Date fecha= new Date(photos[i].lastModified());
			String dir_fecha=fecha.toString();
			File dst_fecha=new File (dst_dir.getAbsolutePath()+File.separator+dir_fecha);
			if (!name.startsWith(".") && !name.endsWith(".ini") && !name.endsWith(".txt") ) {
				System.out.println("origen: <"+name+ ">: "+ dir_fecha);
				if (!dst_fecha.exists()) {
					boolean created = dst_fecha.mkdirs();
					System.out.println("crear: "+dst_fecha.getAbsolutePath()+ ": "+created);
				}
				
				System.out.println("destino: "+dst_fecha.getAbsolutePath()+File.separator+name);
				
				try {
					if (move) { 
						photos[i].renameTo(new File(dst_fecha.getAbsolutePath()+File.separator+name));
					} else {
						System.out.println(photos[i].exists());
						Files.copy(photos[i].toPath(), new File(dst_fecha.getAbsolutePath()+File.separator+name).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
					}
					total++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		return total;
	}

}
