package controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model4mapping.FileIn;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.FileService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@ComponentScan("service")
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/v1", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName,
            @RequestParam("author") String author, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        fileService.addFile(file, fileName, author);
      return "redirect:v1";
    }
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public ModelAndView homePage(Model model) {
        List<FileIn> documents = fileService.getFilesFromRepo();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("documents", documents);
        return mav;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deletion(@RequestParam String id) {
        fileService.deleteFile(id);
        return "redirect:v1";
    }
    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void downloadFile(@RequestParam String id, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        File fileInput = fileService.downloadFile(id);
        Path file = Paths.get(fileInput.getAbsolutePath());
        if (Files.exists(file))
        {
            response.setContentType("application/pdf");
            try {
                response.addHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(String.valueOf(file.getFileName()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}