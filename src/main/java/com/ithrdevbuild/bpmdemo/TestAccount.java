package com.ithrdevbuild.bpmdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;

public final class TestAccount {
    // public static String Url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
    public static String Url = "http://demo.m8solutions.com/sugar/service/v4/rest.php?";
    public static String Username = "admin";
    public static String Password = "demo";

    public static void printJsonObject(Object obj) throws JsonProcessingException {
        printJsonObject(obj.getClass().getSimpleName(), obj);
    }

    public static void printJsonObject(String title, Object obj) throws JsonProcessingException {
        System.out.println("---------" + title + "-------------");
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        String jsonError = mapper.writeValueAsString(obj);
        System.out.println(jsonError);
    }
}
