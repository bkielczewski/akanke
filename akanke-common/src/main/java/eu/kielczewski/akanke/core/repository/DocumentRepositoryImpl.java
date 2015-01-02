package eu.kielczewski.akanke.core.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
class DocumentRepositoryImpl implements DocumentRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Map<String, Map<String, Long>> getCountInYearMonth() {
        LOGGER.debug("Getting document count in year, month");
        List<Object[]> rows = getCountInYearMonthQueryResult();
        Map<String, Map<String, Long>> counts = new LinkedHashMap<>();
        for (Object[] row : rows) {
            String year = String.valueOf(row[0]);
            String month = String.format("%02d", (Integer) row[1]);
            Long count = (Long) row[2];
            Map<String, Long> countInMonth = counts.get(year);
            if (countInMonth != null) {
                countInMonth.put(month, count);
            } else {
                countInMonth = new LinkedHashMap<>();
                countInMonth.put(month, count);
                counts.put(year, countInMonth);
            }
        }
        LOGGER.trace("Returning {}", counts);
        return counts;
    }

    private List<Object[]> getCountInYearMonthQueryResult() {
        Query query = em.createQuery("SELECT YEAR(d.datePublished) AS year, MONTH(d.datePublished) as month, COUNT(*) "
                + "FROM Document d GROUP BY YEAR(d.datePublished), MONTH(d.datePublished) ORDER "
                + "BY YEAR(d.datePublished) DESC, MONTH(d.datePublished) ASC");
        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public Map<String, Long> getCountInMonthByYear(int year) {
        LOGGER.debug("Getting document count in month by year={}", year);
        List<Object[]> rows = getCountInMonthByYearQueryResult(year);
        Map<String, Long> count = new LinkedHashMap<>();
        for (Object[] row : rows) {
            count.put(String.valueOf(String.format("%02d", (Integer) row[0])), (Long) row[1]);
        }
        LOGGER.trace("Returning {}", count);
        return count;
    }

    private List<Object[]> getCountInMonthByYearQueryResult(int year) {
        Query query = em.createQuery("SELECT MONTH(d.datePublished) as month, COUNT(*) FROM Document d "
                + "WHERE YEAR(d.datePublished) = ?1 GROUP BY MONTH(d.datePublished) ORDER BY month ASC");
        query.setParameter(1, year);
        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public long getCountByYearMonth(int year, int month) {
        LOGGER.debug("Getting document count in year={}, month={}", year, month);
        long count = getCountByYearMonthQueryResult(year, month);
        LOGGER.trace("Returning {}", count);
        return count;
    }

    private long getCountByYearMonthQueryResult(int year, int month) {
        Query query = em.createQuery("SELECT COUNT(*) FROM Document d "
                + "WHERE YEAR(d.datePublished) = ?1 AND MONTH(d.datePublished) = ?2");
        query.setParameter(1, year);
        query.setParameter(2, month);
        return (long) query.getSingleResult();
    }

    @Override
    public Map<String, Long> getTagCounts() {
        LOGGER.debug("Getting document tag counts");
        List<Object[]> rows = getTagCountsQueryResult();
        Map<String, Long> count = new HashMap<>();
        for (Object[] row : rows) {
            count.put((String) row[0], ((BigInteger) row[1]).longValue());
        }
        LOGGER.trace("Returning {}", count);
        return count;
    }

    private List<Object[]> getTagCountsQueryResult() {
        Query query = em.createNativeQuery("SELECT dt.tag, COUNT(*) as count_ FROM document_tags dt "
                + "GROUP BY dt.tag ORDER BY count_ DESC");
        //noinspection unchecked
        return query.getResultList();
    }


}
