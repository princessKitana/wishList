package com.abc.core.integrationTests;

import com.abc.api.common.v1.WishDTO;
import com.abc.api.error.v1.ApplicationExceptionDTO;
import com.abc.core.serviceTests.ApplicationError;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.abc.core.enums.BusinessRules.BR_3000;
import static org.junit.Assert.assertEquals;

public class AddWishListTest extends TestHelper {

    @Test
    public void addWishSuccess() {
        ResponseEntity<WishDTO> response = restTemplate.postForEntity(
                addWishEndPoint,
                getWishDTO(),
                WishDTO.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assert.assertNotNull(response.getBody().getId());
        wishId = response.getBody().getId();

        Assert.assertNotNull(response.getBody().getStatus());
        wishStatus = response.getBody().getStatus();
    }

    @Test
    public void addWhenMandatoryFieldWishIsMissing() {
        WishDTO wish = getWishDTO();
        wish.setWish(null);
        ResponseEntity<String> response = restTemplate.postForEntity(
                addWishEndPoint,
                wish,
                String.class);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        ApplicationExceptionDTO appEx = convertToAppException(response.getBody());
        List<ApplicationError> errors = appEx.getErrors();
        assertEquals(1, errors.size());
        assertEquals("wish", errors.get(0).getField());
        assertEquals(BR_3000.name(), errors.get(0).getStatusCode());
        assertEquals("wish: is mandatory", errors.get(0).getDescription());
    }

}
