package hr.tvz.keepthechange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Simple implementation of {@link FileManagerService} which saves files in a folder inside the project directory.
 */
@Service
public class LocalFileManagerService implements FileManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileManagerService.class);
    private static final Path BASE_PATH = Path.of("fileStorage");

    @Override
    public boolean saveFile(String pathString, byte[] bytes) {
        LOGGER.debug("Attempting to save file to pathString {}", pathString);
        Path path = Path.of(pathString);
        if (path.isAbsolute()) {
            throw new IllegalArgumentException("Absolute paths are not allowed");
        }
        Path fullPath = BASE_PATH.resolve(path);
        try {
            Files.createDirectories(fullPath.getParent());
            LOGGER.info("Writing {}B file to path {}", bytes.length, fullPath);
            Files.write(fullPath, bytes);
        } catch (IOException ioException) {
            throw new UncheckedIOException(ioException);
        }
        return true;
    }

    @Override
    public boolean exists(String pathString) {
        Path fullPath = BASE_PATH.resolve(Path.of(pathString));
        return Files.exists(fullPath);
    }

    @Override
    public Resource get(String pathString) {
        Path fullPath = BASE_PATH.resolve(Path.of(pathString));
        return new FileSystemResource(fullPath);
    }

    @Override
    public boolean delete(String pathString) {
        Path fullPath = BASE_PATH.resolve(Path.of(pathString));
        try {
            return Files.deleteIfExists(fullPath);
        } catch (IOException ioException) {
            throw new UncheckedIOException(ioException);
        }
    }

    @Override
    public Stream<String> walkFiles(String pathString) {
        Path fullPath = BASE_PATH.resolve(Path.of(pathString));
        try {
            return Files.walk(fullPath)
                    .filter(Files::isRegularFile)
                    .map(p -> p.subpath(BASE_PATH.getNameCount(), p.getNameCount()))
                    .map(Path::toString);
        } catch (IOException ioException) {
            throw new UncheckedIOException(ioException);
        }
    }
}
