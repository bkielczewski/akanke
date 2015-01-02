package eu.kielczewski.akanke.core.repository;

import eu.kielczewski.akanke.core.domain.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String>, DocumentRepositoryCustom {

    @Query("SELECT d FROM Document d WHERE YEAR(d.datePublished) = ?1")
    Page<Document> findByYear(int year, Pageable pageable);

    @Query("SELECT d FROM Document d WHERE YEAR(d.datePublished) = ?1 AND MONTH(d.datePublished) = ?2")
    Page<Document> findByYearAndMonth(int year, int month, Pageable pageable);

    Page<Document> findByTags(String tag, Pageable pageable);

    Document getByFile(String file);

    @Transactional
    void deleteByFileStartingWith(String target);

    @Query("SELECT d FROM Document d ORDER BY (d.facebookStats.likeCount + d.facebookStats.shareCount + d.facebookStats.commentsboxCount) DESC")
    List<Document> getMostPopular(Pageable pageable);

}
