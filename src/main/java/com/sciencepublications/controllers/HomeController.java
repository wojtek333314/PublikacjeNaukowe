package com.sciencepublications.controllers;

import com.sciencepublications.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println(session);
        return "test";
    }

    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }

}
