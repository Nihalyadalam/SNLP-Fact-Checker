package de.upb.snlp.scm.util;

import java.util.List;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class ListUtil {

	public static <T> boolean isNotEmpty(List<T> list) {
		if (list != null) {
			if (!list.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
