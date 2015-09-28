package eu.kielczewski.akanke.common.service.document;

import eu.kielczewski.akanke.common.domain.Document;
import eu.kielczewski.akanke.common.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
class DocumentServiceImpl implements DocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private final DocumentRepository repository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Document get(@NotNull String id) {
        LOGGER.debug("Getting document id={}", id);
        Document document = repository.findOne(id);
        if (document == null) {
            throw new NoSuchElementException(String.format("No document with id=%s", id));
        }
        LOGGER.trace("Returning {}", document);
        return document;
    }

    @Override
    public Map<String, Long> getTagCounts() {
        LOGGER.debug("Getting tag counts");
        Map<String, Long> documents = repository.getTagCounts();
        LOGGER.trace("Returning {}", documents);
        return documents;
    }

    @Override
    public Page<Document> getRecentByTag(String tag, int page, int numPerPage) {
        LOGGER.debug("Getting all documents by tag={}", tag);
        Page<Document> documents = repository.findByTags(
                tag, new PageRequest(page, numPerPage, Sort.Direction.DESC, "datePublished"));
        if (documents.getContent().isEmpty()) {
            throw new NoSuchElementException(String.format(
                    "There are no recent documents with tag=%s, on page=%s (%s documents per page)",
                    tag, page, numPerPage));
        }
        LOGGER.trace("Returning {}", documents);
        return documents;
    }

    @Override
    public Page<Document> getRecent(int page, int numPerPage) {
        LOGGER.debug("Getting all recent documents, page={}, numPerPage={}", page, numPerPage);
        Page<Document> documents = repository.findAll(
                new PageRequest(page, numPerPage, Sort.Direction.DESC, "datePublished"));
        if (documents.getContent().isEmpty()) {
            throw new NoSuchElementException("There are no recent documents");
        }
        LOGGER.trace("Returning {}", documents);
        return documents;
    }

    @Override
    public Optional<Document> getOneMostRecent() {
        LOGGER.debug("Getting most recent document");
        Page<Document> documents = repository.findAll(new PageRequest(0, 1, Sort.Direction.DESC, "datePublished"));
        if (documents.getContent().isEmpty()) {
            LOGGER.debug("No documents");
            return Optional.empty();
        } else {
            Document document = documents.getContent().get(0);
            LOGGER.trace("Returning {}", document);
            return Optional.of(document);
        }
    }

    @Override
    public Map<String, Map<String, Long>> getCountInYearMonth() {
        LOGGER.debug("Getting document count in year, month");
        Map<String, Map<String, Long>> count = repository.getCountInYearMonth();
        LOGGER.trace("Returning {}", count);
        return count;
    }

    @Override
    public Page<Document> getRecentByYear(int year, int page, int numPerPage) {
        LOGGER.debug("Getting all recent documents by year={}, page={}, numPerPage={}", year, page, numPerPage);
        Page<Document> documents = repository.findByYear(year,
                new PageRequest(page, numPerPage, Sort.Direction.DESC, "datePublished"));
        if (documents.getContent().isEmpty()) {
            throw new NoSuchElementException(String.format(
                    "There are no recent documents in year=%s, on page=%s (%s documents per page)",
                    year, page, numPerPage));
        }
        LOGGER.trace("Returning {}", documents);
        return documents;
    }

    @Override
    public Map<String, Long> getCountInMonthByYear(int year) {
        LOGGER.debug("Getting document count in month by year={}", year);
        Map<String, Long> count = repository.getCountInMonthByYear(year);
        LOGGER.trace("Returning {}", count);
        return count;
    }

    @Override
    public Page<Document> getRecentByYearMonth(int year, int month, int page, int numPerPage) {
        LOGGER.debug("Getting all recent documents by year={}, month={}", year, month);
        Page<Document> documents = repository.findByYearAndMonth(
                year, month, new PageRequest(page, numPerPage, Sort.Direction.DESC, "datePublished"));
        if (documents.getContent().isEmpty()) {
            throw new NoSuchElementException(String.format(
                    "There are no recent documents in year=%s, month=%s, on page=%s (%s documents per page)",
                    year, month, page, numPerPage));
        }
        LOGGER.trace("Returning {}", documents);
        return documents;
    }

    @Override
    public long getCountByYearMonth(final int year, final int month) {
        LOGGER.debug("Getting document count by year={}, month={}", year, month);
        long count = repository.getCountByYearMonth(year, month);
        LOGGER.trace("Returning {}", count);
        return count;
    }

    @Override
    public List<Document> getMostPopular(int count) {
        LOGGER.debug("Getting most popular documents");
        List<Document> mostPopular = repository.getMostPopular(new PageRequest(0, count));
        LOGGER.trace("Returning {}", mostPopular);
        return mostPopular;
    }

}
