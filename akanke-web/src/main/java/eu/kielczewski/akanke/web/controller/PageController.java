package eu.kielczewski.akanke.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.inject.Inject;
import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
public class PageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Inject
    public PageController(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @RequestMapping("/")
    public String getFrontPage() {
        LOGGER.debug("Getting the front page");
        return "index";
    }

    @RequestMapping("/error/")
    public String getErrorPage() {
        return "error";
    }

    @RequestMapping("/{templateName:.+}/")
    public String getPage(@PathVariable String templateName) throws IOException {
        if (freeMarkerConfigurer.getConfiguration().getTemplateLoader()
                .findTemplateSource(templateName + ".ftl") == null) {
            throw new NoSuchElementException(String.format("Couldn't find template=%s", templateName));
        }
        return templateName;
    }

}
