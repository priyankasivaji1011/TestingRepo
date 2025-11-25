package com.test.service;

import com.evry.bdd.utils.ApiUtils;
import com.evry.bdd.utils.ConfigReader;

import io.restassured.response.Response;

public class LoginService {

    public Response login(String payload) {
        String endpoint = ConfigReader.getProperty("base.url") + "/login";
        return ApiUtils.post(endpoint, payload);
    }
}
