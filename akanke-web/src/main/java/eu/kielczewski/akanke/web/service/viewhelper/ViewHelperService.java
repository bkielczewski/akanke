package eu.kielczewski.akanke.web.service.viewhelper;


import java.util.Date;

public interface ViewHelperService {

    String relativeDate(Date date);

    String extractExcerpt(String src, String moreUrl);

}
