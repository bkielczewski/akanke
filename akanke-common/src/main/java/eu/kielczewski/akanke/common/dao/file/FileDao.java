package eu.kielczewski.akanke.common.dao.file;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

@Component
public class FileDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDao.class);

    public String getContents(String file) throws IOException {
        LOGGER.debug("Loading contents of file={}", file);
        return com.google.common.io.Files.toString(new File(file), Charsets.UTF_8);
    }

    public long getCreationTime(String file) throws IOException {
        LOGGER.debug("Getting creation time of file={}", file);
        BasicFileAttributes attributes = Files.readAttributes(Paths.get(file), BasicFileAttributes.class);
        return attributes.creationTime().toMillis();
    }

}
