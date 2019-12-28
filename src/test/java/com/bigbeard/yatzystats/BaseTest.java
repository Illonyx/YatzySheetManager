package com.bigbeard.yatzystats;

import org.apache.log4j.PropertyConfigurator;

public class BaseTest {

    public BaseTest(){
        PropertyConfigurator.configure("log4j.properties");
    }

}
