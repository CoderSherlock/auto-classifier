package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import storage.CategoryNodeStorage;
import util.Vector;

public class VectorFactory extends ReadFactory {
	public VectorFactory() throws Exception {

	}

	public void caculateEASY() throws Exception {
		for (int i = 0, j = categoryStorage.index.size(); i < j; i++) {
			int level = categoryStorage.index.get(i).level;
			Iterator<?> it = categoryStorage.index.get(i).vector.vector
					.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				double avg = 0;
				for (Integer k : categoryStorage.index.get(i).patents) {
					if (level == 1) {
						avg += patentVec0.get(k).vector.get(key);
					} else if (level == 2) {
						avg += patentVec1.get(k).vector.get(key);
					} else {
						avg += patentVec2.get(k).vector.get(key);
					}
				}
				avg /= categoryStorage.index.get(i).count;
				categoryStorage.index.get(i).vector.vector.put(key, avg);
			}
		}
		updateIndexVector2SQL();
	}

	public int classify(int patentid, int possibleIndexFather) {
		TFIDF4single(possibleIndexFather, patentid);
		ArrayList<Integer> possibleIndex = new ArrayList<Integer>();
		HashMap<Integer, Vector> indexVector = new HashMap<Integer, Vector>();
		Vector classifiedVector = bClassify.get(patentid);
		for (CategoryNodeStorage node : categoryStorage.index) {
			if (node.fatherid == possibleIndexFather) {
				possibleIndex.add(node.id);
			}
		}
		for (Integer index : possibleIndex) {
			// Iterator for Each Category
			int count = 0;
			Vector mv = new Vector();
			for (Integer patent : categoryStorage.index.get(index).patents) {
				Vector v = bClassify.get(patent);
				Iterator<?> it1 = v.vector.keySet().iterator();
				while (it1.hasNext()) {
					String word = (String) it1.next();
					if (mv.vector.containsKey(word)) {
						mv.vector.put(word,
								mv.vector.get(word) + v.vector.get(word));
					} else {
						mv.vector.put(word, v.vector.get(word));
					}
				}
				count++;
			}
			Iterator<?> itfinal = mv.vector.keySet().iterator();
			while (itfinal.hasNext()) {
				String word = (String) itfinal.next();
				mv.vector.put(word, mv.vector.get(word) / count);
			}
			indexVector.put(index, mv);
		}

		Iterator<?> itmain = indexVector.keySet().iterator();
		int classify = -1;
		double dis = 1000;
		while (itmain.hasNext()) {
			int i = (Integer) itmain.next();
			double tmpD = classifiedVector.eucliDistance(indexVector.get(i));
			System.out.println("Distance to " + String.valueOf(i) + ":"
					+ String.valueOf(tmpD));
			if (tmpD < dis) {
				dis = tmpD;
				classify = i;
			}
		}
		// System.out.print("haha");
		return classify;
	}

	public int classify(int patentid, int possibleIndexFather, int possible) {
		int i = classify(patentid, possibleIndexFather);
		while (categoryStorage.hasChild(i)) {
			i = classify(patentid, i);
		}
		possible = i;
		return possible;
	}
}
