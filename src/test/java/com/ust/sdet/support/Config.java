package com.ust.sdet.support;

public class Config {

    public static  String baseUrl()
    {
        return System.getProperty("baseurl","http://localhost:5173").replaceAll("/$","");
    }
    public static String catalogUrl()
    {
        return baseUrl()+"/catalog";
    }
    public static String loginUrl()
    {
        return baseUrl()+"/login";
    }
    public static String homeUrl(){return baseUrl()+"/home";}

    public static boolean headless()
    {
        return Boolean.parseBoolean(System.getProperty("headless","false"));
    }
}
