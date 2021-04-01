package hr.tvz.keepthechange.service;

import org.springframework.core.io.Resource;

import java.io.UncheckedIOException;
import java.util.stream.Stream;

/**
 * Contains SPI for working with file resources used in application.
 * Where and how files are stored depends on implementations.
 */
public interface FileManagerService {
    /**
     * Saves file given in a byte array on the given path to a file platform.
     *
     * @param pathString relative path where the file is stored
     * @param bytes      file bytes
     * @return {@code true} if a file was saved, {@code false} otherwise
     * @throws UncheckedIOException if something goes wrong with saving the file
     */
    boolean saveFile(String pathString, byte[] bytes);

    /**
     * Checks if a file exists.
     *
     * @param pathString relative path of the file for whose existence we are performing the check
     * @return {@code true} if it exists, {@code false} otherwise
     */
    boolean exists(String pathString);

    /**
     * Returns an implementation of {@link Resource} containing the file on a given path.
     * Make sure file {@link #exists(String)} before attempting to retrieve it.
     *
     * @param pathString relative path of the file we are trying to get
     * @return {@link Resource} containing the file
     */
    Resource get(String pathString);

    /**
     * Deletes a file or directory at a given path.
     *
     * @param pathString relative path of the file or directory
     * @return {@code true} if it was deleted, {@code false} otherwise
     */
    boolean delete(String pathString);

    /**
     * Returns a stream consisting of paths of files inside a given path and its subdirectories.
     *
     * @param pathString relative path of a directory
     * @return {@link Stream} of relative file paths
     */
    Stream<String> walkFiles(String pathString);
}
