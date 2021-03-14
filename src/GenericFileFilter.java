import java.io.File;

import javax.swing.filechooser.FileFilter;

class GenericFileFilter extends FileFilter {
	String description;
	String extension;

	GenericFileFilter(String paramString1, String paramString2) {
		this.description = paramString1;
		this.extension = paramString2;
	}

	public boolean accept(File paramFile) {
		if (paramFile.isDirectory()) {
			return true;
		}
		return getExtension(paramFile).equals(this.extension);
	}

	public String getDescription() {
		return this.description;
	}

	public static String getExtension(File paramFile) {
		String str1 = "";
		String str2 = paramFile.getName();
		int i = str2.lastIndexOf('.');

		if ((i > 0) && (i < str2.length() - 1)) {
			str1 = str2.substring(i + 1).toLowerCase();
		}
		return str1;
	}
}