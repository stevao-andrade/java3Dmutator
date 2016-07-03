package usp.icmc.labes.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;

public class Util {

	private static final char PKG_SEPARATOR = '.';

	private static final char DIR_SEPARATOR = '/';

	private static final String CLASS_FILE_SUFFIX = ".class";

	private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	/**
	 * Find all classes inside a given package
	 * 
	 * @param scannedPackage
	 *            target package
	 * @return a list with the name of the classes that exists inside that
	 *         package
	 */
	public static List<Class<?>> find(String scannedPackage) {
		String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if (scannedUrl == null) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
		}

		// File scannedDir = new File(scannedUrl.getFile()); //old way

		// better approach to avoid null pointer exception
		File scannedDir = null;
		try {
			scannedDir = new File(scannedUrl.toURI().getPath());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Class<?>> classes = new ArrayList<Class<?>>();

		if (scannedDir != null) {
			for (File file : scannedDir.listFiles()) {
				System.out.println(file.getAbsolutePath());
				classes.addAll(find(file, scannedPackage));
			}
		}

		return classes;
	}

	private static List<Class<?>> find(File file, String scannedPackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String resource = scannedPackage + PKG_SEPARATOR + file.getName();
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				classes.addAll(find(child, resource));
			}
		} else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
			int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try {
				
				//workaround to remove the inner class inside cube class
				if(!Class.forName(className).toString().contains("InterpolatorData")){
					classes.add(Class.forName(className));
				}
			} catch (ClassNotFoundException ignore) {
			}
		}
		return classes;
	}

	/**
	 * Given a package find all classes inside that directory
	 * 
	 * @param scannedPackage
	 *            target package
	 * @return a list with the name of the classes
	 */
	public static List<String> getClassesName(String scannedPackage) {

		List<Class<?>> classes = Util.find(scannedPackage);

		List<String> fullName = new ArrayList<String>();

		for (Class<?> it : classes) {
			fullName.add(it.toString());
		}

		return fullName;

	}

	/**
	 * Given a package return the directory of that package in the system
	 * 
	 * @param scannedPackage
	 *            target package
	 * @return
	 */
	public static File getDirecotry(String scannedPackage) {
		String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
		URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
		if (scannedUrl == null) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
		}

		// better approach to avoid null pointer exception
		File targetDir = null;
		try {
			targetDir = new File (scannedUrl.toURI().getPath());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return targetDir;

	}

	/**
	 * Copy a source dir to destination
	 * 
	 * @param source
	 *            target directory
	 * @param destination
	 *            destination directory
	 */
	public static void copyDirectory(File source, File destination) {
		if (source.isDirectory()) {
			if (!destination.exists()) {
				destination.mkdirs();
			}

			String files[] = source.list();

			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);

				copyDirectory(srcFile, destFile);
			}
		} else {
			InputStream in = null;
			OutputStream out = null;

			try {
				in = new FileInputStream(source);
				out = new FileOutputStream(destination);

				byte[] buffer = new byte[1024];

				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (Exception e) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	public static Scene loadScene(String location) throws IOException {
        ObjectFile loader = new ObjectFile(ObjectFile.RESIZE); 
        return loader.load(new FileReader(location)); 
}

}
