package main;

import main.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSName;

/**
 * This class provides helper methods
 * 
 * @author Chidera Biringa
 */

public class Style extends Utils {

	public Style(String DATA_DIR) {
		super(DATA_DIR);
	}

	public String validateFontTypes() throws IOException {
		Set<String> fonts = new HashSet<>();
		List<Boolean> validator = new ArrayList<>();
		for (COSName fontName : getPageResources().getFontNames()) {
			fonts.add(getPageResources().getFont(fontName).toString());
		}
		for (String font : fonts) {
			validator.add(StringUtils.containsAny(font.toLowerCase(), fontsToValidate().get("PDType"),
					fontsToValidate().get("arial"), fontsToValidate().get("timesNewRoman"),
					fontsToValidate().get("garamond"), fontsToValidate().get("calibri")));
		}
		return validator.contains(false) ? failureMessage() : successMessage();
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