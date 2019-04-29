package cf.dmms.appmanagement.tools;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FilesArchiver {

	public static File zipFiles(List<File> files) throws IOException {
		File output = File.createTempFile("DMMS-logs", ".zip");
		ZipOutputStream zipOut = zipOutputStream(output);
		files.forEach(file -> addToZip(zipOut, file));
		zipOut.close();
		return output;
	}

	private static void addToZip(ZipOutputStream zipOutputStream, File file) {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zipOutputStream.putNextEntry(zipEntry);
			writeBytes(fileInputStream, zipOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeBytes(FileInputStream fileInputStream, ZipOutputStream zipOutputStream) throws IOException {
		byte[] bytes = new byte[1024];
		int length = fileInputStream.read(bytes);
		while (length >= 0) {
			zipOutputStream.write(bytes, 0, length);
			length = fileInputStream.read(bytes);
		}
	}

	private static ZipOutputStream zipOutputStream(File file) throws FileNotFoundException {
		return new ZipOutputStream(new FileOutputStream(file));
	}
}
