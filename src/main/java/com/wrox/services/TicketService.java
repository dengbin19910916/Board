package com.wrox.services;

import com.wrox.entities.Ticket;

import java.util.List;

public interface TicketService {
    /**
     * 返回所有票据列表。
     *
     * @return 票据列表
     */
    List<Ticket> getAllTickets();

    /**
     * 根据票据号返回票据。
     *
     * @param id 票据号
     * @return 票据
     */
    Ticket getTicket(long id);

    /**
     * 保存票据。
     *
     * @param ticket 新票据对象
     */
    void save(Ticket ticket);
}
