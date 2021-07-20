package main.frontmatter;

import main.utils.Utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

/**
 * This class validates the title page
 * @author Chidera Biringa
 */

public class TitlePage extends Utils {

	public TitlePage(String DATA_DIR) {
		super(DATA_DIR);
	}

	public String validateContent() throws IOException {
		if (StringUtils.containsIgnoreCase(getTitlePage(), getTitlePageFlags().get("thesis")) &&
		StringUtils.containsIgnoreCase(getTitlePage(), getTitlePageFlags().get("by"))) {
			return successMessage();
		}
		return failureMessage();
	}
	
	public void validateMargins() {
		// TODO
	}

	public String validateTitlePage() throws IOException {
		System.out.println("Validating title page...");
		return validateSpelling(getTitlePage()) == successMessage() && validateContent() == successMessage() ? 
		this.getClass().getSimpleName() + " --> " + successMessage() : 
		this.getClass().getSimpleName() + " --> " + failureMessage();
	}
}
