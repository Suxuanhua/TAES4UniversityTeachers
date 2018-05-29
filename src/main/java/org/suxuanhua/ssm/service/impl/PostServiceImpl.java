package org.suxuanhua.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suxuanhua.ssm.mapper.post.PostMapper;
import org.suxuanhua.ssm.po.post.Post;
import org.suxuanhua.ssm.service.PostService;
import org.suxuanhua.ssm.tools.TAES4Utils;

import java.util.List;

/**
 * @author XuanhuaSu
 * @version 2018/5/7
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public boolean savePostInfo(Post post) throws Exception {
        if (postMapper.insertPost (post) > 0)
            return true;
        else return false;
    }

    @Override
    public Post findPostByID(String postID) throws Exception {
        return postMapper.findPostById (postID);
    }

    @Override
    public List<Post> findAllPost() {
        return TAES4Utils.loadXMLCreatePostList (
                TAES4Utils.getPropertiesValue (
                        "/TAES4UniversityTeachers-Configuration.properties",
                        "TAES4UT_XML_POSTLIST_PATH") + "/TAES4UT_POSTLIST.xml");
    }

    @Override
    public boolean deletePostByID(String postID) {
        return false;
    }

    @Override
    public Integer addVisits(String postID) throws Exception {
        return postMapper.addVisits (postID);
    }
}
