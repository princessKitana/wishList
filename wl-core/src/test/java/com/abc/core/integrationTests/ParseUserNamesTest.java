package com.abc.core.integrationTests;

import com.abc.api.common.v1.UserDTO;
import com.abc.api.common.v1.UsersRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParseUserNamesTest extends TestHelper {

    @Test
    public void parseUserNamesSuccess() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                parseUserNamesEndPoint,
                getUsersRequest(),
                String.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
        assertEquals("johnsmith, angelinasmith, adamivanov;", response.getBody());
    }

    @Test
    public void parseUserNamesFail() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                parseUserNamesEndPoint,
                "Incorrect json",
                String.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }

    private UsersRequest getUsersRequest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setType("user");
        userDTO.setId(1222236L);
        userDTO.setName("johnsmith");
        userDTO.setEmail("jsmith@example.com");

        List<UserDTO> users = new ArrayList<>();
        users.add(userDTO);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setType("user");
        userDTO1.setId(2544679L);
        userDTO1.setName("angelinasmith");
        userDTO1.setEmail("asmith@example.com");
        users.add(userDTO1);

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setType("user");
        userDTO2.setId(2524459L);
        userDTO2.setName("adamivanov");
        userDTO2.setEmail("aivanov@another.org");
        users.add(userDTO2);

        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setUsers(users);

        return usersRequest;
    }
}
