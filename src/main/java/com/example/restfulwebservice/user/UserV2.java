package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFilter("UserInfoV2")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserV2 extends User {

    private String grade;

}
