package com.scmspain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Tweet {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private LocalDateTime creation;
    @Column(nullable = true)
    private LocalDateTime discarded;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private String tweet;
    @Column(nullable = true)
    private Long pre2015MigrationStatus = 0L;

    public Tweet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreation() {
        return creation.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public String getDiscarded() {
        if (discarded != null) {
            return discarded.format(DateTimeFormatter.ISO_DATE_TIME);
        }else{
            return "";
        }
    }

    public void setDiscarded(LocalDateTime discarded) {
        this.discarded = discarded;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public void setPre2015MigrationStatus(Long pre2015MigrationStatus) {
        this.pre2015MigrationStatus = pre2015MigrationStatus;
    }

}
