package main.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

	/**
	 * This class provides helper methods
	 * @author Chidera Biringa
	 * @param PDF file
	 */

public class Utils {

	public static Map<String, String> getRegExPattern() {
		return Map.of(
			"Space+", "\\s+"
			);
	}

	public static Map<String, String> getAuthorsDetails() {
		// TODO -- pass arguments as system inputs
		return Map.of(
			"Name", "Chidera Uzoma Biringa",
			"Degree", "Master of Science",
			"ThesisTitle", "Multi-Dimensional Performance Impact Analysis of Software Updates in Software Development Lifecycle (SDLC)"
			);
	}

	public static String successMessage() {
		return "Success!";
	}
	
	public static String failureMessage() {
		return "Failure!";
	}

	public static Map<String, String> fontsToValidate() {
		return Map.of(
			"Arial", "Arial",
			"TimesNewRoman", "TimesNewRoman",
			"Garamond", "Garamond",
			"Calibri", "Calibri",
			"PDType", "PDType"
			);
	}
	
	public static Map<String, String> titlePageFlags() {
		return Map.of(
			"UN", "University of Massachusetts Dartmouth",
			"SF", "Submitted in Partial Fulfillment",
			"TP1", "A Thesis in",
			"TP2", "By"
			);
	}

	
	public static List<String> getDictionary() throws IOException {
		List<String> dictionary = Files.readAllLines(Paths.get("./dictionary/words.txt"));
		dictionary.addAll(new ArrayList<>(Arrays.asList(getAuthorsDetails().get("Name").split(getRegExPattern().get("Space+")))));
		dictionary.addAll(new ArrayList<>(Arrays.asList(getAuthorsDetails().get("ThesisTitle").split(getRegExPattern().get("Space+")))));
		return dictionary;
	}
}