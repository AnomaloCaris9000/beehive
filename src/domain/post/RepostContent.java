package domain.post;

public class RepostContent extends Content {
	
	
	private Post post;
	

	public RepostContent(String title, Post post) {
		super(title);
		this.post = post;
	}
	
	
	@Override
	public String toString() {
		return post.getContent().toString();
	}

}
