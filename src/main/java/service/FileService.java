package service;

import lombok.SneakyThrows;
import model.FileIn;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.FileRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@ComponentScan("repository")
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    Tesseract tesseract;

    @SneakyThrows// maybe it is useless here
    public void addFile(MultipartFile multipartFile, String fileName, String author) {
        byte[] fileBinaryData = multipartFile.getBytes();
        File inputFile = convertToFile(fileBinaryData);
        String recognizedText = tesseract.doOCR(inputFile);
        FileIn fileIn = new FileIn();
        fileIn.setAuthor(author);
        fileIn.setBinaryText(fileBinaryData);
        fileIn.setDocumentName(fileName);
        fileIn.setKeyWords(findKeyWords(recognizedText));
        fileIn.setCreationDate(LocalDateTime.now());
        fileIn.setRecognizedText(recognizedText);
        fileRepository.saveFile(fileIn);
    }

    public List<FileIn> getFilesFromRepo() {
        return fileRepository.getFiles();
    }
    public void deleteFile(String id) {
        fileRepository.deleteFile(id);
    }
    @SneakyThrows
    public File downloadFile(String id) {
        FileIn fileIn = fileRepository.getFileById(id);
        File file = new File(fileIn.getDocumentName() + ".pdf");
        OutputStream os = new FileOutputStream(file);
        os.write(fileIn.getBinaryText());
        return file;
    }
    private String findKeyWords(String text) {
        text = text.replaceAll("\n", " ");
        String textToWork = text.replaceAll("\\p{Punct}", "");
        List<String> listToParse = List.of((textToWork.split(" ")));
        HashMap<String, Long> data = new HashMap<>();
        for (String s : listToParse) {
            data.put(s, data.getOrDefault(s, 0L) + 1);
        }
        LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();
        data.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        int count = 0;
        String res = "";
        for (Map.Entry<String, Long> entry : sortedMap.entrySet()) {
            if(count != 7) {
               if(entry.getKey().length() >= 5) {
                   res += entry.getKey();
                   res += " ";
                   count++;
               }
            } else {
                break;
            }
        }
        return res;
    }

    @SneakyThrows
    private File convertToFile(byte[] bytes) {
        File file = new File("C:\\Users\\KK\\IdeaProjects\\pdfEz\\src\\main\\resources\\midFile.pdf"); // check path
        OutputStream os = new FileOutputStream(file);
        os.write(bytes);
        return file;
    }


}
