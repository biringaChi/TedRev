package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.NotFoundException;
import org.apache.commons.lang3.StringUtils;

public class FrontMatter extends Utils {

	public FrontMatter(String DATA_DIR) {
		super(DATA_DIR);
	}

	public String getTitlePage() throws IOException {
		String titlePage = null;

		for (String page : getPDF()) {
			if (StringUtils.containsIgnoreCase(page, titlePageFlags().get("UniversityName")) &&
				StringUtils.containsIgnoreCase(page, titlePageFlags().get("SubmissionFlag"))) {
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
		} else throw new NotFoundException("Title page is not found");
		return titlePage;
	}

	public String validateTitlePage() throws IOException {
		List<Boolean> validator = new ArrayList<>();
		List<String> titlePage = new ArrayList<>(Arrays.asList(getTitlePage().split(getRegExPattern().get("Space+"))));
		for (String page : titlePage) {
			boolean found = false;
			for (String word : getDictionary()) {
				if (page.equalsIgnoreCase(word)) {
					found = true;
					validator.add(found);
					break;
				}
			}
			if (!found) validator.add(false);
		}
		return validator.contains(false) ? failureMessage() : successMessage();
	}
}