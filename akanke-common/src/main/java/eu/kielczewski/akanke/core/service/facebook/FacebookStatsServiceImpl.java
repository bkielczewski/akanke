package eu.kielczewski.akanke.core.service.facebook;

import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;
import eu.kielczewski.akanke.core.domain.FacebookStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
class FacebookStatsServiceImpl implements FacebookStatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookStatsServiceImpl.class);
    private final FacebookClient facebookClient;

    @Value("${akanke.base-url}")
    private String baseUrl;

    @Inject
    public FacebookStatsServiceImpl(FacebookClient facebookClient) {
        this.facebookClient = facebookClient;
    }

    @Override
    @Cacheable("facebook-stats")
    public FacebookStats get(String documentId) {
        LOGGER.debug("Getting facebook stats for document id={}", documentId);
        checkArgument(documentId != null, "Document id cannot be null");
        try {
            List<FacebookStats> result = facebookClient.executeFqlQuery(
                    "SELECT commentsbox_count, share_count, like_count FROM link_stat " +
                            "WHERE url=\"" + baseUrl + documentId + "\"", FacebookStats.class
            );
            if (result == null || result.isEmpty()) {
                return new FacebookStats();
            } else {
                return result.get(0);
            }
        } catch (FacebookException e) {
            LOGGER.warn("Ignoring exception while fetching stats for document id={} from Facebook", documentId, e);
            return new FacebookStats();
        }
    }

}
