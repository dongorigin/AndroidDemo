package cn.dong.demo.model;

import java.util.List;

public class GeoResponse {
    private String status;
    private List<GeoResult> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeoResult> getResults() {
        return results;
    }

    public void setResults(List<GeoResult> results) {
        this.results = results;
    }

}
