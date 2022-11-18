package com.roman.nett.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public interface UserPro {
    String getUsername();
    String getEmail();
    String getFirstName();
    String getLastName();
    String getAboutMe();
    Date getCreated();
}
