package com.abc.core.serviceTests.add;

import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.ApplicationException;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.wish.add.AddWishService;
import com.abc.core.serviceTests.wish.add.AddWishServiceImpl;
import com.abc.core.serviceTests.wish.add.AddWishValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AddWishServiceTest {

    @Mock
    private AddWishValidator validator;

    @InjectMocks
    private AddWishService service = new AddWishServiceImpl();

    @Test
    public void shouldReturnFailedResponseWhenValidationErrorsExist() {
        WishRequest request = new WishRequest();
        request.setWish("Wish text");

        ApplicationError error = new ApplicationError();
        error.setField("wish");
        error.setDescription("Cannot be empty");
        List<ApplicationError> errors = new ArrayList<>();
        errors.add(error);

        Mockito.when(validator.validate(request)).thenReturn(errors);

        try {
            service.addWish(request);
        } catch (ApplicationException e) {
            Assert.assertEquals("Cannot be empty", e.getErrors().get(0).getDescription());
            Assert.assertEquals("wish", e.getErrors().get(0).getField());
        }
    }

}
