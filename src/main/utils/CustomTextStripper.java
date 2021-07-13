package main.utils;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class CustomTextStripper extends PDFTextStripper {

    public CustomTextStripper() throws IOException {
    }

    @Override
    public List<List<TextPosition>> getCharactersByArticle() {
        return super.getCharactersByArticle();
    }

    @Override
    public int getCurrentPageNo() {
        return super.getCurrentPageNo();
    }
}
