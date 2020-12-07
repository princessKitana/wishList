package com.abc.core.serviceTests.wish.add;

import com.abc.core.domain.Wish;
import com.abc.core.repository.WishRepository;
import com.abc.core.serviceTests.ApplicationError;
import com.abc.core.serviceTests.ApplicationException;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.WishResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AddWishServiceImpl implements AddWishService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private AddWishValidator validator;

    @Override
    @Transactional
    public WishResponse addWish(WishRequest request) {
        log.info("Adding wish: {}", request.getWish());

        List<ApplicationError> validationErrors = validator.validate(request);
        if (!validationErrors.isEmpty()) {
            throw new ApplicationException(validationErrors);
        }

        Wish wish = new Wish();
        wish.setWish(request.getWish());

        wishRepository.save(wish);

        WishResponse response = new WishResponse();
        response.setId(String.valueOf(wish.getId()));
        response.setWish(wish.getWish());
        response.setStatus(String.valueOf(wish.getStatus()));

        return response;
    }

}
