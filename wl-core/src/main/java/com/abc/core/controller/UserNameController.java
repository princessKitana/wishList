package com.abc.core.controller;

import com.abc.api.common.v1.UserDTO;
import com.abc.api.common.v1.UsersRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserNameController {

    @PostMapping(value = "/parseNames")
    public ResponseEntity<String> parseNames(@RequestBody UsersRequest request) {
        List<UserDTO> users = request.getUsers();

        String response = "";
        for (UserDTO user : users) {
            response = String.format("%s%s, ", response, user.getName());
        }
        return ResponseEntity.ok(formatStringEnd(response));
    }

    private static String formatStringEnd(String str) {
        return str.substring(0, str.length() - 2) + ";";
    }
}
