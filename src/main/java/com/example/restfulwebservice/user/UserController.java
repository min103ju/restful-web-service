package com.example.restfulwebservice.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        // TODO: 2021-08-16 spring version 2.2 이전에는 Resource를 사용했으나
        // TODO: 2021-08-16 spring version 2.2 이후에는 EntityModel을 사용
        EntityModel<User> entity = EntityModel.of(user);

        // TODO: 2021-08-16 추가적인 정보(link)를 Hypermedia 타입으로 설정하기 위해
        // TODO: 2021-08-16 spring version 2.2 이전에는 ControllerLinkBuilder를 사용했으나
        // TODO: 2021-08-16 spring version 2.2 이후에는 WebMvcLinkBuilder를 사용
        // TODO: 2021-08-16 현재 Class의 특정 메소드를 link 한다.
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        // TODO: 2021-08-16 반환 Entity에 link 정보를 기재
        entity.add(linkTo.withRel("all-users"));

        return entity;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();

        // TODO: 2021-08-15 HATEOAS의 일종인지 확인 후 README에 내용 작성
        // 생성된 User에 대한 조회를 하기 위한 uri를 return한다.
        // HATEOAS의 일종?
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }


}
