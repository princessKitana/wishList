package com.abc.core.serviceTests.delete;

import com.abc.core.domain.Wish;
import com.abc.core.enums.BusinessRules;
import com.abc.core.enums.WishStatus;
import com.abc.core.repository.WishRepository;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.wish.delete.DeleteWishValidator;
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
public class DeleteWishValidatorTest {

    @Mock
    private WishRepository wishRepository;

    @InjectMocks
    private DeleteWishValidator validator = new DeleteWishValidator();

    @Test
    public void shouldReturnErrorWhenIdNotNumeric() {

        WishRequest request = new WishRequest();
        request.setId("string");

        List<ApplicationError> applicationErrors = validator.validate(request);

        assertEquals(1, applicationErrors.size());
        assertEquals(BusinessRules.BR_3002.name(), applicationErrors.get(0).getStatusCode());
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

        Wish wish = new Wish();
        wish.setId(123L);
        wish.setStatus(WishStatus.NEW);

        Mockito.when(wishRepository.findById(wish.getId())).thenReturn(Optional.ofNullable(wish));

        List<ApplicationError> applicationErrors = validator.validate(request);
        assertEquals(0, applicationErrors.size());
    }

}
