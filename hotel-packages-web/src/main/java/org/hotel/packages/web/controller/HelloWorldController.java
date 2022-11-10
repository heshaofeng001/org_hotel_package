package org.hotel.packages.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author he_sh
 * @version 2022-11月-05
 **/
@RestController
@RequestMapping("/hotel/packages/test")
public class HelloWorldController extends BaseController{

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
