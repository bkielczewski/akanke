package eu.kielczewski.akanke.common.service.document;

import eu.kielczewski.akanke.common.domain.Document;
import eu.kielczewski.akanke.common.domain.FacebookStats;
import eu.kielczewski.akanke.common.repository.DocumentRepository;
import eu.kielczewski.akanke.common.service.facebook.FacebookStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
class DocumentFacebookStatsUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentFacebookStatsUpdater.class);
    private final DocumentRepository documentRepository;
    private final FacebookStatsService facebookStatsService;

    @Inject
    public DocumentFacebookStatsUpdater(DocumentRepository documentRepository, FacebookStatsService facebookStatsService) {
        this.documentRepository = documentRepository;
        this.facebookStatsService = facebookStatsService;
    }

    @Scheduled(initialDelay = 3600000, fixedRate = 3600000)
    public void updateAll() {
        LOGGER.debug("Updating Facebook stats in all documents...");
        List<Document> documents = documentRepository.findAll();
        documents.forEach(this::updateDocument);
    }

    private void updateDocument(Document document) {
        LOGGER.trace("Updating Facebook stats of document id={}", document.getId());
        FacebookStats updated = facebookStatsService.get(document.getId());
        FacebookStats current = document.getFacebookStats();
        current.setCommentsboxCount(updated.getCommentsboxCount());
        current.setLikeCount(updated.getLikeCount());
        current.setShareCount(updated.getShareCount());
        documentRepository.save(document);
    }

}
