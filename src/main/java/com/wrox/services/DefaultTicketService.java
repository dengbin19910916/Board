package com.wrox.services;

import com.wrox.entities.Attachment;
import com.wrox.entities.Ticket;
import com.wrox.repositories.TicketRepository;
import com.wrox.utils.FileUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService {

    @Inject
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTickets() {
        return this.ticketRepository.getAll();
    }

    @Override
    public Ticket getTicket(long id) {
        return this.ticketRepository.get(id);
    }

    @Override
    public void save(Ticket ticket) {
        if (ticket.getId() < 1) {
            ticket.setDateCreated(Instant.now());
            this.ticketRepository.add(ticket);
        } else {
            this.ticketRepository.update(ticket);
        }
        this.saveAttachmentOnDisc(ticket);
        // Excel文件转换成对象

    }

    /**
     * 将票据保存到磁盘上。
     *
     * @param ticket 票据
     */
    private void saveAttachmentOnDisc(Ticket ticket) {
        String directory = String.format("%s.ticket.attachment.%s", ticket.getCustomerName(), ticket.getSubject());
        for (Attachment attachment : ticket.getAttachments()) {
            FileUtils.createFile(directory, attachment.getName(), attachment.getContents());
        }
    }

    /**
     *
     * @param filename 文件名称
     * @param inputStream 文件输入流
     */
    private void parseExcel(String filename, InputStream inputStream) {

    }
}
