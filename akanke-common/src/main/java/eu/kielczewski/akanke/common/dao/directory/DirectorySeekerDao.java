package eu.kielczewski.akanke.common.dao.directory;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@Component
public class DirectorySeekerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectorySeekerDao.class);

    @SuppressWarnings("SameParameterValue")
    public List<String> seekByExtension(final String extension, final String baseDir) {
        LOGGER.debug("Searching {} directory for files", baseDir);
        DocumentFinder documentFinder = new DocumentFinder(extension);
        try {
            Files.walkFileTree(Paths.get(baseDir), documentFinder);
        } catch (IOException e) {
            LOGGER.warn("Error while searching directory {} for files", baseDir, e);
        }
        ImmutableList<String> filesFound = ImmutableList.copyOf(documentFinder.filesFound);
        LOGGER.trace("Returning {}", filesFound);
        return filesFound;
    }

    private static final class DocumentFinder implements FileVisitor<Path> {

        private final String extension;
        private final List<String> filesFound = new ArrayList<>();

        private DocumentFinder(final String extension) {
            this.extension = extension;
        }

        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            if (file.toString().endsWith(extension)) {
                LOGGER.trace("Found file {}", file.toString());
                filesFound.add(file.toString());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

}
