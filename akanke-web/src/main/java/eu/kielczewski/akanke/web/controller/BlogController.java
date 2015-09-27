package eu.kielczewski.akanke.web.controller;

import eu.kielczewski.akanke.common.service.document.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
public class BlogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);
    private static final int NUM_PER_PAGE = 5;
    private final DocumentService documentService;

    @Autowired
    public BlogController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping("/blog/")
    public ModelAndView getBlog() {
        LOGGER.debug("Getting the blog");
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecent(0, NUM_PER_PAGE));
        model.addAttribute("count", documentService.getCountInYearMonth());
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("blog", model);
    }

    @RequestMapping("/blog/page/{page:[0-9]+}/")
    public ModelAndView getBlogPage(@PathVariable int page) {
        LOGGER.debug("Getting the blog, page={}", page);
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecent(page - 1, NUM_PER_PAGE));
        model.addAttribute("count", documentService.getCountInYearMonth());
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("blog", model);
    }

    @RequestMapping("/{year:[0-9]+}/{month:[0-9]+}/{title}/")
    public ModelAndView getDocument(@PathVariable String year, @PathVariable String month, @PathVariable String title) {
        ModelMap model = new ModelMap();
        model.addAttribute("post", documentService.get("/" + year + "/" + month + "/" + title + "/"));
        return new ModelAndView("post", model);
    }

    @RequestMapping("/{year:[0-9]+}/")
    public ModelAndView getArchiveByYear(@PathVariable int year) {
        LOGGER.debug("Getting the archive, year={}", year);
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecentByYear(year, 0, NUM_PER_PAGE));
        model.addAttribute("count", documentService.getCountInMonthByYear(year));
        model.addAttribute("year", String.valueOf(year));
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("archive_year", model);
    }

    @RequestMapping("/{year:[0-9]+}/page/{page:[0-9]+}/")
    public ModelAndView getArchiveByYearPage(@PathVariable int year, @PathVariable int page) {
        LOGGER.debug("Getting the archive, year={}, page={}", year, page);
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecentByYear(year, page - 1, NUM_PER_PAGE));
        model.addAttribute("count", documentService.getCountInMonthByYear(year));
        model.addAttribute("year", String.valueOf(year));
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("archive_year", model);
    }

    @RequestMapping("/{year:[0-9]+}/{month:[0-9]+}/")
    public ModelAndView getArchiveByYearMonth(@PathVariable int year,
                                              @Valid @Min(1) @Max(12) @PathVariable int month) {
        LOGGER.debug("Getting the archive, year={}, month={}", year, month);
        // should not be needed in 4.1+
        if (month < 1 || month > 12) {
            return new ModelAndView("error", null);
        }
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecentByYearMonth(year, month, 0, NUM_PER_PAGE));
        model.addAttribute("count", documentService.getCountByYearMonth(year, month));
        model.addAttribute("year", String.valueOf(year));
        model.addAttribute("month", String.format("%02d", month));
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("archive_year_month", model);
    }

    @RequestMapping("/{year:[0-9]+}/{month:[0-9]+}/page/{page:[0-9]+}")
    public ModelAndView getArchiveByYearMonth(@PathVariable int year,
                                              @Valid @Min(1) @Max(12) @PathVariable int month,
                                              @PathVariable int page) {
        LOGGER.debug("Getting the archive, year={}, month={}, page={}", year, month, page);
        // should not be needed in 4.1+
        if (month < 1 || month > 12) {
            return new ModelAndView("error", null);
        }
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecentByYearMonth(year, month, page - 1, NUM_PER_PAGE));
        model.addAttribute("count", documentService.getCountByYearMonth(year, month));
        model.addAttribute("year", String.valueOf(year));
        model.addAttribute("month", String.format("%02d", month));
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("archive_year_month", model);
    }

    @RequestMapping("/tag/{tag}/")
    public ModelAndView getArchiveByTag(@PathVariable String tag) {
        LOGGER.debug("Getting the archive by tag={}", tag);
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecentByTag(tag, 0, NUM_PER_PAGE));
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("archive_tag", model);
    }

    @RequestMapping("/tag/{tag}/page/{page:[0-9]+}/")
    public ModelAndView getArchiveByTagPage(@PathVariable String tag, @PathVariable int page) {
        LOGGER.debug("Getting the archive by tag={}, page={}", tag, page);
        ModelMap model = new ModelMap();
        model.addAttribute("posts", documentService.getRecentByTag(tag, page - 1, NUM_PER_PAGE));
        LOGGER.trace("Generated model={}", model);
        return new ModelAndView("archive_tag", model);
    }

}