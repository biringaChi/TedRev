package main.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;

/**
 * This class provides helper methods
 * 
 * @author Chidera Biringa
 */

public class Utils {

	public String DATA_DIR;

	public Utils(String DATA_DIR) {
		this.DATA_DIR = DATA_DIR;
	}

	public Map<String, String> getRegExPattern() {
		return Map.of("space+", "\\s+", "digit", "\\d");
	}

	public String successMessage() {
		return "Success!";
	}

	public String failureMessage() {
		return "Failure!";
	}

	public Map<String, String> fontsToValidate() {
		return Map.of("arial", "Arial".toLowerCase(), "timesNewRoman", "TimesNewRoman".toLowerCase(), "garamond",
				"Garamond".toLowerCase(), "calibri", "Calibri".toLowerCase(), "PDType", "PDType".toLowerCase());
	}

	public PDDocument pdfDocument() throws IOException {
		try (PDDocument document = PDDocument.load(new File(DATA_DIR))) {
			return document;
		}
	}

	public List<String> getPDF() throws IOException {
		List<String> pdf = new ArrayList<>();
		CustomTextStripper stripper = new CustomTextStripper();

		try (PDDocument document = PDDocument.load(new File(DATA_DIR))) {
			for (int i = 1; i <= document.getNumberOfPages(); i++) {
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				stripper.getCharactersByArticle();
				String page = stripper.getText(document);
				pdf.add(page);
			}
		}

		return pdf;
	}

	public String getPDF2() throws IOException {
		StringBuilder pdf = new StringBuilder();
		try (PDDocument document = PDDocument.load(new File(DATA_DIR))) {
			if (!document.isEncrypted()) {
				CustomTextStripper stripper = new CustomTextStripper();
				String text = stripper.getText(document);
				pdf.append(text);
			} else
				throw new IOException("PDF is encrypted!");
		}
		return pdf.toString();
	}

	public PDResources getPageResources() throws IOException {
		PDResources resources = null;
		for (PDPage page : pdfDocument().getPages()) {
			PDResources temp = page.getResources();
			resources = temp;
		}
		return resources;
	}

	public String getTitlePage() throws IOException {
		for (String page : getPDF()) {
			if (StringUtils.containsIgnoreCase(page, getTitlePageFlags().get("collegeName"))
					&& StringUtils.containsIgnoreCase(page, getTitlePageFlags().get("fulfillment"))) {
				return page;
			}
		}
		throw new IllegalStateException("Missing Title Page");
	}

	public Map<String, String> getTitlePageFlags() {
		return Map.of("college", "College", "department", "Department", "collegeName",
				"University of Massachusetts Dartmouth", "fulfillment", "Submitted in Partial Fulfillment", "thesis",
				"A Thesis in", "dissertation", "A Dissertation in", "by", "by", "Master's", "Master of Science"
		// "PhD", "Doctor of Philosophy"
		);
	}

	public Map<String, String> getSignaturePageFlags() throws IOException {
		return Map.of("ThesisApproval", "We approve the thesis of ".concat(getStudentDetails().get("Name")));
	}

	public Map<String, String> getStudentDetails() throws IOException {
		List<String> titlePage = new ArrayList<>(
				Arrays.asList(getTitlePage().split(System.getProperty("line.separator"))));
		String date = titlePage.get(titlePage.size() - 1);
		String department = "";
		String thesisDissertation = "";

		for (String studentDetails : titlePage) {
			if (StringUtils.startsWithAny(studentDetails, getTitlePageFlags().get("college"),
					getTitlePageFlags().get("department"))) {
				department = department.concat(studentDetails);
			}
			if (StringUtils.startsWithAny(studentDetails, getTitlePageFlags().get("thesis"),
					getTitlePageFlags().get("dissertation"))) {
				thesisDissertation = thesisDissertation.concat(studentDetails);
			}
		}

		String degree = getTitlePage().contains(getTitlePageFlags().get("Master's")) ? "Master's" : "PhD";
		String title = getTitlePage().substring(getTitlePage().indexOf(department) + department.length(),
				getTitlePage().indexOf(getTitlePageFlags().get("thesis")));
		String major = getTitlePage().substring(
				getTitlePage().indexOf(thesisDissertation) + thesisDissertation.length(),
				getTitlePage().indexOf(getTitlePageFlags().get("by")));
		String name = getTitlePage().substring(
				getTitlePage().indexOf(getTitlePageFlags().get("by")) + getTitlePageFlags().get("by").length(),
				getTitlePage().indexOf(getTitlePageFlags().get("fulfillment")));

		return Map.of("name", name.trim(), "title", title.trim(), "major", major.trim(), "degree", degree.trim(),
				"date", date.trim());
	}

	public List<String> getDictionary() throws IOException {
		List<String> dictionary = Files.readAllLines(Paths.get("./dictionary/words.txt"));
		dictionary.addAll(new ArrayList<>(
				Arrays.asList(getStudentDetails().get("studentName").split(getRegExPattern().get("space+")))));
		dictionary.addAll(new ArrayList<>(
				Arrays.asList(getStudentDetails().get("title").split(getRegExPattern().get("space+")))));
		return dictionary;
	}

	public String validateSpelling(String page) throws IOException {
		List<Boolean> validator = new ArrayList<>();
		String tempPage = page.replaceAll(getRegExPattern().get("digit"), "");

		if (!tempPage.isEmpty() && tempPage != null) {
			List<String> tempPagewords = new ArrayList<>(
					Arrays.asList(tempPage.split(getRegExPattern().get("space+"))));
			for (String pageWord : tempPagewords) {
				boolean found = false;
				for (String word : getDictionary()) {
					if (pageWord.equalsIgnoreCase(word)) {
						found = true;
						validator.add(found);
						break;
					}
				}
				if (!found)
					validator.add(false);
			}
		} else
			throw new IllegalArgumentException();

		return validator.contains(false) ? failureMessage() : successMessage();
	}
}
