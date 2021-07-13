package main;
import main.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSName;

import main.utils.DocumentPrep;

public class OverallStyle extends DocumentPrep {

	public OverallStyle(String DATA_DIR) {
		super(DATA_DIR);
	}

	public String validateFontTypes() throws IOException {
		Set<String> fonts = new HashSet<>();
		List<Boolean> validator = new ArrayList<>();
		for (COSName fontName : getPageResources().getFontNames()) {
			fonts.add(getPageResources().getFont(fontName).toString());
		}
		for (String font : fonts) {
			validator.add(
				StringUtils.containsIgnoreCase(font, Utils.fontsToValidate().get("PDType")) ||
				StringUtils.containsIgnoreCase(font, Utils.fontsToValidate().get("Arial")) ||
				StringUtils.containsIgnoreCase(font, Utils.fontsToValidate().get("TimesNewRoman")) ||
				StringUtils.containsIgnoreCase(font, Utils.fontsToValidate().get("Garamond")) || 
				StringUtils.containsIgnoreCase(font, Utils.fontsToValidate().get("Calibri"))
				);
			}
		return validator.contains(false) ? Utils.failureMessage() : Utils.successMessage();
	}

	public void validateFontSize() { 
		// TODO 
    }

	public void validateLineSpacing() {
		// TODO
	}

	public void validateCaseStyles() {
		// TODO
	}
}