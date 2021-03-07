package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

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
					if(jo1.getDouble("mass") != jo2.getDouble("mass") || jo1.getString("id") != jo2.getString("id"))
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
