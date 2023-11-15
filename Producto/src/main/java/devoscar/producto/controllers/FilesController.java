package productos.controllers;

import productos.notFoundException.StorageFileNotFoundException;
import productos.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final StorageService storageService;

    public FilesController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        return ResponseEntity.ok("Archivo '" + file.getOriginalFilename() + "' subido con éxito");
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> handleFileDownload(@PathVariable String filename) {
        Resource resource = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/list")
    public ResponseEntity<String> listAllFiles() {
        return ResponseEntity.ok(
                storageService.loadAll()
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.joining(", "))
        );
    }

    @DeleteMapping("/delete/{filename:.+}")
    public ResponseEntity<String> handleFileDeletion(@PathVariable String filename) {
        storageService.delete(filename);
        return ResponseEntity.ok("Archivo '" + filename + "' eliminado con éxito");
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleStorageFileNotFound(StorageFileNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
