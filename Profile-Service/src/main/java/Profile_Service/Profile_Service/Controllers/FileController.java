package Profile_Service.Profile_Service.Controllers;


import Profile_Service.Profile_Service.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/profile/avatar")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("files") MultipartFile file){
        String minioUrl = fileService.uploadFile(file);
        return ResponseEntity.ok(minioUrl);
    }
}
