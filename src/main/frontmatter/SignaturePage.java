package main.frontmatter;

import main.utils.Utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;


/**
 * This class validates the signature page
 * @author Chidera Biringa
 */


public class SignaturePage extends Utils {

	public SignaturePage(String DATA_DIR) {
		super(DATA_DIR);
	}

	public String getTitlePage() throws IOException {
		for (String page : getPDF()) {
			if (StringUtils.containsIgnoreCase(page, getSignaturePageFlags().get("ThesisApproval"))) {
				return page;	
			}
		}
		return null;
	}
}

