package cs320.Homework2;

public class Notes {
    int id;
	String name;
	String note;
	String title;
	public Notes(int id, String name, String note, String title) {
		this.id=id;
		this.name=name;
		this.note=note;
		this.title=title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
