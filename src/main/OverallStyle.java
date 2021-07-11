package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSName;

public class OverallStyle extends Utils {

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
				StringUtils.containsIgnoreCase(font, fontsToValidate().get("PDType")) ||
				StringUtils.containsIgnoreCase(font, fontsToValidate().get("Arial")) ||
				StringUtils.containsIgnoreCase(font, fontsToValidate().get("TimesNewRoman")) ||
				StringUtils.containsIgnoreCase(font, fontsToValidate().get("Garamond")) || 
				StringUtils.containsIgnoreCase(font, fontsToValidate().get("Calibri"))
				);
			}
		return validator.contains(false) ? failureMessage() : successMessage();
	}

	public void validateFontSize() { 
    }

	public void validateLineSpacing() {
	}

	public void validateCaseStyles() {
	}
}