package com.tts.techtalenttwitter.Repository;

import java.util.List;

import com.tts.techtalenttwitter.Model.Tweet;
import com.tts.techtalenttwitter.Model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {

    List<Tweet>findAllByOrderByCreatedAtDesc();
    List<Tweet>findAllByUserOrderByCreatedAtDesc(User user);
    List<Tweet>findAllByUserInOrderByCreatedAtDesc(List<User> user);
    List<Tweet>findAllByTagOrderByCreatedAtDesc(String phrase);
}
    

