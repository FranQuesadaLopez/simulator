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
					Vector2D jo1_v = new Vector2D(jo1.getJSONArray("v").getDouble(0), jo1.getJSONArray("v").getDouble(1));
					Vector2D jo2_v = new Vector2D(jo2.getJSONArray("v").getDouble(0), jo2.getJSONArray("v").getDouble(1));
					Vector2D jo1_p = new Vector2D(jo1.getJSONArray("p").getDouble(0), jo1.getJSONArray("p").getDouble(1));
					Vector2D jo2_p = new Vector2D(jo2.getJSONArray("p").getDouble(0), jo2.getJSONArray("p").getDouble(1));
					Vector2D jo1_f = new Vector2D(jo1.getJSONArray("f").getDouble(0), jo1.getJSONArray("f").getDouble(1));
					Vector2D jo2_f = new Vector2D(jo2.getJSONArray("f").getDouble(0), jo2.getJSONArray("f").getDouble(1));
					if(Math.abs(jo1.getDouble("mass") - jo2.getDouble("mass")) > eps ||
					   jo1_v.distanceTo(jo2_v) > eps ||
					   jo1_p.distanceTo(jo2_p) > eps ||
					   jo1_f.distanceTo(jo2_f) > eps ||
					   jo1.getString("id") != jo2.getString("id"))
						break;
					++i;
				}
				if(i == s1_b.length())
					return true;
			}
		}
		return false;
	}

}
