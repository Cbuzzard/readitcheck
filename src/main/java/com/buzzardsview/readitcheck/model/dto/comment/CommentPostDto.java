package com.buzzardsview.readitcheck.model.dto.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentPostDto {

    @NotEmpty
    @Size(max = 3000)
    private String content;

    public CommentPostDto(String content, int submissionId) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
