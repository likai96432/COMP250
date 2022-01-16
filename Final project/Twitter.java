import java.util.ArrayList;

public class Twitter {

	private MyHashTable<String, Tweet> tweetsbyauthor;
	private MyHashTable<String, ArrayList<Tweet>> tweetsbydate;
	private ArrayList<Tweet> tweetslist;
	private ArrayList<String> stopwordslist;


	
  
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		tweetsbyauthor = new MyHashTable<String, Tweet>(tweets.size());
		tweetsbydate = new MyHashTable<String, ArrayList<Tweet>>(tweets.size());
        tweetslist=tweets;
        stopwordslist=new ArrayList<String>(stopWords.size());
       
       
        
		for (int i = 0; i < tweets.size(); i++) {
			this.addTweet(tweets.get(i));
		}
		for (int i = 0; i < stopWords.size(); i++) {
			stopwordslist.add(stopWords.get(i).toLowerCase());
		}
		
	}

	/**
	 * Add Tweet t to this Twitter O(1)
	 */
	public void addTweet(Tweet t) {
		String date = t.getDateAndTime().substring(0, 10);
		Tweet haha = tweetsbyauthor.get(t.getAuthor());
		if (haha == null) {
			tweetsbyauthor.put(t.getAuthor(), t);
		} else {

			if (t.getDateAndTime().compareTo(haha.getDateAndTime()) > 0) {
				tweetsbyauthor.put(t.getAuthor(), t);
			}
		}
		if (tweetsbydate.get( t.getDateAndTime().substring(0, 10)) ==null) {
			ArrayList<Tweet> dioablo = new ArrayList<Tweet>();
			dioablo.add(t);
			tweetsbydate.put( t.getDateAndTime().substring(0, 10), dioablo);
		} else {
			 tweetsbydate.get( t.getDateAndTime().substring(0, 10)).add(t);
			
		}
		

	}

	/**
	 * Search this Twitter for the latest Tweet of a given author. If there are no
	 * tweets from the given author, then the method returns null. O(1)
	 */
	public Tweet latestTweetByAuthor(String author) {

		return tweetsbyauthor.get(author);

	}

	/**
	 * Search this Twitter for Tweets by `date' and return an ArrayList of all such
	 * Tweets. If there are no tweets on the given date, then the method returns
	 * null. O(1)
	 */
	public ArrayList<Tweet> tweetsByDate(String date) {

		
			return tweetsbydate.get(date);
		

	}

	/**
	 * Returns an ArrayList of words (that are not stop words!) that appear in the
	 * tweets. The words should be ordered from most frequent to least frequent by
	 * counting in how many tweet messages the words appear. Note that if a word
	 * appears more than once in the same tweet, it should be counted only once.
	 */
	public ArrayList<String> trendingTopics() {
		  
		   MyHashTable<String,Integer> wordshaha=new MyHashTable<String,Integer>(10);
		   ArrayList <String> wordlist;
		   for(int i=0;i<tweetslist.size();i++) {
			   MyHashTable<String,Integer> Allwords=new MyHashTable<String,Integer>(10);
			 wordlist=getWords(tweetslist.get(i).getMessage());
			  for(int j=0;j<wordlist.size();j++) {
				  Integer haha=1;
				  Allwords.put(wordlist.get(j).toLowerCase(), haha);
			  }
			  for(int k=0;k<Allwords.keys().size();k++) {
				  if(wordshaha.get(Allwords.keys().get(k))!=null) 
				   {
					  Integer indice=wordshaha.get(Allwords.keys().get(k));
					  wordshaha.put(Allwords.keys().get(k), indice+1);
					  
				  }
				  else {
					  Integer yu=1;
					  wordshaha.put(Allwords.keys().get(k), yu);
				  }
			  }
		   }
		   for(int c=0;c<stopwordslist.size();c++) {
			   wordshaha.remove(stopwordslist.get(c));
		   }
		   

		return wordshaha.fastSort(wordshaha);

		
	}

	/**
	 * An helper method you can use to obtain an ArrayList of words from a String,
	 * separating them based on apostrophes and space characters. All character that
	 * are not letters from the English alphabet are ignored.
	 */
	private static ArrayList<String> getWords(String msg) {
		msg = msg.replace('\'', ' ');
		String[] words = msg.split(" ");
		ArrayList<String> wordsList = new ArrayList<String>(words.length);
		for (int i = 0; i < words.length; i++) {
			String w = "";
			for (int j = 0; j < words[i].length(); j++) {
				char c = words[i].charAt(j);
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
					w += c;

			}
			wordsList.add(w);
		}
		return wordsList;
	}

}
