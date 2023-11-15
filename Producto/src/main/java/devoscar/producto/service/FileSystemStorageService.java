package productos.service;
import productos.controllers.FilesController;
import productos.notFoundException.StorageFileNotFoundException;
import productos.storageException.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService<StorageProperties> implements StorageService {

    private final Path storageLocation;

    public FileSystemStorageService(StorageProperties storageProperties) {
        this.storageLocation = Paths.get(storageProperties.toString());
    }


    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("No se puede leer el archivo: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("No se puede leer el archivo: " + filename, e);
        }
    }

    @Override
    public String getUrl(String filename) {
        return MvcUriComponentsBuilder
                .fromMethodName(FilesController.class, "serveFile", filename, null)
                .build().toUriString();
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(storageLocation);
        } catch (IOException e) {
            throw new StorageException("No se pudo inicializar el almacenamiento", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("No se puede almacenar un archivo vacío");
            }
            Path destinationFile = this.storageLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(storageLocation.toAbsolutePath())) {
                throw new StorageException(
                        "No se puede almacenar el archivo fuera del directorio actual.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("No se pudo almacenar el archivo", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageLocation, 1)
                    .filter(path -> !path.equals(this.storageLocation))
                    .map(this.storageLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("No se pudieron cargar los archivos almacenados", e);
        }
    }

    @Override
    public Path load(String filename) {
        return storageLocation.resolve(filename);
    }
    @Override
    public void delete(String filename) {
        try {
            Path file = load(filename);
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("No se pudo eliminar el archivo: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(storageLocation.toFile());
    }
}
