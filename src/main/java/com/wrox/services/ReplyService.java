package com.wrox.services;

import com.wrox.entities.Reply;

import java.util.List;

/**
 * Created by dengb on 2015/9/8.
 */
public interface ReplyService {

    /**
     * 返回某一个讨论的所有回复。
     *
     * @param discussionId 讨论序号
     * @return 某一个讨论所有的回复列表
     */
    List<Reply> getRepliesForDiscussion(long discussionId);

    /**
     * 保存回复对象。
     *
     * @param reply 回复对象
     */
    void saveReply(Reply reply);
}
