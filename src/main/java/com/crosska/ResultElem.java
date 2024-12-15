package com.crosska;

public class ResultElem {

    private final String search;
    private final String[] result;
    private final long time;

    public ResultElem(String search, String[] result, long time) {
        this.search = search;
        this.result = result;
        this.time = time;
    }

    public String getSearch() {
        return search;
    }

    public String[] getResult() {
        return result;
    }

    public long getTime() {
        return time;
    }

}
