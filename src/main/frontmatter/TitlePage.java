package main.frontmatter;

import main.utils.Utils;
import main.utils.DocumentPrep;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

	/**
	 * This class validates the title page
	 * @author Chidera Biringa
	 * @param PDF file
	 */

public class TitlePage extends DocumentPrep {

	public TitlePage(String DATA_DIR) {
		super(DATA_DIR);
	}

	public String getTitlePage() throws IOException {
		String titlePage = null;

		for (String page : getPDF()) {
			if (StringUtils.containsIgnoreCase(page, Utils.titlePageFlags().get("UN")) &&
				StringUtils.containsIgnoreCase(page, Utils.titlePageFlags().get("SF"))) {
					titlePage = page;
				}
		}
		if (!titlePage.isEmpty() && titlePage != null) {
			for (char c : titlePage.toCharArray()) {
				if (Character.isDigit(c)) {
					String replaced = titlePage.replace(c, ' ');
					titlePage = replaced;
				}
			}
		} else throw new IllegalStateException("Missing Title Page");
		return titlePage;
	}

	public String validateSpelling() throws IOException {
		List<Boolean> validator = new ArrayList<>();
		List<String> titlePage = new ArrayList<>(Arrays.asList(getTitlePage().split(Utils.getRegExPattern().get("Space+"))));
		for (String page : titlePage) {
			boolean found = false;
			for (String word : Utils.getDictionary()) {
				if (page.equalsIgnoreCase(word)) {
					found = true;
					validator.add(found);
					break;
				}
			}
			if (!found) validator.add(false);
		}
		return validator.contains(false) ? Utils.failureMessage() : Utils.successMessage();
	}

	public String validateContent() throws IOException {
		if (StringUtils.containsIgnoreCase(getTitlePage(), Utils.titlePageFlags().get("TP1")) &&
		StringUtils.containsIgnoreCase(getTitlePage(), Utils.titlePageFlags().get("TP2"))) {
			return Utils.successMessage();
		}
		return Utils.failureMessage();
	}

	public String validateTitlePage() throws IOException {
		return validateSpelling() == Utils.successMessage() && validateContent() == Utils.successMessage() ? 
		this.getClass().getSimpleName() + " --> " + Utils.successMessage() : 
		this.getClass().getSimpleName() + " --> " + Utils.failureMessage();
	}
}
