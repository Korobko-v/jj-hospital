package ru.levelp.hospital.web;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.levelp.hospital.dao.FileUploadItemRepository;
import ru.levelp.hospital.model.FileUploadItem;

import java.io.IOException;
import java.sql.Blob;

@Controller
public class FileUploadController {
    @Autowired
    private FileUploadItemRepository items;

    @GetMapping("/file/upload")
    public String showFileUploadForm(Model model) {
        model.addAttribute("fileSize", -1L);
        model.addAttribute("fileId", -1L);
        return "fileupload";
    }

    @PostMapping("/file/upload")
    public String handleFile(
            @RequestParam String description,
            @RequestParam MultipartFile file,
            Model model
    ) throws IOException {
        Blob blob = BlobProxy.generateProxy(file.getInputStream(), file.getSize());
        FileUploadItem item = new FileUploadItem();
        item.setName(description);
        item.setSize(file.getSize());
        item.setFileContent(blob);

        items.save(item);

        model.addAttribute("fileSize", file.getSize());
        model.addAttribute("fileId", item.getId());
        return "fileupload";
    }
}