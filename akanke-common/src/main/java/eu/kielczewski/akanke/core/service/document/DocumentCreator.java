package eu.kielczewski.akanke.core.service.document;

import com.google.common.io.Files;
import eu.kielczewski.akanke.core.dao.file.FileDao;
import eu.kielczewski.akanke.core.domain.Document;
import eu.kielczewski.akanke.core.service.facebook.FacebookStatsService;
import org.pegdown.PegDownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

@Component
public class DocumentCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentCreator.class);
    private final FileDao fileDao;
    private final DocumentPropertyExtractor propertyExtractor;
    private final FacebookStatsService facebookStatsService;
    private final FactoryBean<PegDownProcessor> pegDownProcessor;

    @Inject
    public DocumentCreator(FileDao fileDao, DocumentPropertyExtractor propertyExtractor, FacebookStatsService facebookStatsService, FactoryBean<PegDownProcessor> pegDownProcessor) {
        this.fileDao = fileDao;
        this.propertyExtractor = propertyExtractor;
        this.facebookStatsService = facebookStatsService;
        this.pegDownProcessor = pegDownProcessor;
    }

    public Document createDocument(String file) {
        LOGGER.debug("Creating document from file={}", file);
        checkArgument(file != null, "File cannot be null");
        try {
            //noinspection ConstantConditions
            String contents = fileDao.getContents(file);
            Map<String, String> properties = propertyExtractor.fromContents(contents);
            Date datePublished = chooseDatePublished(properties.get("date"), file);
            String id = generateId(file, datePublished);

            Document document = new Document(
                    id,
                    file,
                    properties.getOrDefault("title", generateTitle(id)),
                    properties.getOrDefault("description", ""),
                    pegDownProcessor.getObject().markdownToHtml(contents),
                    generateTags(properties.get("tags")),
                    datePublished,
                    facebookStatsService.get(id));

            LOGGER.trace("Returning {}", document);
            return document;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create document from file=" + file, e);
        }
    }

    private String generateId(String file, Date datePublished) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datePublished);
        return "/" + cal.get(Calendar.YEAR) +
                "/" + String.format("%02d", cal.get(Calendar.MONTH) + 1) +
                "/" + Files.getNameWithoutExtension(file) +
                "/";
    }

    private String generateTitle(String id) {
        return StringUtils.capitalize(id
                .replaceFirst("/[0-9]+/[0-9]+/", "")
                .replace("-", " ")
                .replaceFirst("/$", ""));
    }

    private Collection<String> generateTags(String tagsString) {
        if (tagsString != null) {
            return Arrays.asList(tagsString.split(",")).stream()
                    .map(String::trim)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private Date chooseDatePublished(String dateString, String file) throws IOException {
        Date fromFile = new Date(fileDao.getCreationTime(file));
        if (dateString != null) {
            try {
                Calendar fromProperty = Calendar.getInstance();
                fromProperty.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(dateString));
                Calendar date = Calendar.getInstance();
                date.setTime(fromFile);
                date.set(Calendar.DAY_OF_MONTH, fromProperty.get(Calendar.DAY_OF_MONTH));
                date.set(Calendar.MONTH, fromProperty.get(Calendar.MONTH));
                date.set(Calendar.YEAR, fromProperty.get(Calendar.YEAR));
                return date.getTime();
            } catch (ParseException e) {
                LOGGER.warn("Date string={} is invalid", dateString, e);
            }
        }
        return fromFile;
    }

}
