package eu.kielczewski.akanke.core.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
@Immutable
@Entity
public class Document implements Serializable {

    @Id
    @NotNull
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @NotNull
    @Column(name = "file", nullable = false, updatable = false)
    private String file;

    @NotNull
    @Column(name = "title", nullable = false, updatable = false)
    private String title;

    @Column(name = "description", updatable = false)
    private String description;

    @NotNull
    @Column(name = "contents", nullable = false, updatable = false, length = 65536)
    @Lob
    private String contents;

    @ElementCollection
    @CollectionTable(name = "document_tags", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "tag", updatable = false)
    private Collection<String> tags;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datePublished", nullable = false, updatable = false)
    private Date datePublished;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "document_id", unique = true)
    @JsonManagedReference
    private FacebookStats facebookStats;

    private Document() {
    }

    public Document(String id, String file, String title, String description,
                    String contents, Collection<String> tags, Date datePublished,
                    FacebookStats facebookStats) {
        this.id = id;
        this.file = file;
        this.title = title;
        this.description = description;
        this.contents = contents;
        this.tags = tags;
        this.datePublished = new Date(datePublished.getTime());
        this.facebookStats = facebookStats;
    }

    public String getId() {
        return id;
    }

    public String getFile() {
        return file;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContents() {
        return contents;
    }

    public Collection<String> getTags() {
        return ImmutableList.copyOf(tags);
    }

    public Date getDatePublished() {
        return new Date(datePublished.getTime());
    }

    public FacebookStats getFacebookStats() {
        return facebookStats;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Document other = (Document) obj;
        return Objects.equal(this.id, other.id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("file", file)
                .add("title", title)
                .add("description", description)
                .add("tags", tags)
                .add("datePublished", datePublished)
                .add("facebookStats", facebookStats)
                .toString();
    }

}
