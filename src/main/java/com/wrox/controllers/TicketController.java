package com.wrox.controllers;

import com.wrox.controllers.forms.DownloadingView;
import com.wrox.entities.Attachment;
import com.wrox.entities.Ticket;
import com.wrox.services.TicketService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * 处理票据。
 *
 * Created by dengb on 2015/9/9.
 */
@Controller
@RequestMapping("ticket")
public class TicketController {
    private static final Logger log = LogManager.getLogger();

    @Inject
    private TicketService ticketService;

    /**
     * 显示所有的票据。
     *
     * @param model 数据的Map集合
     * @return 票据列表的视图名
     */
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("显示票据列表。");
        model.addAttribute("tickets", this.ticketService.getAllTickets());

        return "ticket/list";
    }

    /**
     * 根据票据号显示某一个票据详细信息。
     *
     * @param model 数据的Map集合
     * @param ticketId 票据号
     * @return 票据详细信息视图
     */
    @RequestMapping(value = "view/{ticketId}", method = RequestMethod.GET)
    public ModelAndView view(Model model, @PathVariable("ticketId") long ticketId) {
        Ticket ticket = this.ticketService.getTicket(ticketId);
        if (ticket == null) {
            return this.getListRedirectModelAndView();
        }
        model.addAttribute("ticketId", ticketId);
        model.addAttribute(ticket);

        return new ModelAndView("ticket/view");
    }

    /**
     * 创建票据表单。
     *
     * @param model 数据的Map集合
     * @return 新票据的视图名
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("ticketForm", new Form());
        return "ticket/add";
    }

    /**
     * 提交票据表单。
     *
     * @param principal 身份对象
     * @param form 票据表单
     * @return 某一个票据明细的视图名
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Principal principal, Form form) throws IOException {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(principal.getName());
        ticket.setSubject(form.getSubject());
        ticket.setBody(form.getBody());

        for (MultipartFile filePart : form.getAttachments()) {
            log.debug("处理新票据的附件。");
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            try {
                attachment.setContents(filePart.getBytes());
            } catch (IOException e) {
                log.warn("文件上传失败！ ", e);
            }
            if (StringUtils.isNotEmpty(attachment.getName()) || ArrayUtils.isNotEmpty(attachment.getContents())) {
                ticket.addAttachment(attachment);
            }
        }
        this.ticketService.save(ticket);

        return "redirect:/ticket/view/" + ticket.getId();
    }

    /**
     * 下载票据中的附件。
     *
     * @param ticketId 票据号
     * @param name 附件名称
     * @return 下载视图对象
     */
    @RequestMapping("{ticketId:\\d+}/attachment/{attachment:.+}")
    public View download(@PathVariable("ticketId") long ticketId, @PathVariable("attachment") String name) {
        Ticket ticket = this.ticketService.getTicket(ticketId);
        if (ticket == null) {
            return this.getListRedirectView();
        }

        Attachment attachment = ticket.getAttachment(name);
        if (attachment == null) {
            log.info("此票据{}未发现附件{}。", ticket, name);
            return this.getListRedirectView();
        }

        return new DownloadingView(attachment.getName(), attachment.getMimeContentType(), attachment.getContents());
    }

    /**
     * 返回跳转至票据列表的模式视图
     *
     * @return 票据列表模式视图
     */
    private ModelAndView getListRedirectModelAndView() {
        return new ModelAndView(this.getListRedirectView());
    }

    /**
     * 返回跳转至票据列表的视图
     *
     * @return 票据列表视图
     */
    private View getListRedirectView() {
        return new RedirectView("/ticket/list", true, false);
    }

    /**
     * 票据表单
     */
    public static class Form {
        /**
         * 主题
         */
        private String subject;
        /**
         * 内容
         */
        private String body;
        /**
         * 附件
         */
        private List<MultipartFile> attachments;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        @SuppressWarnings("unused")
        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }
}
