package com.flame.util;

import java.util.List;

public class MyUtil {

	public static boolean isInArr(List<Object> arr, Object s) {
		boolean b = false;
		for (Object obj : arr) {
			if (obj.equals(s)) {
				b = true;
				break;
			}
		}

		return b;
	}

}
