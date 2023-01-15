package kevinppaulo.studentcrudapi.controller;

import kevinppaulo.studentcrudapi.models.Student;
import kevinppaulo.studentcrudapi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ApiController {

    private final StudentRepository studentRepository;

    @GetMapping("/students/list")
    public List<Student> listAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponse> uploadPhoto(@RequestParam("file") MultipartFile file) {
        // Get the file name
        String fileName = file.getOriginalFilename();

        // Get the file extension
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        // set the filename to a random uuid
        fileName = java.util.UUID.randomUUID().toString() + "." + fileExtension;

        // Save the file to the resources/static folder
        try {
            byte[] bytes = file.getBytes();
            String uploadDir = "/var/uploads/";
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ImageUploadResponse("", e.getMessage()));
        }

        // Return the url of the file
        return ResponseEntity.ok(new ImageUploadResponse("/uploads/" + fileName, ""));
    }

    record ImageUploadResponse(String url, String message){}

}
