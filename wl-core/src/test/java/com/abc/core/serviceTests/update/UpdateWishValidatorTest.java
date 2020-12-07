package com.abc.core.serviceTests.update;

import com.abc.core.domain.Wish;
import com.abc.core.enums.BusinessRules;
import com.abc.core.enums.WishStatus;
import com.abc.core.repository.WishRepository;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.wish.update.UpdateWishValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UpdateWishValidatorTest {

    @Mock
    private WishRepository wishRepository;

    @InjectMocks
    private UpdateWishValidator validator = new UpdateWishValidator();

    @Test
    public void shouldReturnErrorWhenIdNotNumeric() {
        WishRequest request = new WishRequest();
        request.setId("string");
        request.setStatus("DONE");

        List<ApplicationError> applicationErrors = validator.validate(request);

        assertEquals(1, applicationErrors.size());
        assertEquals(BusinessRules.BR_3002.name(), applicationErrors.get(0).getStatusCode());
    }

    @Test
    public void shouldReturnErrorWhenStatusIncorrect() {
        WishRequest request = new WishRequest();
        request.setId("123655");
        request.setStatus("INCORRECT STATUS");

        List<ApplicationError> applicationErrors = validator.validate(request);

        assertEquals(1, applicationErrors.size());
        assertEquals(BusinessRules.BR_3003.name(), applicationErrors.get(0).getStatusCode());
    }

    @Test
    public void shouldReturnErrorWhenWishNotFoundInDb() {
        WishRequest request = new WishRequest();
        request.setId("123655");
        request.setStatus("DONE");

        Mockito.when(wishRepository.findById(any())).thenReturn(Optional.empty());

        List<ApplicationError> applicationErrors = validator.validate(request);
        assertEquals(1, applicationErrors.size());
        assertEquals(BusinessRules.BR_3001.name(), applicationErrors.get(0).getStatusCode());
    }

    @Test
    public void shouldNotReturnErrorWhenWishFoundInDb() {
        WishRequest request = new WishRequest();
        request.setId("123");
        request.setStatus("NEW");
        request.setWish("some updated wish");

        Wish wish = new Wish();
        wish.setId(123L);
        wish.setStatus(WishStatus.NEW);

        Mockito.when(wishRepository.findById(wish.getId())).thenReturn(Optional.ofNullable(wish));

        List<ApplicationError> applicationErrors = validator.validate(request);
        assertEquals(0, applicationErrors.size());
    }

}
