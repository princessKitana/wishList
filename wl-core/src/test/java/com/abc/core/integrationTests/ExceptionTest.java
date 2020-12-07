package com.abc.core.integrationTests;

import com.abc.api.common.v1.WishDTO;
import com.abc.api.error.v1.ApplicationExceptionDTO;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.wish.add.AddWishService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.abc.core.enums.BusinessRules.BR_9999;
import static org.junit.Assert.assertEquals;

public class ExceptionTest extends TestHelper{
    @MockBean
    AddWishService service;

    @Test
    public void exceptionTest() {
        WishRequest request = new WishRequest();
        request.setWish("some text");
        Mockito.when(service.addWish(request)).thenThrow(new RuntimeException());

        WishDTO wish = getWishDTO();
        wish.setWish("some text");
        ResponseEntity<String> response = restTemplate.postForEntity(
                addWishEndPoint,
                wish,
                String.class);

        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        ApplicationExceptionDTO appEx = convertToAppException(response.getBody());
        List<ApplicationError> errors = appEx.getErrors();
        assertEquals(1, errors.size());
        assertEquals(BR_9999.name(), errors.get(0).getStatusCode());
    }
}
