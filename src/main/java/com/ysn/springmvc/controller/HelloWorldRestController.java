package com.ysn.springmvc.controller;

import com.ysn.springmvc.model.root.Diagnostic;
import com.ysn.springmvc.model.user.DataUser;
import com.ysn.springmvc.model.user.DataUsers;
import com.ysn.springmvc.model.user.User;
import com.ysn.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
public class HelloWorldRestController {

    @Autowired
    UserService userService;    // Service which will do all data retrieval/manipulation work

    @RequestMapping("/")
    public String root() {
        return "Hello world!";
    }

    /**
     * Root API
     *
     * @return ResponseEntity of Diagnostic
     */
    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Diagnostic> root() {
        try {
            Diagnostic diagnostic = new Diagnostic(HttpStatus.OK, new Date().getTime());
            return new ResponseEntity<Diagnostic>(diagnostic, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Diagnostic>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    /**
     * Retrieve all users API
     *
     * @return ResponseEntity of DataUsers
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<DataUsers> listAllUsers() {
        DataUsers dataUsers = new DataUsers();
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<DataUsers>(HttpStatus.NO_CONTENT);
        }
        Diagnostic diagnostic = new Diagnostic(HttpStatus.OK, new Date().getTime());
        dataUsers.setDiagnostic(diagnostic);
        dataUsers.setUsers(users);
        return new ResponseEntity<DataUsers>(dataUsers, HttpStatus.OK);
    }

    /**
     * Retrieve single user API
     *
     * @param id {long} ID user
     * @return ResponseEntity of DataUser
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataUser> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        DataUser dataUser = new DataUser();
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<DataUser>(HttpStatus.NOT_FOUND);
        }
        Diagnostic diagnostic = new Diagnostic(HttpStatus.OK, new Date().getTime());
        dataUser.setDiagnostic(diagnostic);
        dataUser.setUser(user);
        return new ResponseEntity<DataUser>(dataUser, HttpStatus.OK);
    }

    /**
     * Create a user API
     *
     * @param user                 {User} Value of user
     * @param uriComponentsBuilder {UriComponentsBuilder} Value of uriComponentsBuilder
     * @return ResponseEntity of Void
     */
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Creating User " + user.getName());
        Diagnostic diagnostic = new Diagnostic();
        if (userService.isUserExists(user)) {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    /**
     * Update a user API
     *
     * @param id   {long} ID user
     * @param user {User} Value of user
     * @return ResponseEntity of User
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);
        User currentUser = userService.findById(id);
        if (currentUser == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
        userService.updateUser(user);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    /**
     * Delete a user API
     *
     * @param id {long} ID user
     * @return ResponseEntity of User
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching and deleting User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete all users API
     *
     * @return ResponseEntity of User
     */
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting all Users");
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
