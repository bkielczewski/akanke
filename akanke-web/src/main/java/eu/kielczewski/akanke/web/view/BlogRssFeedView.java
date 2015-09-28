package eu.kielczewski.akanke.web.view;

import com.rometools.rome.feed.rss.*;
import eu.kielczewski.akanke.common.domain.Document;
import eu.kielczewski.akanke.common.service.document.DocumentService;
import eu.kielczewski.akanke.web.service.viewhelper.ViewHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("blogRssFeedView")
public class BlogRssFeedView extends AbstractRssFeedView {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogRssFeedView.class);
    private static final String CHANNEL_TITLE = "Kielczewski.eu";
    private static final String CHANNEL_DESCRIPTION = "Software Development & IT Management";
    private static final int NUMBER_OF_ITEMS = 10;
    private final DocumentService documentService;
    private final ViewHelperService viewHelperService;

    @Value("${akanke.base-url}")
    private String baseUrl;

    @Autowired
    public BlogRssFeedView(DocumentService documentService, ViewHelperService viewHelperService) {
        this.documentService = documentService;
        this.viewHelperService = viewHelperService;
    }

    @Override
    protected Channel newFeed() {
        Channel channel = new Channel("rss_2.0");
        channel.setLink(baseUrl + "/feed/");
        channel.setTitle(CHANNEL_TITLE);
        channel.setDescription(CHANNEL_DESCRIPTION);
        documentService.getOneMostRecent().ifPresent(d -> channel.setPubDate(d.getDatePublished()));
        return channel;
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        LOGGER.debug("Building feed items");
        return documentService.getRecent(0, NUMBER_OF_ITEMS).getContent().stream()
                .map(p -> createItem((Document) p))
                .collect(Collectors.toList());
    }

    private Item createItem(Document document) {
        Item item = new Item();
        item.setLink(baseUrl + document.getId());
        item.setTitle(document.getTitle());
        item.setDescription(createDescription(document));
        item.setCategories(createCategories(document));
        item.setPubDate(document.getDatePublished());
        return item;
    }

    private Description createDescription(Document document) {
        Description description = new Description();
        description.setType(Content.HTML);
        description.setValue(viewHelperService.extractExcerpt(document.getContents(), ""));
        return description;
    }

    private List<Category> createCategories(Document document) {
        return document.getTags().stream()
                .map(this::createCategory)
                .collect(Collectors.toList());
    }

    private Category createCategory(String tag) {
        Category category = new Category();
        category.setDomain("http://kielczewski.eu/tag/");
        category.setValue(tag);
        return category;
    }

}