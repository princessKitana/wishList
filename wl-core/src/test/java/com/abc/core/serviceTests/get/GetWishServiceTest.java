package com.abc.core.serviceTests.get;

import com.abc.core.domain.Wish;
import com.abc.core.enums.WishStatus;
import com.abc.core.repository.WishRepository;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.ApplicationException;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.WishResponse;
import com.abc.core.serviceTests.wish.get.GetWishService;
import com.abc.core.serviceTests.wish.get.GetWishServiceImpl;
import com.abc.core.serviceTests.wish.get.GetWishValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class GetWishServiceTest {

    @Mock
    private GetWishValidator validator;

    @Mock
    private WishRepository wishRepository;

    @InjectMocks
    private GetWishService service = new GetWishServiceImpl();


    @Test
    public void shouldNotReturnFailedResponseWhenRequestCorrect() {
        WishRequest request = new WishRequest();
        request.setId("123");
        Mockito.when(validator.validate(request)).thenReturn(new ArrayList<>());

        Wish wish = new Wish();
        wish.setId(123L);
        wish.setStatus(WishStatus.NEW);
        wish.setWish("Coffee mug");
        Mockito.when(wishRepository.findById(wish.getId())).thenReturn(Optional.ofNullable(wish));

        WishResponse response = service.getWish(request);
        Assert.assertEquals("Coffee mug", response.getWish());
    }

    @Test
    public void shouldReturnFailedResponseWhenValidationErrorsExist() {
        WishRequest request = new WishRequest();
        request.setId("123");

        ApplicationError error = new ApplicationError();
        error.setField("id");
        error.setDescription("Should be numeric");
        List<ApplicationError> errors = new ArrayList<>();
        errors.add(error);
        Mockito.when(validator.validate(request)).thenReturn(errors);

        try {
            service.getWish(request);
        } catch (ApplicationException e) {
            Assert.assertEquals("Should be numeric", e.getErrors().get(0).getDescription());
            Assert.assertEquals("id", e.getErrors().get(0).getField());
        }
    }

    @Test
    public void getAllWishesSuccess() {
        Wish wish1 = new Wish();
        wish1.setId(123L);
        wish1.setStatus(WishStatus.NEW);
        wish1.setWish("Coffee mug 1");

        Wish wish2 = new Wish();
        wish2.setId(124L);
        wish2.setStatus(WishStatus.DONE);
        wish2.setWish("Coffee mug 2");
        List<Wish> wishes = new ArrayList<>();
        wishes.add(wish1);
        wishes.add(wish2);

        Mockito.when(wishRepository.findAll()).thenReturn(wishes);

        List<WishResponse> wishResponseList = service.getAllWishes();
        Assert.assertEquals(2, wishResponseList.size());
    }

}
