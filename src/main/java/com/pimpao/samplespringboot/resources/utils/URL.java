package com.pimpao.samplespringboot.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static String decodeParam(String name) {
		try {
			return URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return "";
		}
	}

	public static List<Integer> decodeIntList(String pathUrl) {
		String[] paths = pathUrl.split(",");
		List<Integer> ids = new ArrayList<>();

		for (int i = 0; i < paths.length; i++) {
			int id = Integer.parseInt(paths[i]);
			ids.add(id);
		}

		return ids;

		// return Arrays.asList(pathUrl.split(",")).stream().map(path ->
		// Integer.parseInt(path)).collect(Collectors.toList());

	}
}
