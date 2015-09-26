package com.wrox.controllers;

import com.wrox.controllers.forms.DiscussionForm;
import com.wrox.entities.Discussion;
import com.wrox.services.DiscussionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;

/**
 * Created by dengb on 2015/9/7.
 */
@Controller
@RequestMapping("/discussion")
public class BoardController {

    @Inject
    private DiscussionService discussionService;

    @RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("discussions", this.discussionService.getAllDiscussions());
        return "discussion/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute(new DiscussionForm());
        return "discussion/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(DiscussionForm form) {
        Discussion discussion = new Discussion();
        discussion.setUser(form.getUser());
        discussion.setSubject(form.getSubject());
        discussion.setMessage(form.getMessage());
        this.discussionService.saveDiscussion(discussion);

        // 使用相对路径，不兼容HTTP 1.0
        return new RedirectView("/discussion/" + discussion.getId() + "/" + discussion.getUriSafeSubject(), true, false);
    }
}
