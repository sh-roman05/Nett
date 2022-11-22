package com.roman.nett.dto.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roman.nett.model.entity.Role;

import java.util.Date;
import java.util.List;

public interface UserView {
    String getUsername();
    String getEmail();
    String getFirstName();
    String getLastName();
    String getAboutMe();
    Date getCreated();
    @JsonIgnore
    List<Role> getRoles();

    default List<Long> getRolesId() {
        return getRoles().stream()
                .map(Role::getId)
                .toList();
    }
}
