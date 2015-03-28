package storage;

public class PatentNodeStorage {
	public int id = -1;
	public String no = "";
	public String name = "";
	public String abstruct = "";
	// Overview
	public int category = -2;
	public boolean isAutomaticClassified = false;
	public boolean isMatched = true;

	public PatentNodeStorage(String no, String name, String abstrct,
			int category) {
		this.no = no;
		this.name = name;
		this.abstruct = abstrct;
		this.category = category;
	}

	public PatentNodeStorage(int id, String no, String name, String abstruct,
			int category, boolean isAutoClassified, boolean isMatched) {
		this.id = id;
		this.no = no;
		this.name = name;
		this.abstruct = abstruct;
		this.category = category;
		this.isAutomaticClassified = isAutoClassified;
		this.isMatched = isMatched;
	}
}