package devoscar.Restaurante.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    Resource loadAsResource(String filename);

    String getUrl(String filename);

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    void delete(String filename);

    void deleteAll();
}
