package com.sciencepublications.controllers;

import com.sciencepublications.MailMail;
import com.sciencepublications.models.FileEntity;
import com.sciencepublications.models.PublicationEntity;
import com.sciencepublications.models.UserConfirmEntity;
import com.sciencepublications.models.UserEntity;
import com.sciencepublications.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    MailMail mailMail;

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
            System.out.println(userEntity.getLogin() + ":"+login);
            if (userEntity.getPassword().equals(password) && userEntity.getLogin().equals(login)) {
                model.addAttribute("result", true);
                model.addAttribute("userId", userEntity.getId());
                if (userEntity.getRole().equals("ADMIN")) {
                    System.out.println("Is admin!");
                    model.addAttribute("isAdmin", true);
                }
                if(userEntity.isConfirmed()){
                    model.addAttribute("confirmed");
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
                           @RequestParam(value = "email", required = false) String email,
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
        String randomUUID = UUID.randomUUID().toString();
        Query query = session
                .createSQLQuery("INSERT INTO user (login, password) VALUES (:login1, :password1)");
        query.setParameter("login1", login);
        query.setParameter("password1", password);
        query.executeUpdate();
        Long lastId;
        lastId = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
        transaction.commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        UserConfirmEntity userConfirmEntity = new UserConfirmEntity();
        userConfirmEntity.setHash(randomUUID);
        userConfirmEntity.setUserId(lastId.intValue());
        System.out.println("new user id:" + lastId);
        session.save(userConfirmEntity);
        transaction.commit();
        session.close();

        mailMail.sendMail("sciencepublications@no-spam.com",
                email,
                "Confirmation link",
                "Click to confirm account: " + "http://panel11.mydevil.net:5521/confirm?hash=" + randomUUID);
        return "login";
    }

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
    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public String addType(@RequestParam("name") String name,
                          @RequestParam(value = "item") String[] dane,
                          @RequestParam(value = "dane_checkbox") String[] dane_checkbox,
                          ModelMap model) {
        try {
            Session session;
            session = HibernateUtil.getSessionFactory().openSession();
            System.out.println(dane_checkbox.length + "@??@" + dane.length);
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO TypeOfPublications ( name) VALUES (:name)");
            query.setParameter("name", name);
            transaction.commit();

            transaction = session.beginTransaction();
            Long lastId;
            lastId = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
            transaction.commit();
            int position = 0;
            for (String aDane : dane) {
                transaction = session.beginTransaction();

                query = session.createSQLQuery("INSERT INTO Attrubute (id_type,name,optional) VALUES (:id_type,:name,:optional)");
                query.setParameter("name", aDane);
                query.setParameter("id_type", lastId);
                query.setParameter("optional", dane_checkbox[position]);
                System.out.println(dane_checkbox[position]);
                query.executeUpdate();
                transaction.commit();
                position++;
            }
            session.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "addTypePublications";
    }

    @RequestMapping(value = "/confirm{hash}")
    public String confirmUser(@RequestParam("hash") String hash,
                              ModelMap model, HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("I am confirming hash:" + hash);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<UserEntity> users = new ArrayList<UserEntity>(session.createCriteria(UserEntity.class).list());
        ArrayList<UserConfirmEntity> userConfirmEntities = new ArrayList<UserConfirmEntity>(session.createCriteria(UserConfirmEntity.class).list());

        for (UserConfirmEntity userConfirmEntity : userConfirmEntities) {
            if (userConfirmEntity.getHash().equals(hash)) {
                for (UserEntity userEntity : users) {
                    if (userEntity.getId() == userConfirmEntity.getUserId()) {
                        userEntity.setConfirmed(true);
                        session.update(userEntity);
                        session.delete(userConfirmEntity);
                        System.out.println(hash + " confirmed!");
                        model.addAttribute("isConfirmed", true);
                        transaction.commit();
                        session.close();
                        httpServletResponse.sendRedirect("/login?isConfirmed=true");
                        return "login";
                    }
                }
            }
        }
        transaction.commit();
        session.close();
        return "login";
    }

}
