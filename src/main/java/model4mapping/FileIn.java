package model4mapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class FileIn {
    private Long documentId;
    private String documentName;
    private String author;
    private LocalDateTime creationDate;
    private byte[] binaryText;
    private String recognizedText;
    private String keyWords;

    public FileIn(Long documentId, String documentName, String author, LocalDateTime creationDate, byte[] binaryText, String recognizedRText, String keyWords) {
        super();
        this.documentId = documentId;
        this.documentName = documentName;
        this.author = author;
        this.creationDate = creationDate;
        this.binaryText = binaryText;
        this.recognizedText = recognizedRText;
        this.keyWords = keyWords;
    }
}