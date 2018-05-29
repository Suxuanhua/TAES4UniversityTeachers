package org.suxuanhua.ssm.service;

import org.suxuanhua.ssm.po.post.Post;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/5/7
 */
public interface PostService {
    boolean savePostInfo(Post post) throws Exception;
    Post findPostByID(String postID) throws Exception;
    List<Post> findAllPost() throws Exception;
    boolean deletePostByID(String postID) throws Exception;
    Integer addVisits(String postID) throws Exception;
}
