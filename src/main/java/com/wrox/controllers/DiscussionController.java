package com.wrox.controllers;

import com.wrox.controllers.forms.ReplyForm;
import com.wrox.entities.Discussion;
import com.wrox.entities.Reply;
import com.wrox.services.DiscussionService;
import com.wrox.services.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;

/**
 * Created by dengb on 2015/9/7.
 */
@Controller
@RequestMapping("/discussion/{discussionId:\\d+}")
public class DiscussionController {

    @Inject
    private DiscussionService discussionService;
    @Inject
    private ReplyService replyService;

    @RequestMapping(value = {"", "*"}, method = RequestMethod.GET)
    public String viewDiscussion(Model model, @PathVariable("discussionId") long discussionId) {
        Discussion discussion = this.discussionService.getDiscussion(discussionId);
        if (discussion != null) {
            model.addAttribute(discussion);
            model.addAttribute("replies", this.replyService.getRepliesForDiscussion(discussionId));
            model.addAttribute("replyForm", new ReplyForm());

            return "discussion/view";
        }

        return "discussion/errorNoDiscussion";
    }

    @RequestMapping(value = "reply", method = RequestMethod.POST)
    public ModelAndView reply(ReplyForm form, @PathVariable("discussionId") long discussionId) {
        Discussion discussion = this.discussionService.getDiscussion(discussionId);
        if (discussion != null) {
            Reply reply = new Reply();
            reply.setDiscussionId(discussionId);
            reply.setUser(form.getUser());
            reply.setMessage(form.getMessage());
            this.replyService.saveReply(reply);

            return new ModelAndView(new RedirectView("/discussion/" + discussionId + "/" + discussion.getUriSafeSubject(), true, false));
        }

        return new ModelAndView("discussion/errorNoDiscussion");
    }
}
