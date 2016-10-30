package com.sciencepublications.controllers;

import com.sciencepublications.models.UserEntity;
import com.sciencepublications.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        return "publications";
    }

    @RequestMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/publications")
    public String publications(ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
     /*   String singlePublicationHtml = " <tr>\n" +
                "            <th scope=\"row\">1</th>\n" +
                "            <td>TITLE</td>\n" +
                "            <td>NAME</td>\n" +
                "            <td>\n" +
                "                <button class=\"btn btn-primary btn-block center-block\">See more...</button>\n" +
                "            </td>\n" +
                "        </tr>";*/
        /*ArrayList<PublicationEntity> publications = new ArrayList<PublicationEntity>(session.createCriteria(PublicationEntity.class).list());*/
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        System.out.println(users.size()+"!!!!!!!!!!!!!");
        for(UserEntity userEntity : users){
            System.out.println(userEntity.getLogin());
        }
     /*   int i = 0;
        for (PublicationEntity publicationEntity : publications) {
            // model.addAttribute(String.valueOf(i), singlePublicationHtml.replaceFirst("TITLE",
            //          publicationEntity.getTitle()).replaceFirst("NAME", getUserWithId(users, publicationEntity.getUserId()).getLogin()));

            i++;
        }
        model.addAttribute("publications", publications);*/
        return "publications";
    }

    private UserEntity getUserWithId(ArrayList<UserEntity> users, int id) {
        for (UserEntity user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
