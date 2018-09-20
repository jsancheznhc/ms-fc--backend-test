package com.scmspain.services;

import com.scmspain.Constants.SMCQuerys;
import com.scmspain.entities.Tweet;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TweetService {
    private EntityManager entityManager;
    private MetricWriter metricWriter;

    public TweetService(EntityManager entityManager, MetricWriter metricWriter) {
        this.entityManager = entityManager;
        this.metricWriter = metricWriter;
    }

    /**
     * Push tweet to repository
     * Parameter - publisher - creator of the Tweet
     * Parameter - text - Content of the Tweet
     * Result - recovered Tweet
     */
    public void publishTweet(String publisher, String text) {
            Tweet tweet = new Tweet();
            tweet.setTweet(text);
            tweet.setPublisher(publisher);
            tweet.setCreation(LocalDateTime.now());
            this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
            this.entityManager.persist(tweet);
    }

    /**
     * Push tweet to repository
     * Parameter - publisher - creator of the Tweet
     * Parameter - text - Content of the Tweet
     * Result - recovered discarded Tweet
     */
    public void discardTweet(Long id) {
        Tweet tweet = this.entityManager.find(Tweet.class, id);
        if(tweet!=null){
            tweet.setDiscarded(LocalDateTime.now());
            this.metricWriter.increment(new Delta<Number>("discarded-tweets", 1));
            this.entityManager.merge(tweet);
        }else{
            throw new IllegalArgumentException("Tweet not found");
        }

    }

    /**
     * Recover tweet from repository
     * Parameter - id - id of the Tweet to retrieve
     * Result - retrieved Tweet
     */
    public Tweet getTweet(Long id) {
        return this.entityManager.find(Tweet.class, id);
    }

    /**
     * Recover tweet from repository
     * Parameter - id - id of the Tweet to retrieve
     * Result - retrieved Tweet
     */
    public List<Tweet> listAllTweets() {
        List<Tweet> result = new ArrayList<Tweet>();
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        TypedQuery<Long> query = this.entityManager.createQuery(SMCQuerys.ALL_TWEETS_NOT_DISCARDED, Long.class);
        List<Long> ids = query.getResultList();
        for (Long id : ids) {
            result.add(getTweet(id));
        }
        return result;
    }

    /**
     * Recover tweet from repository
     * Parameter - id - id of the Tweet to retrieve
     * Result - retrieved discarded Tweet
     */
    public List<Tweet> listAllDiscardedTweets() {
        List<Tweet> result = new ArrayList<Tweet>();
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        TypedQuery<Long> query = this.entityManager.createQuery(SMCQuerys.ALL_DISCARDED_TWEETS, Long.class);
        List<Long> ids = query.getResultList();
        for (Long id : ids) {
            result.add(getTweet(id));
        }
        return result;
    }
}
