package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.entity.RecipeComment;
import com.jamedow.laodoufang.entity.RecipeCommentExample;
import com.jamedow.laodoufang.entity.VoteLog;
import com.jamedow.laodoufang.mapper.RecipeCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ydy on 2017/2/15.
 */
@Service
public class CommentService {

    @Autowired
    private RecipeCommentMapper recipeCommentMapper;
    @Autowired
    private VoteService voteService;

    public List<RecipeComment> getRecipeComments(Integer recipeId, Integer userId) {
        RecipeCommentExample example = new RecipeCommentExample();
        example.createCriteria().andRecipeIdEqualTo(recipeId);
        example.setOrderByClause("rc.is_picked desc,rc.vote_up desc,rc.create_time desc");
        List<RecipeComment> comments = recipeCommentMapper.getCommentsByRecipeId(example);

        for (RecipeComment comment : comments) {
            //用户当前点赞状态
            int voteStatus = 0;
            if (userId != null) {
                voteStatus = voteService.getVoteStatus(comment.getId(), userId);
            }
            comment.setVoteStatus(voteStatus);
            //点赞总量
            //TODO 点赞总量缓存至redis
            long voteCount = voteService.getVoteCount(comment.getId(), VoteLog.VoteStatus.UP_VOTE.getStatus());
            comment.setVoteCount(voteCount);
        }
        return comments;
    }

    public int deleteRecipeCommentById(Integer id) {
        return recipeCommentMapper.deleteByPrimaryKey(id);
    }
}
