package io.botic.response;

import java.util.List;

public class ResultListTimedBenchResult extends TimedBenchResult {
    private List results;

    public ResultListTimedBenchResult() {
    }

    public ResultListTimedBenchResult(String text) {
        super(text);
    }

    public String getResultList() {
        StringBuffer resultSerialized = new StringBuffer(results.size() * 30);
        for (Object result : results) {
            resultSerialized.append(result.toString());
        }

        return resultSerialized.toString();
    }

    public void setResults(List results) {
        this.results = results;
    }
}
