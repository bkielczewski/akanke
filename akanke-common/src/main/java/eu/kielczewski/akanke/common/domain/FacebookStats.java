package eu.kielczewski.akanke.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "facebook_stats")
public class FacebookStats {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @NotNull
    @JsonProperty("comment_count")
    @Column(name = "comment_count")
    private int commentCount = 0;

    @NotNull
    @JsonProperty("share_count")
    @Column(name = "share_count")
    private int shareCount = 0;

    public FacebookStats() {
    }

    public FacebookStats(int commentCount, int shareCount) {
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }

    public int getId() {
        return id;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("commentCount", commentCount)
                .add("shareCount", shareCount)
                .toString();
    }

}
