package com.abc.core.serviceTests.delete;


import com.abc.core.repository.WishRepository;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.ApplicationException;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.WishResponse;
import com.abc.core.serviceTests.wish.delete.DeleteWishService;
import com.abc.core.serviceTests.wish.delete.DeleteWishServiceImpl;
import com.abc.core.serviceTests.wish.delete.DeleteWishValidator;
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
public class DeleteWishServiceTest {
    @Mock
    private WishRepository wishRepository;

    @Mock
    private DeleteWishValidator validator;

    @InjectMocks
    private DeleteWishService service = new DeleteWishServiceImpl();

    @Test
    public void shouldNotReturnFailedResponseWhenRequestOk() {
        WishRequest request = new WishRequest();
        request.setId("123");
        Mockito.when(validator.validate(request)).thenReturn(new ArrayList<>());

        try {
            service.deleteWish(request);
        } catch (ApplicationException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldReturnFailedResponseWhenValidationErrorsExist() {
        WishRequest request = new WishRequest();
        request.setId("1");

        ApplicationError error = new ApplicationError();
        error.setField("id");
        error.setDescription("Must be numeric");
        List<ApplicationError> errors = new ArrayList<>();
        errors.add(error);

        Mockito.when(validator.validate(request)).thenReturn(errors);

        try {
            service.deleteWish(request);
        } catch (ApplicationException e) {
            Assert.assertEquals("Must be numeric", e.getErrors().get(0).getDescription());
            Assert.assertEquals("id", e.getErrors().get(0).getField());
        }
    }

}
