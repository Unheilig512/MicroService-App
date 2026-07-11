package News.Service.Services;

import News.Service.DTO.Errors.ErrorCode;
import News.Service.Exceptions.Exceptions_Classes.FileValidationException;
import News.Service.Exceptions.Exceptions_Classes.FileWrongTypeException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Component
public class FileValidationService{

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "application/pdf"
    );

    private static  final Tika tika = new Tika();

    public void validate(MultipartFile file){
        try{

            String detectedType = tika.detect(file.getInputStream());
            if(!ALLOWED_MIME_TYPES.contains(detectedType))
                throw new FileWrongTypeException(ErrorCode.UNSUPPORTED_FILE_TYPE);

        }catch (IOException e){
            throw new FileValidationException(ErrorCode.BAD_REQUEST);
        }
    }
}
