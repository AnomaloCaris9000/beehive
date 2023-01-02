package domain.post;


import domain.*;


public class Post {
	
	
	private Content content;
	private String authorId;
	private int likeCount;
	
	
	public Post(Content content, String authorId, Network network) {
		this.content = content;
		this.authorId = authorId;
		this.likeCount = 0;
	}
	
	
	
	public void receiveLike() {
		this.likeCount ++;
	}
	
	
	@Override
	public String toString() {
		return content.toString();
	}

	public Object getAuthorId() {
		return authorId;
	}

	public Object getContent() {
		return content;
	}

}
