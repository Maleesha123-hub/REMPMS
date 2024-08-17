package com.pdev.rempms.documentservice.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/24
 */
@Slf4j
@Component
public class PdfGenerator {

    /**
     * Pdf Generator
     *
     * @param req,res - required for web context
     * @param object  required data for create pdf
     * @return ByteArrayOutputStream will be returned
     * @author Rannul
     */
    public ByteArrayOutputStream pdfGenerator(HttpServletRequest req,
                                              HttpServletResponse res,
                                              Object object,
                                              String fileName,
                                              TemplateEngine templateEngine,
                                              String font) {
        log.info("PdfGenerator -> pdfGenerator() => started");
        WebContext context = createContext(req, res);
        context.setVariable("dataEntry", object);

        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setOrder(templateEngine.getTemplateResolvers().size());
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateResolver.setCheckExistence(true);
        String orderHtml = templateEngine.process(fileName, context);

        FontProvider fontProvider = new DefaultFontProvider(false, false, false);
        FontProgram fontProgram = null;
        try {
            fontProgram = FontProgramFactory.createFont(font);
        } catch (IOException ex) {
            log.info("PdfGenerator -> pdfGenerator() => failed");
            throw new IllegalArgumentException("Unable to create font   ", ex);

        }
        fontProvider.addFont(fontProgram, "Winansi");
        log.info("PdfGenerator -> pdfGenerator() => converting properties");
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri(req.getRequestURI());
        converterProperties.setFontProvider(fontProvider);

        log.info("PdfGenerator -> pdfGenerator() => converting html to pdf");
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);
        log.info("PdfGenerator -> pdfGenerator() => ended");
        return target;
    }

    public ByteArrayOutputStream multiplePdfGenerator(HttpServletRequest req, HttpServletResponse res, List<Object> objectList, List<String> fileName, TemplateEngine templateEngine, String font) {
        log.info("multiplePdfGenerator -> multiplePdfGenerator() => started");
        WebContext context = createContext(req, res);

        StringBuffer stringBuffer = new StringBuffer("");
        objectList.forEach((object) -> {
            int i = 0;
            context.setVariable("dataEntry", object);
            FileTemplateResolver templateResolver = new FileTemplateResolver();
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            templateResolver.setOrder(templateEngine.getTemplateResolvers().size());
            templateResolver.setCharacterEncoding("UTF-8");
            templateResolver.setCacheable(false);
            templateResolver.setCheckExistence(true);
            String orderHtmlTemp = templateEngine.process(fileName.get(i), context);
            stringBuffer.append(orderHtmlTemp);
            i = i + 1;

        });


        FontProvider fontProvider = new DefaultFontProvider(false, false, false);
        FontProgram fontProgram = null;
        try {
            fontProgram = FontProgramFactory.createFont(font);
        } catch (IOException ex) {
            log.info("multiplePdfGenerator -> multiplePdfGenerator() => failed");
            throw new IllegalArgumentException("Unable to create font   ", ex);

        }
        fontProvider.addFont(fontProgram, "Winansi");
        log.info("multiplePdfGenerator -> multiplePdfGenerator() => converting properties");
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri(req.getRequestURI());
        converterProperties.setFontProvider(fontProvider);

        log.info("multiplePdfGenerator -> multiplePdfGenerator() => converting html to pdf");
        HtmlConverter.convertToPdf(stringBuffer.toString(), target, converterProperties);
        log.info("multiplePdfGenerator -> multiplePdfGenerator() => ended");
        return target;
    }

    /**
     * createContext
     *
     * @param req,res - required for Web Context
     * @return Webcontext will be returned
     * @author Rannul
     */
    public static WebContext createContext(HttpServletRequest req, HttpServletResponse res) {
        log.info("PdfGenerator -> createContext() => started");
        JakartaServletWebApplication jakartaServletWebApplication = JakartaServletWebApplication.buildApplication(req.getServletContext());
        IServletWebExchange iServletWebExchange = jakartaServletWebApplication.buildExchange(req, res);
        log.info("PdfGenerator -> createContext() => ended");
        return new WebContext(iServletWebExchange);
    }
}
