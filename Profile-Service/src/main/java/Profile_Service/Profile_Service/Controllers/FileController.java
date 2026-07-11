package Profile_Service.Profile_Service.Controllers;


import Profile_Service.Profile_Service.Services.FileService;
import Profile_Service.Profile_Service.Services.FileValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/profile/avatar")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    FileValidationService fileValidationService;

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @RequestParam("files") MultipartFile file,
            @RequestHeader("X-User-Id") String userId,
            @RequestBody() String requestUserId){
        if(!userId.equals(requestUserId)){
            return ResponseEntity.status(403).body("User ID is invalid");
        }
        fileValidationService.validate(file);
        String minioUrl = fileService.uploadFile(file, userId);
        return ResponseEntity.ok(minioUrl);
    }
}
