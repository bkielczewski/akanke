package eu.kielczewski.akanke.web.service.viewhelper;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class ViewHelperServiceImpl implements ViewHelperService {

    private static final RelativeDateToStringConverter relativeDateToStringConverter = new RelativeDateToStringConverter();
    private static final ExcerptExtractor excerptExtractor = new ExcerptExtractor();

    @Override
    public String relativeDate(Date date) {
        return relativeDateToStringConverter.convert(date);
    }

    @Override
    public String extractExcerpt(String source, String excerptEndTagReplacement) {
        return excerptExtractor.extract(source, excerptEndTagReplacement);
    }

}
