package models.rest;

import java.util.List;

public class QueryResponse<T> {

    private final List<T> result;

    public QueryResponse(List<T> t) {
        this.result = t;
    }

}
