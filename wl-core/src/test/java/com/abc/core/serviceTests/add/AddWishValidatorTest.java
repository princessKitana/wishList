package com.abc.core.serviceTests.add;

import com.abc.core.enums.BusinessRules;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.wish.add.AddWishValidator;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddWishValidatorTest {

    @InjectMocks
    private AddWishValidator validator = new AddWishValidator();

    @Test
    public void shouldReturnErrorWhenWishIsEmpty() {
        List<ApplicationError> applicationErrors = validator.validate(new WishRequest());
        assertEquals(1, applicationErrors.size());
        assertEquals(BusinessRules.BR_3000.name(), applicationErrors.get(0).getStatusCode());
    }

    @Test
    public void shouldNotReturnErrorWhenRequestCorrect() {
        WishRequest request = new WishRequest();
        request.setWish("some wish");

        List<ApplicationError> applicationErrors = validator.validate(request);
        assertEquals(0, applicationErrors.size());
    }

}
