package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import storage.CategoryNodeStorage;
import storage.CategoryStorage;
import storage.PatentNodeStorage;
import storage.PatentStorage;
import util.TextParser;
import util.Vector;

public class ReadFactory {
	public CategoryStorage categoryStorage = new CategoryStorage();
	public PatentStorage patentStorage = new PatentStorage();

	public static int THRESHOLD = 6;

	public void readFiles() throws ClassNotFoundException, SQLException {
		this.categoryStorage.count = categoryStorage.readFromsql(this);
		this.patentStorage.count = patentStorage.readFromsql(this);
	}

	public HashMap<Integer, Vector> patentVec0 = new HashMap<Integer, Vector>();
	public HashMap<Integer, Vector> patentVec1 = new HashMap<Integer, Vector>();
	public HashMap<Integer, Vector> patentVec2 = new HashMap<Integer, Vector>();

	public void TFIDF() throws Exception {
		fulfillVec();
		Queue<Integer> todo = new LinkedList<Integer>();
		todo.add(-1);

		int timer1 = 0;
		while (!todo.isEmpty()) {
			int father = todo.poll();
			int level = 0;

			if (father != -1)
				level = categoryStorage.index.get(father).level;

			switch (level) {
			case 1:
				List<Integer> children = new ArrayList<Integer>();
				for (CategoryNodeStorage node : categoryStorage.index) {
					if (node.fatherid == father) {
						children.add(node.id);
					}
				}
				int fathercount = 0;
				for (int i = 0, j = children.size(); i < j; i++) {
					fathercount += categoryStorage.index.get(children.get(i)).count;
				}

				Iterator<?> it1 = categoryStorage.index.get(children.get(0)).vector.vector
						.keySet().iterator();
				while (it1.hasNext()) {
					String key = (String) it1.next();
					int doccount = 0;
					for (Integer i : children) {
						for (Integer j : categoryStorage.index.get(i).patents) {
							if (vec.get(j).containsKey(key))
								doccount++;
						}
					}
					for (Integer i : children) {
						for (Integer j : categoryStorage.index.get(i).patents) {
							int contextCount = 0;
							if (vec.get(j).containsKey(key))
								contextCount = vec.get(j).get(key);
							double TF = (double) contextCount / vecCount.get(j);
							double IDF = Math.log((double) fathercount
									/ (doccount + 1))
									/ Math.log(2);
							Vector tmp;
							if ((tmp = patentVec1.get(j)) == null)
								tmp = new Vector();
							tmp.vector.put(key, TF * IDF);
							patentVec1.put(j, tmp);
						}
					}
				}
				break;
			case 2:
				List<Integer> children2 = new ArrayList<Integer>();
				for (CategoryNodeStorage node : categoryStorage.index) {
					if (node.fatherid == father) {
						children2.add(node.id);
					}
				}
				int fathercount2 = 0;
				for (int i = 0, j = children2.size(); i < j; i++) {
					fathercount2 += categoryStorage.index.get(children2.get(i)).count;
				}

				Iterator<?> it2 = categoryStorage.index.get(children2.get(0)).vector.vector
						.keySet().iterator();
				while (it2.hasNext()) {
					String key = (String) it2.next();
					int doccount = 0;
					for (Integer i : children2) {
						for (Integer j : categoryStorage.index.get(i).patents) {
							if (vec.get(j).containsKey(key))
								doccount++;
						}
					}
					for (Integer i : children2) {
						for (Integer j : categoryStorage.index.get(i).patents) {
							int contextCount = 0;
							if (vec.get(j).containsKey(key))
								contextCount = vec.get(j).get(key);
							double TF = (double) contextCount / vecCount.get(j);
							double IDF = Math.log((double) fathercount2
									/ (doccount + 1))
									/ Math.log(2);
							Vector tmp;
							if ((tmp = patentVec2.get(j)) == null)
								tmp = new Vector();
							tmp.vector.put(key, TF * IDF);
							patentVec2.put(j, tmp);
						}
					}
				}
				break;
			case 0:
				List<Integer> children3 = new ArrayList<Integer>();
				for (CategoryNodeStorage node : categoryStorage.index) {
					if (node.fatherid == father) {
						children3.add(node.id);
					}
				}
				int fathercount3 = 0;
				for (int i = 0, j = children3.size(); i < j; i++) {
					fathercount3 += categoryStorage.index.get(children3.get(i)).count;
				}

				Iterator<?> it3 = categoryStorage.index.get(children3.get(0)).vector.vector
						.keySet().iterator();
				while (it3.hasNext()) {
					String key = (String) it3.next();
					int doccount = 0;
					for (Integer i : children3) {
						for (Integer j : categoryStorage.index.get(i).patents) {
							if (vec.get(j).containsKey(key))
								doccount++;
						}
					}
					for (Integer i : children3) {
						for (Integer j : categoryStorage.index.get(i).patents) {
							System.out.println(j);
							int contextCount = 0;
							if (vec.get(j).containsKey(key))
								contextCount = vec.get(j).get(key);
							double TF = (double) contextCount / vecCount.get(j);
							double IDF = Math.log((double) fathercount3
									/ (doccount + 1))
									/ Math.log(2);

							Vector tmp;
							if ((tmp = patentVec0.get(j)) == null)
								tmp = new Vector();
							tmp.vector.put(key, TF * IDF);
							patentVec0.put(j, tmp);
						}
					}
				}
				break;
			}

			for (CategoryNodeStorage node : categoryStorage.index) {
				if (node.fatherid == father
						&& categoryStorage.hasChild(node.id)) {
					todo.add(node.id);
				}
			}
			System.out.println(++timer1);
		}
		updateVector2SQL();
	}

	public void updateVector2SQL() throws Exception {
		Connection conn = null;
		String sql;
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();

		Iterator<?> it1 = patentVec0.keySet().iterator();
		while (it1.hasNext()) {
			int tmp = (Integer) it1.next();
			sql = "INSERT INTO vector (id, l1, l2, l3) VALUES(" + tmp + ",'"
					+ patentVec0.get(tmp).Vector2Str().replace("'", "''")
					+ "', '', '')";
			stmt.execute(sql);
		}

		Iterator<?> it2 = patentVec1.keySet().iterator();
		while (it2.hasNext()) {
			int tmp = (Integer) it2.next();
			sql = "UPDATE `vector` SET l2 ='"
					+ patentVec1.get(tmp).Vector2Str().replace("'", "''")
					+ "' WHERE Id = " + tmp;
			stmt.execute(sql);
		}

		Iterator<?> it3 = patentVec2.keySet().iterator();
		while (it3.hasNext()) {
			int tmp = (Integer) it3.next();
			sql = "UPDATE `vector` SET l3 ='"
					+ patentVec2.get(tmp).Vector2Str().replace("'", "''")
					+ "' WHERE Id = " + tmp;
			try {
				stmt.execute(sql);
			} catch (Exception e) {
				System.out.println(sql);
			}
		}
	}

	public List<HashMap<String, Double>> pvec = new ArrayList<HashMap<String, Double>>();
	public List<HashMap<String, Integer>> vec = new ArrayList<HashMap<String, Integer>>();
	public List<Integer> vecCount = new ArrayList<Integer>();

	@SuppressWarnings("unused")
	public void IG() throws Exception {
		fulfillVec();
		Queue<Integer> todo = new LinkedList<Integer>();
		todo.add(-1);

		int timer1 = 0;
		while (!todo.isEmpty()) {
			int father = todo.poll();
			String mainString = AbstructSummary(father);

			HashMap<String, Integer> mainHash = new TextParser()
					.luceneTokenizer(mainString);
			HashMap<String, Double> mainCal = new HashMap<String, Double>();
			List<Integer> children = new ArrayList<Integer>();
			for (CategoryNodeStorage node : categoryStorage.index) {
				if (node.fatherid == father) {
					children.add(node.id);
				}
			}

			Iterator<?> it1 = mainHash.keySet().iterator();
			while (it1.hasNext()) {
				String word = (String) it1.next();

				double IG = 0;
				// Start to count H(C) = SUM(P(C) * Log(P(C),2))
				double HC = 0;
				int fathercount = 0;

				if (father == -1) {
					for (int i = 0, j = children.size(); i < j; i++) {
						fathercount += categoryStorage.index.get(children
								.get(i)).count;
					}
				} else {
					fathercount = categoryStorage.index.get(father).count;
				}

				for (int i = 0, j = children.size(); i < j; i++) {
					double tmpHC = (double) categoryStorage.index.get(children
							.get(i)).count / fathercount;
					HC += tmpHC * Math.log(tmpHC) / Math.log(2);
				}
				// End of counting H(C)

				// Start to count H(C|t)
				double HCt = 0;
				// Start to count P(t) * SUM(P(C|t) * Log(P(C|t),2))
				// Start to count P(t) & !P(t)
				double pt = 0;
				double pt_ = 0;
				int ptCount = 0;
				if (father == -1) {
					ptCount = 0;
					List<Integer> patents = new ArrayList<Integer>();
					for (int i = 0, j = children.size(); i < j; i++) {
						patents.addAll(categoryStorage.index.get(children
								.get(i)).patents);
					}
					for (Integer i : patents) {
						if (vec.get(i).containsKey(word)) {
							ptCount++;
						}
					}
					pt = (double) ptCount / fathercount;

				} else {
					ptCount = 0;
					for (Integer i : categoryStorage.index.get(father).patents) {
						if (vec.get(i).containsKey(word)) {
							ptCount++;
						}
					}
					pt = (double) ptCount / fathercount;
				}
				pt_ = 1 - pt;
				// End of counting P(t) & !P(t)
				// Start to count SUM() & !SUM()
				double sum = 0;
				double sum_ = 0;

				for (int i = 0, j = children.size(); i < j; i++) {
					int sumCount = 0, sumCount_ = 0, l = categoryStorage.index
							.get(i).patents.size();
					double tmpHCT = 0, tmpHCT_ = 0;
					for (int k = 0; k < l; k++) {
						if (vec.get(k).containsKey(word)) {
							sumCount++;
						} else {
							sumCount_++;
						}
					}

					if (ptCount == 0) {
						throw new Exception("ptCount");
					} else {
						tmpHCT = (double) sumCount / ptCount;
					}
					int tmpMinus = fathercount - ptCount;
					if (tmpMinus == 0) {
						tmpHCT_ = 0;
					} else {
						tmpHCT_ = (double) sumCount_ / tmpMinus;
					}

					if (tmpHCT != 0)
						sum += tmpHCT * Math.log(tmpHCT) / Math.log(2);
					else
						sum += 0;
					if (tmpHCT_ != 0)
						sum_ += tmpHCT_ * Math.log(tmpHCT_) / Math.log(2);
					else
						sum_ += 0;
				}
				// End of counting SUM() & !SUM()
				IG = -HC + pt * sum + pt_ * sum_;
				mainCal.put(word, IG);

			}

			// Counting Special Vector
			List<Map.Entry<String, Double>> list_Data = new ArrayList<Map.Entry<String, Double>>(
					mainCal.entrySet());
			Collections.sort(list_Data,
					new Comparator<Map.Entry<String, Double>>() {
						public int compare(Map.Entry<String, Double> o1,
								Map.Entry<String, Double> o2) {
							if ((o2.getValue() - o1.getValue()) > 0)
								return 1;
							else if ((o2.getValue() - o1.getValue()) == 0)
								return 0;
							else
								return -1;
						}
					});

			for (int i = 0, j = list_Data.size(); i < j && i < THRESHOLD - 1; i++) {
				for (int k : children) {
					String fuck = list_Data.get(i).getKey();
					categoryStorage.index.get(k).vector.vector.put(fuck,
							(double) 0);

				}
				// System.out.println(list_Data.get(i).getKey());
			}

			for (CategoryNodeStorage node : categoryStorage.index) {
				if (node.fatherid == father
						&& categoryStorage.hasChild(node.id)) {
					todo.add(node.id);
				}
			}
			System.out.println(++timer1);
		}
	}

	public void fulfillVec() throws Exception {
		vec.clear();
		for (PatentNodeStorage node : patentStorage.Patents) {
			if (node.category != -1)
				vec.add(new TextParser().luceneTokenizer(node.abstruct));

		}
		for (int i = 0, j = vec.size(); i < j; i++) {
			Iterator<?> it1 = vec.get(i).keySet().iterator();
			int count = 0;
			while (it1.hasNext()) {
				count += vec.get(i).get(it1.next());
			}
			vecCount.add(count);
		}
	}

	public String AbstructSummary(int fatherid) {
		String tmp = "";
		if (fatherid == -1) {
			for (PatentNodeStorage node : patentStorage.Patents) {
				if (node.category != -1)
					tmp += node.abstruct + " ";
			}
			return tmp;
		} else {
			for (int i : categoryStorage.index.get(fatherid).patents) {
				tmp += patentStorage.Patents.get(i).abstruct + " ";
			}
			return tmp;
		}
	}

	public void updateIndexVector2SQL() throws Exception {
		Connection conn = null;
		String sql;
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		for (CategoryNodeStorage node : categoryStorage.index) {
			sql = "UPDATE `index` SET vector ='"
					+ node.vector.Vector2Str().replace("'", "''")
					+ "' WHERE Id = " + node.id;
			stmt.execute(sql);
		}
	}

	public void deleteVector() {
		for (CategoryNodeStorage node : categoryStorage.index) {
			node.vector.vector.clear();
		}
	}

	public void deleteVectorTable() throws Exception {
		Connection conn = null;
		String sql = "delete from vector";
		String url = "jdbc:mysql://localhost:3306/auto";
		String user = "root", password = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}
}
