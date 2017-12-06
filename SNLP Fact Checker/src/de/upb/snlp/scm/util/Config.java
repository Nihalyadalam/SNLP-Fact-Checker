package de.upb.snlp.scm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Config {

	private static Config instance = null;

	private static final String confFile = "conf.properties";

	private static Properties props = null;

	public static String NER_3_CLASSIFIER;

	public static String NER_4_CLASSIFIER;

	public static String NER_7_CLASSIFIER;

	public static String TEST_FILE;
	
	public static String TEST_FILE_2;

	static {
		props = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(confFile);

			// load a properties file
			props.load(input);

			initProperties();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private Config() {

	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	private static void initProperties() {
		Field[] fields = Config.class.getFields();

		for (Field field : fields) {
			try {
				field.set(Config.getInstance(), props.getProperty(field.getName()));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
