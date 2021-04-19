package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	
	private double eps;

	public EpsilonEqualStates(double eps) {
		this.eps = eps;
	}
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		if(s1.getDouble("time") == s2.getDouble("time")) {
			JSONArray s1_b = s1.getJSONArray("bodies");
			JSONArray s2_b = s2.getJSONArray("bodies");
			if(s1_b.length() == s2_b.length()) {
				int i = 0;
				JSONObject jo1;
				JSONObject jo2;
				while(i < s1_b.length()) {
					jo1 = s1_b.getJSONObject(i);
					jo2 = s2_b.getJSONObject(i);
					if( Math.abs(jo1.getDouble("m") - jo2.getDouble("m")) > eps ||
						!epsEqualVectors(jo1.getJSONArray("v"), jo2.getJSONArray("v")) ||
						!epsEqualVectors(jo1.getJSONArray("p"), jo2.getJSONArray("p")) ||
						!epsEqualVectors(jo1.getJSONArray("f"), jo2.getJSONArray("f")) ||
					    !(jo1.getString("id").equals(jo2.getString("id"))))
						break;
					++i;
				}
				if(i == s1_b.length())
					return true;
			}
		}
		return false;
	}
	
	private boolean epsEqualVectors(JSONArray a1, JSONArray a2) {
		if (a1.length() != a2.length())
			return false;

		Vector2D v1 = new Vector2D(a1.getDouble(0), a1.getDouble(1));
		Vector2D v2 = new Vector2D(a2.getDouble(0), a2.getDouble(1));

		return (v1.distanceTo(v2) <= eps);
	}

}
