package eu.kielczewski.akanke.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.restfb.Facebook;

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
    @Facebook("commentsbox_count")
    @JsonProperty("commentsbox_count")
    @Column(name = "commentsbox_count")
    private int commentsboxCount = 0;

    @NotNull
    @Facebook("share_count")
    @JsonProperty("share_count")
    @Column(name = "share_count")
    private int shareCount = 0;

    @NotNull
    @Facebook("like_count")
    @JsonProperty("like_count")
    @Column(name = "like_count")
    private int likeCount = 0;

    public FacebookStats() {
    }

    public FacebookStats(int commentsboxCount, int shareCount, int likeCount) {
        this.commentsboxCount = commentsboxCount;
        this.shareCount = shareCount;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public int getCommentsboxCount() {
        return commentsboxCount;
    }

    public void setCommentsboxCount(int commentsboxCount) {
        this.commentsboxCount = commentsboxCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("commentsboxCount", commentsboxCount)
                .add("shareCount", shareCount)
                .add("likeCount", likeCount)
                .toString();
    }

}
