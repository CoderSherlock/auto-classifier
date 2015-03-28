package storage;

import java.util.ArrayList;

import util.Vector;

public class CategoryNodeStorage {
	public int id = -2;
	public int fatherid = -2;
	public int level = 0;
	public String name = "";
	public Vector vector = new Vector();
	public ArrayList<Integer> patents = new ArrayList<Integer>();
	public int count = 0;

	public CategoryNodeStorage(String name, int level) {
		this.name = name;
		this.level = level;
	}

	public int addPatent(int id) {
		patents.add(id);
		return ++count;
	}

	public CategoryNodeStorage(int id, int fatherid, int level, String name,
			String vector, String patents, int count) {
		this.id = id;
		this.fatherid = fatherid;
		this.level = level;
		this.name = name;
		this.vector = new Vector(vector);
		this.patents = Str2patents(patents);
		this.count = count;
	}

	private ArrayList<Integer> Str2patents(String patents) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		if (patents.equals("")) {
			return tmp;
		}
		String[] tmpStr = patents.split(",");
		for (String string : tmpStr) {
			tmp.add(Integer.valueOf(string));
		}
		return tmp;
	}

	public String patents2Str() {
		String tmpStr = "";
		for (int patent : patents) {
			tmpStr += Integer.toString(patent) + ",";
		}
		if(!tmpStr.equals(""))
			tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
		return tmpStr;
	}
}
