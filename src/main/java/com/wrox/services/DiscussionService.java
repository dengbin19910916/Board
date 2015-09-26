package com.wrox.services;

import com.wrox.entities.Discussion;

import java.util.List;

/**
 * Created by dengb on 2015/9/7.
 */
public interface DiscussionService {

    /**
     * 返回所有的讨论。
     *
     * @return 所有讨论的列表
     */
    List<Discussion> getAllDiscussions();

    /**
     * 根据讨论序号返回讨论对象。
     *
     * @param id 讨论序号
     * @return 讨论对象
     */
    Discussion getDiscussion(long id);

    /**
     * 保存一个新的讨论对象。
     *
     * @param discussion 新讨论对象
     */
    void saveDiscussion(Discussion discussion);
}
