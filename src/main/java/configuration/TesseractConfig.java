package configuration;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class TesseractConfig {

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("rus+eng");

        tesseract.setDatapath("C:\\Users\\KK\\IdeaProjects\\pdfEz\\src\\main\\resources\\");
        return tesseract;
    }
}
