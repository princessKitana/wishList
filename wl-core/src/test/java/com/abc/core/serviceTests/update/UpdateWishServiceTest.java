package com.abc.core.serviceTests.update;


import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.ApplicationException;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.wish.update.UpdateWishService;
import com.abc.core.serviceTests.wish.update.UpdateWishServiceImpl;
import com.abc.core.serviceTests.wish.update.UpdateWishValidator;
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
public class UpdateWishServiceTest {

    @Mock
    private UpdateWishValidator validator;

    @InjectMocks
    private UpdateWishService service = new UpdateWishServiceImpl();

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
            service.updateWish(request);
        } catch (ApplicationException e) {
            Assert.assertEquals("Cannot be empty", e.getErrors().get(0).getDescription());
            Assert.assertEquals("wish", e.getErrors().get(0).getField());
        }
    }

}
