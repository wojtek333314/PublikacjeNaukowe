package com.sciencepublications.controllers;

import com.sciencepublications.models.UserEntity;
import com.sciencepublications.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        return "test";
    }

    @RequestMapping(value = "/login")
    public String login(Model model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        int i = 0 ;
        for (UserEntity userEntity : users) {
            System.out.println(userEntity.getLogin() + "/" + userEntity.getPassword());
            model.addAttribute("user"+i, userEntity.getLogin());
            model.addAttribute("pass"+i, userEntity.getPassword());
        }
        return "login";
    }

}
