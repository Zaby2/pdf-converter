package model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FileIn {
    @Id
    @GeneratedValue
    private Long documentId;
    @Column(nullable=false, length=512)
    private String documentName;
    @Column(nullable=false, length=512)
    private String author;
    @Column(nullable=false, length=512)
    private LocalDateTime creationDate;
    @Column(nullable=false, length=10000)
    private byte[] binaryText;
    @Column(nullable=false)
    @Type(type = "text")
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
