package com.roman.nett.dto.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roman.nett.model.entity.User;

import java.util.Date;


public interface PostView {
    Long getId();
    String getText();
    @JsonIgnore User getUser();
    Date getCreated();

    default Long getUserId() {
        return getUser().getId();
    }
}
