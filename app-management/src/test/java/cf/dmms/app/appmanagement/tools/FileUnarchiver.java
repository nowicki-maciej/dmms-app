package cf.dmms.app.appmanagement.tools;

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

	private static final String DIR_PREFIX = "dmms-unarchived";

	public static List<File> unarchive(File archive) throws IOException {
		File destinationDir = Files.createTempDirectory(DIR_PREFIX).toFile();

		return readFilesFromArchive(archive, destinationDir);
	}

	private static List<File> readFilesFromArchive(File archive, File destinationDir) {
		List<File> out = new ArrayList<>();
		try(ZipInputStream zis = new ZipInputStream(new FileInputStream(archive))) {
			unarchiveFiles(destinationDir, out, zis);
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read files from archive.", e);
		}
		return out;
	}

	private static void unarchiveFiles(File destinationDir, List<File> output, ZipInputStream zipInputStream) throws IOException {
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		byte[] buffer = new byte[1024];
		while (zipEntry != null) {
			File newFile = new File(destinationDir, zipEntry.getName());
			writeBytesToFile(newFile, buffer, zipInputStream);
			output.add(newFile);
			zipEntry = zipInputStream.getNextEntry();
		}
	}

	private static void writeBytesToFile(File file, byte[] buffer, ZipInputStream zipInputStream) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		int length;
		while ((length = zipInputStream.read(buffer)) > 0) {
			fos.write(buffer, 0, length);
		}
		fos.close();
	}
}
