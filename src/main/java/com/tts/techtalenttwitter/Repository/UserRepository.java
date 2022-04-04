package com.tts.techtalenttwitter.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.techtalenttwitter.Model.User;

//This is going to be the interface by whitch we
//access the database table that holds User Objects

//We do not write the code for this
//We make an interface that acts as a speification
//for what we want, and spring boot makes the actual repository

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
// the basic method we add with crudReposityory
// are pretty much the bare minimum interface we would want
// to use in our database
	
	User findByUsername(String username);
	User findAllByUserName(String username);
}
