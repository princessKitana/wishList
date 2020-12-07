package com.abc.core.integrationTests;

import com.abc.api.common.v1.WishDTO;
import com.abc.api.error.v1.ApplicationExceptionDTO;
import com.abc.core.CoreApplication;
import com.abc.core.repository.WishRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {CoreApplication.class})
public abstract class TestHelper {

    protected String wishId;
    protected String wishStatus;
    protected String wishText;

    protected String addWishEndPoint = "/wishList/add";
    protected String getWishEndPoint = "/wishList/get/";
    protected String getAllWishesEndPoint = "/wishList/getAll";
    protected String updateWishEndPoint = "/wishList/update/";
    protected String deleteWishEndPoint = "/wishList/delete/";

    protected String parseUserNamesEndPoint = "/users/parseNames";

    @LocalServerPort
    public String port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected WishRepository wishRepository;

    @Before
    public void setUp() {
        wishRepository.deleteAll();
    }

    protected ApplicationExceptionDTO convertToAppException(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, ApplicationExceptionDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("JSON parsing exception", e);
        }
    }

    protected WishDTO getWishDTO() {
        WishDTO WishDTO = new WishDTO();
        wishText="White coffee mug " + RandomString.make(5);
        WishDTO.setWish(wishText);
        return WishDTO;
    }

}
