package com.example.restfulwebservice.user;

import com.example.restfulwebservice.post.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonIgnoreProperties(value = {"password"})
// TODO: 2021-08-15 Filter Class 단위로 Response에서 무시하게끔 하는 Annotation
// TODO: 2021-08-15 @JsonFilter가 선언되어 있을때, 사용하지 않는다면 Exception 발생
//@JsonFilter("UserInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO: 2021-08-15 minimum 설정
    // TODO: 2021-08-15 validation의 경우 spring boot 2.3.0 이후 spring-boot-stater-validation으로 이동
    @Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    private String name;

    // TODO: 2021-08-15 미래 데이터를 쓸 수 없고, 과거 데이터만 가능하다는 설정
    @Past
    @ApiModelProperty(notes = "사용자 등록일을 입력해주세요.")
    private Date joinDate;

    // TODO: 2021-08-15 Response에서 무시할 Annotation
    // Class 단위로 @JsonIgnoreProperties 사용 가능
    //    @JsonIgnore
    @ApiModelProperty(notes = "사용자 패스워드를 입력해주세요.")
    private String password;

    //    @JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호를 입력해주세요.")
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(Integer id, String name, Date joinDate, String password, String ssn) {
        this(id, name, joinDate, password, ssn, null);
    }
}
