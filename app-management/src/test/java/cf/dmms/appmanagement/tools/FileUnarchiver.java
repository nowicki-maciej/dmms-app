package cf.dmms.appmanagement.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUnarchiver {

	public static List<File> unarchive(File archive) throws IOException {
		List<File> out = new ArrayList<>();
		File destinationDir = Files.createTempDirectory("dmms-unarchived").toFile();

		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(archive));
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			File newFile = new File(destinationDir, zipEntry.getName());
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			out.add(newFile);
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		return out;
	}
}
