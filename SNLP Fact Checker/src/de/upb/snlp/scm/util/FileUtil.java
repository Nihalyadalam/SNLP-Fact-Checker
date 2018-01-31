package de.upb.snlp.scm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.upb.snlp.scm.model.Input;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class FileUtil {
	/**
	 * for reading file content as string
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		byte[] encoded = null;
		String content = null;
		try {
			encoded = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			content = new String(encoded, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * for string content to file
	 * 
	 * @param filePath
	 * @param content
	 */
	public static void writeToFile(String filePath, String content) {
		PrintWriter writer;
		try {
			File output = new File(filePath);
			writer = new PrintWriter(output);
			writer.print(content);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Reads TSV file format to a list of Input objects
	 * 
	 * @param filePath
	 * @return
	 * @see Input
	 */
	public static List<Input> readTSV(String filePath) {
		List<Input> inputs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));) {
			String line;
			int lineCount = 0;
			while ((line = br.readLine()) != null) {
				if (lineCount != 0) {
					String[] tabs = line.split("\t");
					long id = Long.valueOf(tabs[0]);
					String sentence = tabs[1];
					double value = 0;
					try {
						value = Double.valueOf(tabs[2]);
						value = value == 0.0 ? -1.0 : value;
					} catch (ArrayIndexOutOfBoundsException e) {
						// test file
					}
					inputs.add(new Input(id, sentence, value));
				}
				lineCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputs;
	}

	public static String createTTLLine(String id, double value) {
		StringBuilder str = new StringBuilder();
		str.append("<http://swc2017.aksw.org/task2/dataset/").append(id).append(">")
				.append("<http://swc2017.aksw.org/hasTruthValue>\"").append(value)
				.append("\"^^<http://www.w3.org/2001/XMLSchema#double> .").append("\n");

		return str.toString();
	}
}