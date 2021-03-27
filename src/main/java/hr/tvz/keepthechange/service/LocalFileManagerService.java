package hr.tvz.keepthechange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Simple implementation of {@link FileManagerService} which saves files in a folder inside the project directory.
 */
@Service
public class LocalFileManagerService implements FileManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileManagerService.class);
    private static final Path BASE_PATH = Path.of("fileStorage");

    @Override
    public boolean saveFile(String pathString, byte[] bytes) throws IOException {
        LOGGER.debug("Attempting to save file to pathString {}", pathString);
        Path path = Path.of(pathString);
        if (path.isAbsolute()) {
            throw new IllegalArgumentException("Absolute paths are not allowed");
        }
        Path fullPath = BASE_PATH.resolve(path);
        Files.createDirectories(fullPath.getParent());
        LOGGER.info("Writing {}B file to path {}", bytes.length, fullPath);
        Files.write(fullPath, bytes);
        return true;
    }
}
