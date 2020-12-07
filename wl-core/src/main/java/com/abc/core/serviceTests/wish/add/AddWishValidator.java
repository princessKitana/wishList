package com.abc.core.serviceTests.wish.add;

import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.validation.AbstractWishValidation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddWishValidator extends AbstractWishValidation {

    @Override
    public List<ApplicationError> validate(WishRequest request) {

        List<ApplicationError> errors = new ArrayList<>();
        checkWishTextIsPresent(request.getWish(), "wish").ifPresent(errors::add);
        return errors;
    }
}



