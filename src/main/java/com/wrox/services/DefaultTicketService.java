package com.wrox.services;

import com.wrox.entities.Attachment;
import com.wrox.entities.Ticket;
import com.wrox.repositories.TicketRepository;
import com.wrox.utils.FileUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.nio.file.Path;
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
            this.saveAttachmentOnDisc(ticket);
        } else
            this.ticketRepository.update(ticket);
    }

    /**
     * 将票据保存到磁盘上。
     *
     * @param ticket 票据
     */
    private void saveAttachmentOnDisc(Ticket ticket) {
        Path uploadPath = FileUtils.getDirectoryPath(String.format("%s.ticket.attachment", ticket.getCustomerName()));
        for (Attachment attachment : ticket.getAttachments()) {
            FileUtils.createFile(uploadPath, attachment.getName(), attachment.getContents());
        }
    }


}
