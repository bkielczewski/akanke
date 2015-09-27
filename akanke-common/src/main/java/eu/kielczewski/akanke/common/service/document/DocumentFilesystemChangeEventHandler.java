package eu.kielczewski.akanke.common.service.document;

import eu.kielczewski.akanke.common.dao.directory.DirectoryWatcherDao;
import eu.kielczewski.akanke.common.domain.Document;
import eu.kielczewski.akanke.common.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

@Component
class DocumentFilesystemChangeEventHandler implements ApplicationListener<DirectoryWatcherDao.FilesystemChangeEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentFilesystemChangeEventHandler.class);
    private static final String EXTENSION_DOCUMENT = "md";
    private final DocumentRepository repository;
    private final DocumentImporter importer;
    private final DocumentCreator creator;

    @Autowired
    public DocumentFilesystemChangeEventHandler(DocumentImporter importer, DocumentCreator creator, DocumentRepository repository) {
        this.importer = importer;
        this.creator = creator;
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(final DirectoryWatcherDao.FilesystemChangeEvent event) {
        LOGGER.debug("Handling {}", event);
        String target = event.getTarget();
        try {
            if (target.endsWith(EXTENSION_DOCUMENT)) {
                handleEventForFile(event, target);
            } else {
                handleEventForDirectory(event, target);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't read file={}", target, e);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while handling {}", event, e);
        }
    }

    private void handleEventForDirectory(DirectoryWatcherDao.FilesystemChangeEvent event, String target) throws IOException {
        if (event.getType() == DirectoryWatcherDao.FilesystemChangeEvent.Type.DELETE) {
            LOGGER.trace("Deleting documents in directory={}", target);
            repository.deleteByFileStartingWith(target);
        }
        if (event.getType() == DirectoryWatcherDao.FilesystemChangeEvent.Type.CREATE) {
            LOGGER.trace("Adding documents from directory={}", target);
            importer.importFrom(target);
        }
    }

    private void handleEventForFile(DirectoryWatcherDao.FilesystemChangeEvent event, String file) throws IOException {
        Document existing = repository.getByFile(file);
        if (existing != null) {
            LOGGER.trace("Deleting existing document id={}", existing.getId());
            repository.delete(existing);
        }
        if (event.getType() == DirectoryWatcherDao.FilesystemChangeEvent.Type.CREATE) {
            LOGGER.trace("Saving new document");
            repository.save(creator.createDocument(file));
        }
        if (event.getType() == DirectoryWatcherDao.FilesystemChangeEvent.Type.MODIFY) {
            LOGGER.trace("Saving updated document");
            repository.save(creator.createDocument(file));
        }
    }

}
