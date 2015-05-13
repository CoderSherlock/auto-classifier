package util;

import java.util.HashMap;
import java.util.Iterator;

public class Vector {
	public HashMap<String, Double> vector = new HashMap<String, Double>();

	public Vector(String vector) {
		if (vector.equals(""))
			this.vector = Str2Vector(vector);
	}

	public Vector() {
	}

	public Vector(HashMap<String, Double> vector) {
		this.vector = vector;
	}

	public HashMap<String, Double> Str2Vector(String vector) {
		HashMap<String, Double> tmpvec = new HashMap<String, Double>();
		if (vector.equals(""))
			return tmpvec;
		else {
			String[] tmp1 = vector.split(",");
			for (String tmp2 : tmp1) {
				String[] tmp3 = tmp2.split("=");
				if (tmp3.length == 2)
					tmpvec.put(tmp3[0], Double.valueOf(tmp3[1]));
				else
					tmpvec.put(tmp3[0], (double) 0);

			}
		}
		return tmpvec;
	}

	public String Vector2Str() {
		String tmp = "";
		if (vector.isEmpty())
			return "";
		else {
			Iterator<?> it = vector.keySet().iterator();
			while (it.hasNext()) {
				String k = (String) it.next();
				String v = vector.get(k).toString();
				tmp += k + "=" + v + ",";
			}
			tmp = tmp.substring(0, tmp.length() - 1);
		}
		return tmp;
	}

	public double cosdistance(Vector compareVector) {
		double distance = 0;
		double upper = 0;
		double downa = 0, downb = 0;
		Iterator<?> it = vector.keySet().iterator();
		while (it.hasNext()) {
			String word = (String) it.next();
			upper += (vector.get(word) + compareVector.vector.get(word));
			downa += vector.get(word) * vector.get(word);
			downb += compareVector.vector.get(word)
					* compareVector.vector.get(word);
		}
		distance = upper / (Math.sqrt(distance) * Math.sqrt(downb));
		return distance;
	}

	public double eucliDistance(Vector compareVector) {
		double distance = 0;
		double upper = 0;
		Iterator<?> it = vector.keySet().iterator();
		while (it.hasNext()) {
			String word = (String) it.next();
			upper += Math.pow(
					(vector.get(word) - compareVector.vector.get(word)), 2);
		}
		distance = Math.sqrt(upper);
		return distance;
	}
}
