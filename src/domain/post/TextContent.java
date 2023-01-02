package domain.post;



public class TextContent extends Content {
	
	
	private String text;


	public TextContent(String title, String text) {
		super(title);
		this.text = text;
	}
	
	
	public TextContent(String text) { // pas toujours besoin de titre
		this("null", text);
	}
	
	
	@Override
	public String toString() {
		return String.format("[title : %s]\n%s", title, text);
	}
	

}
