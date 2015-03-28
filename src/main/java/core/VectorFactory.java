package core;

import java.util.Iterator;

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
}
