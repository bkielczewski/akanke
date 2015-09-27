package eu.kielczewski.akanke.common.service.facebook;

import com.fasterxml.jackson.databind.JsonNode;
import eu.kielczewski.akanke.common.domain.FacebookStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.SocialException;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.util.Collections;

import static com.google.common.base.Preconditions.checkArgument;

@Service
class FacebookStatsServiceImpl implements FacebookStatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookStatsServiceImpl.class);
    private static final String COMMENT_COUNT = "comment_count";
    private static final String SHARE_COUNT = "share_count";
    private final FacebookTemplate facebookTemplate;

    @Value("${akanke.base-url}")
    private String baseUrl;

    @Autowired
    public FacebookStatsServiceImpl(FacebookTemplate facebookTemplate) {
        this.facebookTemplate = facebookTemplate;
    }

    @Override
    @Cacheable("facebook-stats")
    public FacebookStats get(String documentId) {
        LOGGER.debug("Getting facebook stats for document id={}", documentId);
        checkArgument(documentId != null, "Document id cannot be null");
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.put("fields", Collections.singletonList("share"));
            params.put("id", Collections.singletonList(baseUrl + documentId));
            JsonNode ogObjectNode = facebookTemplate.fetchObject("/", JsonNode.class, params);
            JsonNode shareNode = ogObjectNode.get("share");
            if (shareNode == null) {
                return new FacebookStats();
            } else {
                int commentCount = (shareNode.has(COMMENT_COUNT)) ? shareNode.get(COMMENT_COUNT).asInt() : 0;
                int shareCount = (shareNode.has(SHARE_COUNT)) ? shareNode.get(SHARE_COUNT).asInt() : 0;
                return new FacebookStats(commentCount, shareCount);
            }
        } catch (SocialException e) {
            LOGGER.warn("Ignoring exception while fetching stats for document id={} from Facebook", documentId, e);
            return new FacebookStats();
        }
    }

}
