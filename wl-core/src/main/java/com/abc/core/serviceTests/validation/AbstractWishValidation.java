package com.abc.core.serviceTests.validation;

import com.abc.core.domain.Wish;
import com.abc.core.enums.BusinessRules;
import com.abc.core.repository.WishRepository;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.WishRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.abc.core.enums.WishStatus.isValidStatus;

public abstract class AbstractWishValidation {

    private static final String VALIDATION = "VALIDATION";
    private static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    @Autowired
    private WishRepository wishRepository;

    public abstract List<ApplicationError> validate(WishRequest request);

    public Optional<ApplicationError> checkIsNumeric(String value) {
        if (StringUtils.isNumeric(value)) {
            return Optional.empty();
        } else {
            ApplicationError error = new ApplicationError();
            error.setField("id");
            error.setStatusCode(BusinessRules.BR_3002.name());
            error.setDescription(BusinessRules.BR_3002.getMessage());
            error.setType(VALIDATION_ERROR);
            error.setSeverity(VALIDATION);
            return Optional.of(error);
        }
    }

    public Optional<ApplicationError> checkWishIsPresentInDb(String id) {
        Optional<Wish> wish = wishRepository.findById(Long.valueOf(id));
        if (wish.isPresent()) {
            return Optional.empty();
        } else {
            ApplicationError error = new ApplicationError();
            error.setField("id");
            error.setStatusCode(BusinessRules.BR_3001.name());
            error.setDescription(BusinessRules.BR_3001.getMessage());
            error.setType(VALIDATION_ERROR);
            error.setSeverity(VALIDATION);
            return Optional.of(error);
        }
    }

    public Optional<ApplicationError> checkStatus(String value) {
        if (isValidStatus(value)) {
            return Optional.empty();
        } else {
            ApplicationError error = new ApplicationError();
            error.setStatusCode(BusinessRules.BR_3003.name());
            error.setDescription(BusinessRules.BR_3003.getMessage());
            error.setType(VALIDATION_ERROR);
            error.setSeverity(VALIDATION);
            return Optional.of(error);
        }
    }

    public Optional<ApplicationError> checkWishTextIsPresent(String propertyValue, String propertyName) {

        if (StringUtils.isEmpty(propertyValue)) {
            ApplicationError error = new ApplicationError();
            error.setField(propertyName);
            error.setStatusCode(BusinessRules.BR_3000.name());
            error.setDescription(propertyName + ": " + BusinessRules.BR_3000.getMessage());
            error.setType(VALIDATION_ERROR);
            error.setSeverity(VALIDATION);
            return Optional.of(error);
        } else
            return Optional.empty();
    }

}
