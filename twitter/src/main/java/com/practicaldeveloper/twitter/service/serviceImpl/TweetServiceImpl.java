package com.practicaldeveloper.twitter.service.serviceImpl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.practicaldeveloper.twitter.dto.TweetDto;
import com.practicaldeveloper.twitter.model.Tweet;
import com.practicaldeveloper.twitter.repository.TweetRepository;
import com.practicaldeveloper.twitter.service.TweetService;
import com.practicaldeveloper.twitter.exception.NotFoundException;

@Service
public class TweetServiceImpl implements TweetService{
	
	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public Tweet saveTweet(Tweet tweetDto) {
		System.out.print("Before+ service");
		LocalDateTime obj=LocalDateTime.now();
    	tweetDto.setTweetDate(obj);
		// TODO Auto-generated method stub
		Tweet tweet=new Tweet(tweetDto.getEmail(),tweetDto.getTweetText(),tweetDto.getTweetDate());
		
		
		return tweetRepository.save(tweet);
	}

	@Override
	public List<TweetDto> getAllTweets() {
		
		// TODO Auto-generated method stub
		
		List<TweetDto> tweetDtoList=new ArrayList<TweetDto>(); 
		
		List<Tweet> tweetList=tweetRepository.findAll();
		
		for(Tweet tweets:tweetList) {
			
			Stack<Tweet> s=new Stack();
			s.add(tweets);
			
			while(!s.isEmpty()) {
				Tweet tweet=s.pop();
				System.out.println(tweet);
				TweetDto tweetDto=new TweetDto(
						tweet.getTweetId(),
						tweet.getEmail(),
						tweet.getTweetText(),
						tweet.getTweetDate());
				tweetDtoList.add(tweetDto);
			}
			
			
		}
		return tweetDtoList;
	}

	@Override
	public void deleteTweet(int tweetId) {
		// TODO Auto-generated method stub
		tweetRepository.deleteById(tweetId);
			
	}

	@Override
	public TweetDto updateTweet(int tweetId,TweetDto tweetDto) {
		// TODO Auto-generated method stub
		
		Tweet updateTweet=tweetRepository.findById(tweetId)
				  .orElseThrow(() -> new NotFoundException("Tweet not found"));
		
		
		updateTweet.setTweetText(tweetDto.getTweetText());
		
		tweetRepository.save(updateTweet);
		
		
		
		return convertToTweetDto(updateTweet);
	}
	
	public TweetDto convertToTweetDto(Tweet tweet) {
		TweetDto tweetDto=new TweetDto(tweet.getTweetId(),
				                      tweet.getEmail(),
				                      tweet.getTweetText(),
				                      tweet.getTweetDate());
		
		
		return tweetDto;
	}

	@Override
	public TweetDto getTweetById(int tweetId) {
		// TODO Auto-generated method stub
		
		Tweet getTweet=tweetRepository.findById(tweetId)
				  .orElseThrow(() -> new NotFoundException("Tweet not found"));
		
		
		TweetDto tweetDto=new TweetDto(getTweet.getTweetId(),
				                       getTweet.getEmail(),
				                       getTweet.getTweetText(),
				                       getTweet.getTweetDate()
				                       );
				
		
		return tweetDto;
	}

	@Override
	public List<TweetDto> findByEmail(String email) {
		// TODO Auto-generated method stub
		List<TweetDto> tweetDtoList=new ArrayList<TweetDto>(); 
		
		List<Tweet> tweetList=tweetRepository.findByEmail(email);
				 
		Stack<Tweet> s=new Stack();
		for(Tweet twet:tweetList) {
			

			
			s.add(twet);
			
		}
		while(!s.isEmpty()) {
			Tweet tweet=s.pop();
			System.out.println(tweet.getEmail());
			tweetDtoList.add(convertToTweetDto(tweet));
		}
		
		return tweetDtoList ;
	}

}
