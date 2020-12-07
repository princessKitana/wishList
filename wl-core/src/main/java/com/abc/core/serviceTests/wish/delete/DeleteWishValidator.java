package com.abc.core.serviceTests.wish.delete;

import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.validation.AbstractWishValidation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeleteWishValidator extends AbstractWishValidation {

    @Override
    public List<ApplicationError> validate(WishRequest request) {
        List<ApplicationError> errors = new ArrayList<>();
        checkIsNumeric(request.getId()).ifPresent(errors::add);
        if (errors.isEmpty()) {
            checkWishIsPresentInDb(request.getId()).ifPresent(errors::add);
        }
        return errors;
    }

}



