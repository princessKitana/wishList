package com.abc.core.serviceTests.wish.delete;

import com.abc.core.repository.WishRepository;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.ApplicationException;
import com.abc.core.serviceTests.WishRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DeleteWishServiceImpl implements DeleteWishService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private DeleteWishValidator validator;

    @Override
    @Transactional
    public void deleteWish(WishRequest request) {
        log.info("Deleting wish: {}", request.getId());

        List<ApplicationError> validationErrors = validator.validate(request);
        if (!validationErrors.isEmpty()) {
            throw new ApplicationException(validationErrors);
        }

        wishRepository.deleteById(Long.valueOf(request.getId()));
    }

}
