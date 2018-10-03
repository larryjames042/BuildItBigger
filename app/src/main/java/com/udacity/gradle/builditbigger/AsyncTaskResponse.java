package com.udacity.gradle.builditbigger;

public interface AsyncTaskResponse {
    String processFinish(String response);
    void beforeProcess();
}
