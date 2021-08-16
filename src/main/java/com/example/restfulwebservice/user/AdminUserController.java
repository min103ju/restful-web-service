package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id", "name", "joinDate", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // v1 api
    // TODO: 2021-08-16 API version 관리 방법
    // TODO: 2021-08-16 1. api uri 안에 version을 명시
    // @GetMapping("/v1/users/{id}")
    // TODO: 2021-08-16 2. params로 api 버전 관리
    // @GetMapping(value = "/users/{id}", params = "version=1")
    // TODO: 2021-08-16 3. Http header로 version 명시
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    // TODO: 2021-08-16 4. MIME type으로 version을 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // TODO: 2021-08-15 특정 로직 별 Filter를 적용하고자 할때는 SimpleBeanPropertyFilter, SimpleFilterProvider를 사용
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // TODO: 2021-08-15 SimpleFilterProvider().addFilter() 시에 첫번째 인자에
        // TODO: 2021-08-15 도메인 객체에 Annotation @JsonFilter에 선언한 Alias를 기재한다.
        // TODO: 2021-08-15 어떠한 객체를 대상으로 할 것인지에 대한 Alias.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // TODO: 2021-08-15 Response할 user객체에 Filter를 적용하기 위해 MappingJacksonValue를 사용한다.
        // TODO: 2021-08-15 객체를 직렬화하는 홀더
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    // @GetMapping("/v2/users/{id}")
    // @GetMapping(value = "/users/{id}", params = "version=2")
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> UserV2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // TODO: 2021-08-15 특정 로직 별 Filter를 적용하고자 할때는 SimpleBeanPropertyFilter, SimpleFilterProvider를 사용
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id", "name", "joinDate", "grade");

        // TODO: 2021-08-15 SimpleFilterProvider().addFilter() 시에 첫번째 인자에
        // TODO: 2021-08-15 도메인 객체에 Annotation @JsonFilter에 선언한 Alias를 기재한다.
        // TODO: 2021-08-15 어떠한 객체를 대상으로 할 것인지에 대한 Alias.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        // TODO: 2021-08-15 Response할 user객체에 Filter를 적용하기 위해 MappingJacksonValue를 사용한다.
        // TODO: 2021-08-15 객체를 직렬화하는 홀더
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }

}
