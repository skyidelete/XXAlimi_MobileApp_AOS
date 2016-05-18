package com.anonyblah.aos.mobapp.xxalimi.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Kloc on 2016-05-18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    private ResponseData responseData;

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return this.responseData;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ResponseData {

        private List<SearchResult> results;

        public void setResults(List<SearchResult> results) {
            this.results = results;
        }

        public List<SearchResult> getResults() {
            return this.results;
        }

    }
}
