package dev.simplesolution.pdf.service.impl;

import com.lowagie.text.DocumentException;
import dev.simplesolution.pdf.service.PdfGenerateService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${pdf.directory}")
    private String pdfDirectory;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
        	//final File outputFile = File.createTempFile(fileName, ".pdf");
            //PDFEncryption pdfEncryption  = new PDFEncryption();
        	String password= "password@123";
        	//pdfEncryption.setUserPassword(password.getBytes());
            FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
            ITextRenderer renderer = new ITextRenderer();
          
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            //renderer.setPDFEncryption(pdfEncryption);
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
    }
}