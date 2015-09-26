package com.wrox.repositories;

import com.wrox.entities.Reply;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存数据库
 *
 * Created by dengb on 2015/9/7.
 */
@Repository
public class InMemoryReplyRepository implements ReplyRepository {

    private final Map<Long, Reply> database = new ConcurrentHashMap<>();
    private volatile long replyIdSequence = 1L;

    @Override
    public List<Reply> getForDiscussion(long discussionId) {
        List<Reply> list = new ArrayList<>(database.values());
        list.removeIf(reply -> reply.getDiscussionId() != discussionId);    // 过滤掉所有不属于此讨论的所有回复

        return list;
    }

    @Override
    public synchronized void add(Reply reply) {
        if (reply != null) {
            reply.setId(this.getNextReplyId());
            this.database.put(reply.getDiscussionId(), reply);
        }
    }

    @Override
    public synchronized void update(Reply reply) {
        if (reply != null) {
            this.database.put(reply.getId(), reply);
        }
    }

    @Override
    public synchronized void deleteForDiscussion(long discussionId) {
        this.database.entrySet().removeIf(entry -> entry.getValue().getDiscussionId() == discussionId);
    }

    /**
     * 返回下一个回复序号。
     *
     * @return 下一个回复序号
     */
    private synchronized long getNextReplyId() {
        return this.replyIdSequence++;
    }
}
