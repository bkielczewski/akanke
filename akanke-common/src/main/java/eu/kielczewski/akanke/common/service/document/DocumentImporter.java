package eu.kielczewski.akanke.common.service.document;

import eu.kielczewski.akanke.common.dao.directory.DirectorySeekerDao;
import eu.kielczewski.akanke.common.dao.directory.DirectoryWatcherDao;
import eu.kielczewski.akanke.common.domain.Document;
import eu.kielczewski.akanke.common.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
class DocumentImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentImporter.class);
    private static final String EXTENSION_DOCUMENT = "md";
    private final String baseDir;
    private final DirectorySeekerDao seekerDao;
    private final DirectoryWatcherDao watcherDao;
    private final DocumentCreator documentCreator;
    private final DocumentRepository repository;
    private final TaskExecutor taskExecutor;

    @Autowired
    public DocumentImporter(@Value("${akanke.documents.path}") String baseDir,
                            DirectorySeekerDao seekerDao,
                            DirectoryWatcherDao watcherDao,
                            DocumentRepository repository,
                            DocumentCreator documentCreator,
                            TaskExecutor taskExecutor) {
        this.baseDir = baseDir;
        this.documentCreator = documentCreator;
        this.seekerDao = seekerDao;
        this.watcherDao = watcherDao;
        this.repository = repository;
        this.taskExecutor = taskExecutor;
    }

    @PostConstruct
    void importAndWatch() throws Exception {
        LOGGER.debug("Starting initial import of documents from directory {}", baseDir);
        taskExecutor.execute(() -> {
            try {
                importFrom(baseDir);
                watcherDao.watch(baseDir);
            } catch (IOException e) {
                LOGGER.error("Couldn't proceed with initial import or watch due to exception", e);
            }
        });
    }

    public void importFrom(String path) throws IOException {
        LOGGER.debug("Importing documents from directory {}", path);
        repository.save(loadDocuments(path));
    }

    private List<Document> loadDocuments(String path) throws IOException {
        return seekerDao.seekByExtension(EXTENSION_DOCUMENT, path).parallelStream()
                .map(documentCreator::createDocument)
                .collect(Collectors.toList());
    }

}
