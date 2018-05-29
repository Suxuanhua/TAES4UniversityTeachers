package org.suxuanhua.ssm.mapper.post;

import org.suxuanhua.ssm.po.post.Post;

import java.util.List;

/**
 * MyBatis Dao 原始开发方式
 *
 * @author XuanhuaSu
 * @version 2018/4/2m
 */
public interface PostMapper {

    List<Integer> seleteAllId() throws Exception;

    Post findPostById(final String pid) throws Exception;

    List<Post> findPostListByName(final String name) throws Exception;

    List<Post> findAllPost() throws Exception;

    Integer insertPost(final Post teacher) throws Exception;

    boolean deletePostById(final String pid) throws Exception;

    boolean updatePost(final Post teacher) throws Exception;

    Integer addVisits(final String postID) throws Exception;
}