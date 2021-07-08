package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.levelp.hospital.dao.FileUploadItemRepository;
import ru.levelp.hospital.model.FileUploadItem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RestController
public class FileShowController {
    @Autowired
    private FileUploadItemRepository items;

//    @GetMapping("/file/show/{id}")
//    @ResponseBody
//    public ResponseEntity<InputStream> showFile(@PathVariable int id) throws SQLException {
//        FileUploadItem file = items.findById(id).orElseThrow();
//        InputStream fileStream = file.getFileContent().getBinaryStream();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "image/png");
//
//        ResponseEntity<InputStream> response = new ResponseEntity<InputStream>(fileStream,
//                headers,
//                200
//        );
//
//        return response;
//    }

    @GetMapping("/file/show/{id}")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> showFile(@PathVariable int id) {
        Optional<FileUploadItem> item = items.findById(id);
        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FileUploadItem file = item.get();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(file.getSize());

        StreamingResponseBody body = outputStream -> {
            try {
                StreamUtils.copy(file.getFileContent().getBinaryStream(), outputStream);
            } catch (SQLException cause) {
                throw new IOException(cause);
            }
        };

        return new ResponseEntity<>(
                body,
                headers,
                200
        );
    }
}
