package predictorIA_SUM;

import java.util.Comparator;

public class Struct_queryIdScore {
	int queryId;
	double score;
	
	public int getQueryId() {
		return queryId;
	}
	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

}
class Struct_compare implements Comparator<Struct_queryIdScore>{
	
	@Override
	public int compare(Struct_queryIdScore arg0, Struct_queryIdScore arg1) {
		if(arg0.getQueryId()<arg1.getQueryId())
			return -1;
		if(arg0.getQueryId()>arg1.getQueryId())
			return 1;
		return 0;
	}

}
