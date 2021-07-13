package main.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;

	/**
	 * This class reads and preps a master's theses or doctoral dissertations document
	 * @author Chidera Biringa
	 * @param PDF file
	 */

public class DocumentPrep {

	public String DATA_DIR;

	public DocumentPrep(String DATA_DIR) {
		this.DATA_DIR = DATA_DIR;
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

	public PDResources getPageResources() throws IOException {
		PDResources resources = null; 
		for (PDPage page : pdfDocument().getPages()) {
			PDResources temp = page.getResources(); 
			resources = temp;
		}
		return resources;
	}
}
