package com.tts.techtalenttwitter.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tts.techtalenttwitter.Model.Tag;
import com.tts.techtalenttwitter.Model.Tweet;
import com.tts.techtalenttwitter.Model.User;
import com.tts.techtalenttwitter.Repository.TagRepository;
import com.tts.techtalenttwitter.Repository.TweetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Tweet> findAll() {
        List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
        return formatTweets(tweets);
    }

    public List<Tweet> findAllByUser(User user){
        List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
        return formatTweets(tweets);
    }

    public List<Tweet> findAllByUserIn(List<User> users){
        List<Tweet> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(users);
        return formatTweets(tweets);
    }

    public List<Tweet> findAllByTag(String tag){
        List<Tweet> tweets = tweetRepository.findAllByTagOrderByCreatedAtDesc(tag);
        return formatTweets(tweets);
    }

    public Tweet save(Tweet tweet) {
        handleTags(tweet);
        return tweetRepository.save(tweet);
    }

    private void handleTags(Tweet tweet) {
        List<Tag> tags = new ArrayList<Tag>(); 
            Pattern pattern = Pattern.compile("#(\\w+)");
            Matcher matcher = pattern.matcher(tweet.getMessage());

            while (matcher.find()) {
                String phrase = matcher.group().substring(1).toLowerCase();

                Tag tag = tagRepository.findByPhrase(phrase);
                if (tag == null) {
                    tag = new Tag();
                    tag.setPhrase(phrase);
                    tagRepository.save(tag);
                }
                tags.add(tag);
            }
            tweet.setTags(tags);
        }
    
    private List<Tweet> formatTweets(List<Tweet> tweets) {
       addTagLinks(tweets);
         return tweets;
    }

    private void addTagLinks(List<Tweet> tweets) {
        Pattern pattern = Pattern.compile("#(\\w+)");
        for (Tweet tweet : tweets) {
            String message = tweet.getMessage();
            Matcher matcher = pattern.matcher(message);
            Set<String> tags = new HashSet<String>();
            tweet.setMessage(message);
            while (matcher.find()) {
                tags.add(matcher.group());
            }
            for (String tag : tags) {
                String link = "<a class=\tag\" href=\"/tweets/";
                link += tweet.getId();
                link += "/tags/";
                link += tag.substring(1);
                link += "\">";
                link += tag;
                link += "</a>";
                message = message.replace(tag, link);

            }
        }
    }
    
}
