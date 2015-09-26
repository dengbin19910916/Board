package com.wrox.repositories;

import com.wrox.entities.Reply;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dengb on 2015/9/7.
 */
@Repository
public interface ReplyRepository {

    /**
     * 通过讨论序号查找所有的回复。
     *
     * @param discussionId 讨论序号
     * @return 回复列表
     */
    List<Reply> getForDiscussion(long discussionId);

    /**
     * 添加一个回复。
     *
     * @param reply 回复对象
     */
    void add(Reply reply);

    /**
     * 更新某一个回复对象。
     *
     * @param reply 新回复对象。
     */
    void update(Reply reply);

    /**
     * 通过某一个讨论序号删除所有回复。
     *
     * @param discussionId 讨论序号
     */
    void deleteForDiscussion(long discussionId);
}
