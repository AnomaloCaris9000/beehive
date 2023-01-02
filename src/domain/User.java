package domain;

import java.util.HashSet;
import java.util.Stack;
import java.util.function.Consumer;

import domain.post.Content;
import domain.post.Post;
import domain.post.RepostContent;

public class User {
	
	
	private String id;
	private HashSet<String> followedSet;
	private HashSet<String> followerSet;
	private Stack<Post> feedStack;
	private Stack<Post> userPostStack;
	private Network network;


	public User(String id, Network network) {
		this.id = id;
		this.network = network;
		
		
		// init time !
		followedSet = new HashSet<>();
		followerSet = new HashSet<>();
		feedStack = new Stack<>();
		userPostStack = new Stack<>();
	}
	
	
	/**
	 * Useful for persistance
	 * @param names names[0] being the name and other names are the followed users names
	 * @param network
	 */
	public User(String[] names, Network network) {
		this(names[0], network);
		// attentions ! on ne garantie pas que les followed compte this comme follower !
		// it's a leap of face - Spider Man 
		for(int i = 1; i < names.length; i++) {
			this.followedSet.add(names[i]);
		}
	}
	
	
	
	/**
	 * Create a post while updating the network
	 * @param content
	 * @return
	 */
	public Post createPost(Content content) {
		Post post = new Post(content, this.getId(), this.getNetwork());
		userPostStack.add(post); // ajout du post dans la liste des posts de l'objet
		network.addPost(post); // ajout du post dans le reseau
		updateFollower(post);
		return post; // une referense du post
	}
	
	
	/**
	 * A follow B => B is following A 
	 */
	public void updateFollowRelation() {
		for(String followedUserId: followedSet) {
			User followedUser = network.getUser(followedUserId);
			if(followedUser != null) {
				followedUser.followerSet.add(this.getId());
			}
		}
	}
	
	
	/**
	 * 
	 * @param post
	 */
	private void updateFollower(Post post) {
		for(String followerId: followerSet) 
			network.getUser(followerId).feedStack.add(post);
	}
	
	
	/**
	 * Create a post thats share another post
	 * @param post
	 * @return
	 */
	public Post sharePost(Post post) {
		return createPost(new RepostContent(id, post));
	}
	
	
	public void likePost(Post post) {
		post.receiveLike();
	}
	
	
	/**
	 * Apply a certain treatement to the top of the feed stack
	 * Update feed stack
	 * @param cons
	 */
	public void consumesFeed(Consumer<Post> cons) throws EmptyFeedException {
		if(feedStack.isEmpty()) 
			throw new EmptyFeedException(); 
		else 
			cons.accept(feedStack.pop());
	}
	
	
	/**
	 * 
	 * @param id
	 */
	public void follows(String followedUserId) {
		if(!this.isFollowing(followedUserId)) { 
			followedSet.add(followedUserId);
			network.getUser(followedUserId).followerSet.add(this.getId()); // l'utilisateur suivis compte maintenant notre objet dans sa liste de suiveur
			network.getMemory().updateFollow(this.getId(), followedUserId);
		}
	}
	
	
	public void unFollows(String followedUserId) {
		if(this.isFollowing(followedUserId)) {
			this.followedSet.remove(followedUserId);
			User followedUser = network.getUser(followedUserId);
			if(followedUser != null) {// au cas ou l'utilisateur n'existe plus
				followedUser.followerSet.remove(this.getId());
			}
		}
	}
	
	
	public boolean isFollowing(String followedUserId) {
		return followedSet.contains(followedUserId);
	}
	
	
	public boolean isFollowed(String followerUserId) {
		return followerSet.contains(followerUserId);
	}
	
	
	public String getId() {
		return id;
	}
	
	
	public Network getNetwork() {
		return network;
	}
	
}
