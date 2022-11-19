package com.roman.nett.dto.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roman.nett.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Date;


public interface PostPro {
    Long getId();
    String getText();
    @JsonIgnore User getUser();
    Date getCreated();

    default Long getUserId() {
        return getUser().getId();
    }
}
