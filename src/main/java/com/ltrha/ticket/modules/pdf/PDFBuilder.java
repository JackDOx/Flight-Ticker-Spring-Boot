package com.ltrha.ticket.modules.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Map;

@RequiredArgsConstructor
public class PDFBuilder {

    private final PDFTemplateProcessor pdfTemplateProcessor;

    public void createPdfFromExistingHtml(String html, OutputStream outputStream) throws IOException, DocumentException {
        // Create a new document

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        // Open the document to write
        document.open();
        // Parse the HTML to the PDF document
        StyleSheet styles = new StyleSheet();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(html));
        // Close the document

    }

    public void createPdfFromExistingHtml(String html, String path) throws IOException, DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        // Open the document to write
        document.open();
        // Parse the HTML to the PDF document
        StyleSheet styles = new StyleSheet();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(html));
        // Close the document

    }


    public void createPdfFromHtml(String htmlTemplate, Map<String, Object> values, String pdfPath) throws
            IOException, DocumentException {
        // Replace placeholders with actual values
        String pdfContent = pdfTemplateProcessor.processTemplate(htmlTemplate, values);

        // Create a new document
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        // Open the document to write
        document.open();
        // Parse the HTML to the PDF document
        StyleSheet styles = new StyleSheet();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(pdfContent));
        // Close the document

    }
}
