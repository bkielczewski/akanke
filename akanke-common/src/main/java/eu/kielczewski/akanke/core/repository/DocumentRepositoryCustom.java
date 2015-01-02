package eu.kielczewski.akanke.core.repository;

import java.util.Map;

public interface DocumentRepositoryCustom {

    Map<String, Map<String, Long>> getCountInYearMonth();

    Map<String, Long> getCountInMonthByYear(int year);

    long getCountByYearMonth(int year, int month);

    Map<String, Long> getTagCounts();

}
