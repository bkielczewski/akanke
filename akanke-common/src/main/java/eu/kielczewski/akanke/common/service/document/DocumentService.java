package eu.kielczewski.akanke.common.service.document;

import eu.kielczewski.akanke.common.domain.Document;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DocumentService {

    Document get(String id);

    Map<String, Long> getTagCounts();

    Page<Document> getRecentByTag(String tag, int page, int numPerPage);

    Page<Document> getRecent(int page, int numPerPage);

    Map<String, Map<String, Long>> getCountInYearMonth();

    Page<Document> getRecentByYear(int year, int page, int numPerPage);

    Map<String, Long> getCountInMonthByYear(int year);

    Page<Document> getRecentByYearMonth(int year, int month, int page, int numPerPage);

    long getCountByYearMonth(int year, int month);

    List<Document> getMostPopular(int count);
}
