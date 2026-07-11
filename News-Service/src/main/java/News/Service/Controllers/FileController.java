package News.Service.Controllers;

import News.Service.DTO.Errors.ErrorCode;
import News.Service.Exceptions.Exceptions_Classes.FileValidationException;
import News.Service.Services.FileService;
import News.Service.Services.FileValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/postcreate/files")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    FileValidationService fileValidationService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @RequestParam("files") MultipartFile file,
            @RequestHeader("X-User-Id") String userId){
        if(file.isEmpty()){
            throw new FileValidationException(ErrorCode.UNSUPPORTED_FILE_TYPE);
        }
        fileValidationService.validate(file);
        String minioUrl = fileService.uploadFile(file, UUID.fromString(userId));
        return ResponseEntity.ok(minioUrl);
    }
}
