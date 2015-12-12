package com.wrox.controllers;

import com.wrox.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/post")
public class PostController {

    private static List<User> users = new ArrayList<>();

    {
        //-----------------------------------------------
        // 设置Entity
        // -----------------------------------------------
        users.add(new User());
        User user = users.get(0);
        user.setId(1);
        user.setName("Robin");
        user.setCreateTime(new Date());
        user.setGirl(true);
        user.setCbx(new String[]{"1", "2", "3"});
        user.setAge(18);
        user.setEmail("abcd@abc.com");
    }

    @RequestMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("person/post");
        view.addObject("users", users);
        return view;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ModelAndView addUser( @Valid User user, BindingResult result) {
        ModelAndView view = new ModelAndView("redirect:/post");

        System.out.println("UUU ==>" + user);

        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError err : errors) {
                System.out.println("ObjectName:" + err.getObjectName() + "\tFieldName:" + err.getField()
                        + "\tFieldValue:" + err.getRejectedValue() + "\tMessage:" + err.getDefaultMessage());
            }
            view.addObject("users", users);
            return view;
        }

        user.setId(users.size() + 1);
        users.add(user);
        view.addObject("users", users);
        return view;
    }
}