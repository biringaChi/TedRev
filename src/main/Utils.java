package main;

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


public class Utils {
	protected String DATA_DIR;

	public Utils(String DATA_DIR) {
		this.DATA_DIR = DATA_DIR;
	}

	public Map<String, String> getRegExPattern() {
		return Map.of(
			"Space+", "\\s+"
			);
	}

	public Map<String, String> getAuthorsDetails() {
		// TODO -- pass arguments as system inputs
		return Map.of(
			"Name", "Chidera Uzoma Biringa",
			"Degree", "Master of Science",
			"ThesisTitle", "Multi-Dimensional Performance Impact Analysis of Software Updates in Software Development Lifecycle (SDLC)"
			);
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
				String page = stripper.getText(document);
				pdf.add(page);
			}
		}
		return pdf; 
	}

	public String getPDF2() throws IOException {
		StringBuilder pdf =  new StringBuilder();
		try (PDDocument document = PDDocument.load(new File(DATA_DIR))) {
			if (!document.isEncrypted()) {
				CustomTextStripper stripper = new CustomTextStripper();
				String text = stripper.getText(document);
				pdf.append(text);
			} else throw new IOException("PDF is encrypted!");
		}
		return pdf.toString();
	}

	public String successMessage() {
		return "Success!";
	}
	
	public String failureMessage() {
		return "Failure!";
	}

	public Map<String, String> fontsToValidate() {
		return Map.of(
			"Arial", "Arial",
			"TimesNewRoman", "TimesNewRoman",
			"Garamond", "Garamond",
			"Calibri", "Calibri",
			"PDType", "PDType"
			);
	}
	
	public Map<String, String> titlePageFlags() {
		return Map.of(
			"UniversityName", "University of Massachusetts Dartmouth",
			"SubmissionFlag", "Submitted in Partial Fulfillment"
			);
	}
	
	public List<String> getDictionary() throws IOException {
		List<String> dictionary = Files.readAllLines(Paths.get("./dictionary/words.txt"));
		dictionary.addAll(new ArrayList<>(Arrays.asList(getAuthorsDetails().get("Name").split(getRegExPattern().get("Space+")))));
		dictionary.addAll(new ArrayList<>(Arrays.asList(getAuthorsDetails().get("ThesisTitle").split(getRegExPattern().get("Space+")))));
		return dictionary;
	}

	public PDResources getPageResources() throws IOException {
		PDResources resources = null; 
		for (PDPage page : pdfDocument().getPages()) {
			PDResources temp = page.getResources(); 
			resources = temp;
		}
		return resources;
	}
}