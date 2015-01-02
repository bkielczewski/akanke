package eu.kielczewski.akanke.core.service.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DocumentPropertyExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentPropertyExtractor.class);

    public Map<String, String> fromContents(String content) {
        Pattern p = Pattern.compile("[^\\s]*<!--(.*?)-->", Pattern.DOTALL);
        Matcher m = p.matcher(content);
        Map<String, String> props = new HashMap<>();
        while (m.find()) {
            String commentBlock = m.group(1);
            LOGGER.trace("Extracting properties from comment block: {}", commentBlock);
            props.putAll(extract(commentBlock));
        }
        LOGGER.debug("Extracted properties={}", props);
        return props;
    }

    private Map<String, String> extract(String src) {
        Pattern p = Pattern.compile("\\s*([a-zA-Z0-9_]+)\\s*[=|:]\\s*(.+)\\s*");
        Matcher m = p.matcher(src);
        Map<String, String> props = new HashMap<>();
        while (m.find()) {
            props.put(m.group(1), m.group(2));
        }
        return props;
    }

}
