package com.wrox.services;

import com.wrox.entities.Discussion;
import com.wrox.entities.Reply;
import com.wrox.repositories.ReplyRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;

/**
 * Created by dengb on 2015/9/8.
 */
@Service
public class DefaultReplyService implements ReplyService {

    @Inject
    private ReplyRepository replyRepository;
    @Inject
    private DiscussionService discussionService;

    @Override
    public List<Reply> getRepliesForDiscussion(long discussionId) {
        List<Reply> list = this.replyRepository.getForDiscussion(discussionId);
        list.sort((r1, r2) -> r1.getId() < r2.getId() ? -1 : 1);

        return list;
    }

    @Override
    public void saveReply(Reply reply) {
        Discussion discussion = this.discussionService.getDiscussion(reply.getDiscussionId());
        if (reply.getId() < 1) {
            discussion.getSubscribedUsers().add(reply.getUser());
            reply.setCreated(Instant.now());
            this.replyRepository.add(reply);
//
//            Set<String> recipients = new HashSet<>(discussion.getSubscribedUsers());
//            recipients.remove(reply.getUser());
        } else {
            this.replyRepository.update(reply);
        }

        this.discussionService.saveDiscussion(discussion);
    }
}
