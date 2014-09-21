/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iis.persiannormalizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * به نام خدا
 * 
 * @author Mostafa Dehghani
 */
public class UTPersianNormalizer {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException, IOException {
		if (args.length == 0) {
			System.out
					.println("=========================================================");
			System.out.println("               UT-PersianNormalizer-0.1");
			System.out
					.println("=========================================================");
			System.out
					.println("\nTo run the UT-PersianNormalizer from the command line, type the following: \n\n");
			System.out
					.println("java -jar UT-PersianNormalizer.jar sourceDirectory [destinationDirectory]");
			System.out
					.println("\n\nThe source directory should contains the Persian text files in UTF-8 encoding."
							+ "         \nThe corresponding normalized files will be written in destination directory");
			System.out
					.println("---------------------------------------------------------");
			return;
		}
		String srcDir = args[0];
		File inDir = new File(srcDir);
		if (!inDir.exists()) {
			System.out
					.println("Error: The specified source directory could not be found...");
			return;
		}
		int fCount = 0;
		try {
			fCount = inDir.listFiles().length;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (fCount == 0) {
			System.out
					.println("Error: The specified source directory is empty...");
			return;
		}
		String dstDir = "";
		if (args.length == 1) {
			System.out
					.println("Distination directory is not specified, So normalized fils will be stored in source directory");
			dstDir = args[0];
		} else
			dstDir = args[1];

		File outDir = new File(dstDir);
		if (!outDir.exists()) {
			System.out.println("creating distination directory: " + dstDir);
			boolean result = outDir.mkdir();
			if (!result) {
				System.out
						.println("Distination directory could not be created, So normalized fils will be stored in source directory");
			}
		}
		for (int i = 0; i < fCount; i++) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(srcDir + "/"
							+ inDir.listFiles()[i].getName()), "utf-8"));
			String outputPath = dstDir + "/Normalized_"
					+ inDir.listFiles()[i].getName();
			File file = new File(outputPath);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			String str = "";
			while ((str = br.readLine()) != null) {
				str = PersianNormalizerScheme.PersianStringNormalizer(str);
				bw.write(str + System.getProperty("line.separator"));

			}
			bw.close();
			br.close();
		}
		if (fCount == 1)
			System.out
					.println("1 Text file is successfully normalized and stored in: \""
							+ dstDir + "\"");
		if (fCount > 1)
			System.out
					.println(fCount
							+ " Text files are successfully normalized and stored in: \""
							+ dstDir + "\"");
	}
}
