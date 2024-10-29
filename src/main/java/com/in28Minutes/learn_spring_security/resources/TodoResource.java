package com.in28Minutes.learn_spring_security.resources;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final List<Todo> TODOS_LIST =List.of(new Todo("in28minutes","Learn AWS"),
            new Todo("in28minutes","Get AWS Certified"));

    @GetMapping("/todos")
    public List<Todo> retrieveAllTodos() {
        return TODOS_LIST;
    }

    // Pre Authorize makes sure the user that sent the request has the Role USER and that the path variable is equal
    // to the authentication name (in28minutes in our case for example)
    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authentication.name")
    // Post Authorize verifies the result and makes sure that it has effectively returned it based on the condition
    @PostAuthorize("returnObject.username == 'in28minutes'")
    // The @RolesAllowed annotation is the JSR-250’s equivalent annotation of the @Secured annotation
    //Basically, we can use the @RolesAllowed annotation in a similar way as @Secured.
    @RolesAllowed({"ADMIN","USER"})
    // The @Secured annotation is used to specify a list of roles on a method. So, a user only can access that method if
    // he has at least one of the specified roles.
    @Secured({"ADMIN","USER"})
    public Todo retrieveTodosForASpecificUser(@PathVariable String username) {
        return TODOS_LIST.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void createTodoForSpecificUser(@PathVariable String username,@RequestBody Todo todo) {
        logger.info("Create {} for {}",todo,username);
    }
}

record Todo(String username,String description ){}