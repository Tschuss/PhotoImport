package mio.photo;

import java.io.File;
import java.util.Arrays;

public class BorraDirVacios {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length!=1) {
			System.err.println("USE: BorrarDirVacios <dir padre>");
			return;
		}
		File f = new File (args[0]);
		if (!f.exists()) {
			System.err.println("el directorio no existe: "+f.getAbsolutePath());
			return;
		}
		
		if(!f.isDirectory()) {
			System.out.println("no es un directorio: "+f.getAbsolutePath());
			return;
		}
		File ff[] = f.listFiles();
		for (int i=1; i<ff.length;i++) {
			if (!ff[i].isDirectory()) {
				continue;
			}
			if (ff[i].listFiles().length==0) {
				if(ff[i].delete()) {
					System.out.println("borrado: "+ff[i].getAbsolutePath());
				}
			} else {
				System.out.println(ff[i].getName()+ " contiene: "+ Arrays.asList(ff[i].listFiles()));
			}
		}		
	}

}
