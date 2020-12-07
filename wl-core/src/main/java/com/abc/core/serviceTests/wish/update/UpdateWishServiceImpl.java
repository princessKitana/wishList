package com.abc.core.serviceTests.wish.update;

import com.abc.core.domain.Wish;
import com.abc.core.enums.WishStatus;
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
import java.util.Optional;

@Component
public class UpdateWishServiceImpl implements UpdateWishService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UpdateWishValidator validator;

    @Override
    @Transactional
    public void updateWish(WishRequest request) {
        log.info("Updating wish: {}", request.getId());

        List<ApplicationError> validationErrors = validator.validate(request);
        if (!validationErrors.isEmpty()) {
            throw new ApplicationException(validationErrors);
        }

        Optional<Wish> wish = wishRepository.findById(Long.valueOf(request.getId()));
        if (wish.isPresent()) {
            wish.get().setWish(request.getWish());
            wish.get().setStatus(WishStatus.valueOf(request.getStatus()));
            wishRepository.save(wish.get());
        }
    }

}
