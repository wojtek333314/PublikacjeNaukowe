package com.sciencepublications.controllers;

import com.sciencepublications.models.FileEntity;
import com.sciencepublications.models.PublicationEntity;
import com.sciencepublications.models.TypeOfPublicationsEntity;
import com.sciencepublications.models.UserEntity;
import com.sciencepublications.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@Controller
public class HomeController {

    private static final int PASSWORD_MIN_LENGTH = 4;

    @RequestMapping(value = "/")
    public String home() {
        return "publications";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "logininput", required = false) String login,
                        @RequestParam(value = "passwordinput", required = false) String password,
                        ModelMap model) {
        if (login == null && password == null) {
            return "login";
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        for (UserEntity userEntity : users) {
            if (userEntity.getPassword().equals(password) && userEntity.getLogin().equals(login)) {
                model.addAttribute("result", true);
                model.addAttribute("userId", userEntity.getId());
                if (userEntity.getRole().equals("ADMIN")) {
                    model.addAttribute("isAdmin", true);
                }
                System.out.println(userEntity.getRole() + ":" + userEntity.getLogin() + " with id:" + userEntity.getId() + " is logged in!");
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

    @RequestMapping(value = "/edit{id}")
    public String publicationEdit(@RequestParam("id") int id, ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        model.addAttribute("publication", session.get(PublicationEntity.class, id));
        session.close();
        return "edit";
    }

    //robert
    @RequestMapping(value = "/addTypePublications")
    public String addTypePublications(ModelMap model) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
        return "addTypePublications";
    }
    //robert

    @RequestMapping(value = "/fileDownload")
    public String fileDownload() {
        return "fileDownload";
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
    public String register(@RequestParam(value = "username", required = false) String login,
                           @RequestParam(value = "password", required = false) String password,
                           ModelMap modelMap) {
        if (login == null && password == null) {
            return "register";
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
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
        query.executeUpdate();
        transaction.commit();
        session.close();
        return "login";
    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile")


    public void uploadFileHandler(@RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("file") MultipartFile file,
                                  @RequestParam("userId") Integer userId,
                                  ModelMap model,
                                  HttpServletResponse httpServletResponse) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                Session session = HibernateUtil.getSessionFactory().openSession();
                Criteria criteria = session.createCriteria(UserEntity.class);
                criteria.add(Restrictions.eq("id", userId));
                UserEntity user = (UserEntity) criteria.uniqueResult();

                Transaction transaction = session.beginTransaction();

                FileEntity fileEntity = new FileEntity();
                fileEntity.setFile(bytes);
                fileEntity.setFilename(file.getOriginalFilename());
                Integer fileId = (Integer) session.save(fileEntity);
                transaction.commit();
                session.close();

                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();

                PublicationEntity publicationEntity = new PublicationEntity();
                publicationEntity.setAuthorId(user.getId());
                publicationEntity.setAuthorName(user.getLogin());
                publicationEntity.setDescription(description);
                publicationEntity.setFileId(fileId);
                publicationEntity.setTitle(title);

                Integer id = (Integer) session.save(publicationEntity);
                model.addAttribute("publication", publicationEntity);
                transaction.commit();
                session.close();
                System.out.println(fileId + "!!!");
                httpServletResponse.sendRedirect("/publication?id=" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/download/{documentId}")
    public String download(@PathVariable("documentId")
                                   Integer documentId, HttpServletResponse response) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            FileEntity fileEntity = (FileEntity) session.get(FileEntity.class, documentId);
            response.setHeader("Content-Disposition", "inline;filename=\"" + fileEntity.getFilename() + "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            out.write(fileEntity.getFile());
            out.flush();
            out.close();
            session.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @RequestMapping(value = "/delete{id}")
    public String deletePublication(@RequestParam("id") int id, ModelMap model) {
        System.out.println("I am deleting:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        PublicationEntity publicationEntity = (PublicationEntity) session.get(PublicationEntity.class, id);
        System.out.println("Found publicationEntity to delete!" + publicationEntity.toString());
        session.delete(publicationEntity);
        transaction.commit();
        session.close();
        return "delete";
    }

    //robert i michael
  @RequestMapping(value = "/addType")
    public String addType(@RequestParam("name") String name,
                          @ModelAttribute("dane") ArrayList<String> dane,
                                  ModelMap model) {
            try {
                //System.out.println("asd");
                for(int i=0;i<dane.size();i++) System.out.println("Dane: " + dane.get(i));
                Session session;
                Transaction transaction;
                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();

                Query query = session
                        .createSQLQuery("INSERT INTO TypeOfPublications ( name) VALUES (:name)");
                query.setParameter("name", name);
                query.executeUpdate();
                transaction.commit();

                session.close();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return "addTypePublications";
    }


}
