package com.sciencepublications.controllers;

import com.sciencepublications.models.PublicationEntity;
import com.sciencepublications.models.UserEntity;
import com.sciencepublications.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.POST;
import java.util.ArrayList;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        return "publications";
    }

    @RequestMapping(value = "/login")
    @POST
    public String login(@RequestParam(value = "logininput", required = false) String login,
                        @RequestParam(value = "passwordinput", required = false) String password,
                        ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        for (UserEntity userEntity : users) {
            if (userEntity.getPassword().equals(password) && userEntity.getLogin().equals(login)) {
                model.addAttribute("result", true);
                model.addAttribute("userId", userEntity.getId());
                System.out.println("User:" + userEntity.getLogin() + " with id:" + userEntity.getId() + " is logged in!");
                break;
            }
        }
        session.close();
        return "login";
    }

    @RequestMapping(value = "/publications")
    public String publications(ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<PublicationEntity> publications = new ArrayList<PublicationEntity>(session.createCriteria(PublicationEntity.class).list());
        model.addAttribute("publications", publications);
        session.close();
        return "publications";
    }

    @RequestMapping(value = "/publication{id}")
    public String publicationDetails(@RequestParam("id") int id, ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        PublicationEntity publicationEntity = (PublicationEntity) session.get(PublicationEntity.class, id);
        model.addAttribute("publication", publicationEntity);
        session.close();
        return "publicationDetails";
    }

    @RequestMapping(value = "/create")
    public String createPublication() {
        return "createPublication";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "/register")
    @POST
    public String register(@RequestParam(value = "username", required = false) String login,
                           @RequestParam(value = "password", required = false) String password,
                           ModelMap modelMap) {
        if (login == null && password == null) {
            return "register";
        }
        if (password.length() < 4) {
            modelMap.addAttribute("passwordTooShort", "<p style=\"color:red;\">Password is too short!</p>");
            return "register";
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        for (UserEntity userEntity : users) {
            if (userEntity.getPassword().equals(password) && userEntity.getLogin().equals(login)) {
                modelMap.addAttribute("userExist", "<p style=\"color:red;\">User is exist!</p>");
                session.close();
                return "register";
            }
        }
        Transaction transaction = session.beginTransaction();
        Query query = session
                .createSQLQuery("INSERT INTO user (login, password) VALUES (:login1, :password1)");
        query.setParameter("login1", login);
        query.setParameter("password1", password);
        System.out.println(query.executeUpdate());
        transaction.commit();
        session.close();
        return "login";
    }
}
