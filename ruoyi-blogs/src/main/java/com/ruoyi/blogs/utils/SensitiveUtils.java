package com.ruoyi.blogs.utils;

import com.ruoyi.blogs.domain.Comment;
import com.ruoyi.blogs.sensi.SensitiveFilter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hubi
 * @date 2021/8/16
 */
@Component
public class SensitiveUtils {

    private  SensitiveFilter filter = SensitiveFilter.DEFAULT;

    public List<Comment> desensitization(List<Comment> commentList){

        for (Comment comment : commentList) {
            String result = filter.filter(comment.getContent());
            comment.setContent(result);
        }
        return commentList;
    }

    public boolean hasSensitiveWord(String text){
        return filter.hasSensitiveWord(text);
    }

}
