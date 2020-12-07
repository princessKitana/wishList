package com.abc.core.integrationTests;

import com.abc.api.common.v1.WishDTO;
import com.abc.api.error.v1.ApplicationExceptionDTO;
import com.abc.core.enums.BusinessRules;
import com.abc.core.enums.WishStatus;
import com.abc.core.serviceTests.ApplicationError;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetWishListTest extends AddWishListTest {

    @Test
    public void getWishSuccess() {
        addWishSuccess();

        ResponseEntity<WishDTO> response = restTemplate.getForEntity(
                getWishEndPoint + wishId,
                WishDTO.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(WishStatus.NEW.name(), response.getBody().getStatus());
        assertEquals(wishText, response.getBody().getWish());
    }

    @Test
    public void getAllWishesSuccess() {
        addWishSuccess();
        addWishSuccess();

        ResponseEntity<List<WishDTO>> response = restTemplate.exchange(
                getAllWishesEndPoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WishDTO>>() {
                });

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<WishDTO> wishes = response.getBody();
        assertEquals(2, wishes.size());
        Assert.assertNotEquals(wishes.get(0).getWish(), wishes.get(1).getWish());
    }

    @Test
    public void getWishNotExistingInDB() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                    getWishEndPoint + 99999,
                    String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ApplicationExceptionDTO appEx = convertToAppException(response.getBody());
        List<ApplicationError> errors = appEx.getErrors();
        assertEquals(1, errors.size());
        assertEquals(BusinessRules.BR_3001.name(), errors.get(0).getStatusCode());
        assertEquals(BusinessRules.BR_3001.getMessage(), errors.get(0).getDescription());
    }

    @Test
    public void getWishNotNumericId() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                getWishEndPoint + "not numeric id",
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApplicationExceptionDTO appEx = convertToAppException(response.getBody());
        List<ApplicationError> errors = appEx.getErrors();
        assertEquals(1, errors.size());
        assertEquals(BusinessRules.BR_3002.name(), errors.get(0).getStatusCode());
        assertEquals(BusinessRules.BR_3002.getMessage(), errors.get(0).getDescription());
    }

}
