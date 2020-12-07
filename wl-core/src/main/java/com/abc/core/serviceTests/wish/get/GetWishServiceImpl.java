package com.abc.core.serviceTests.wish.get;


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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GetWishServiceImpl implements GetWishService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private GetWishValidator validator;

    @Override
    @Transactional
    public WishResponse getWish(WishRequest request) {
        log.info("Getting wish: {}", request.getId());

        List<ApplicationError> validationErrors = validator.validate(request);
        if (!validationErrors.isEmpty()) {
            throw new ApplicationException(validationErrors);
        }

        Optional<Wish> wish = wishRepository.findById(Long.valueOf(request.getId()));
        WishResponse response = new WishResponse();
        response.setId(String.valueOf(wish.get().getId()));
        response.setStatus(String.valueOf(wish.get().getStatus()));
        response.setWish(String.valueOf(wish.get().getWish()));

        return response;
    }

    @Override
    @Transactional
    public List<WishResponse> getAllWishes() {
        log.info("Getting all wishes");

        List<Wish> wishes = wishRepository.findAll();
        List<WishResponse> responseList = new ArrayList<>();
        for(Wish wish: wishes){
            WishResponse response = new WishResponse();
            response.setId(String.valueOf(wish.getId()));
            response.setStatus(String.valueOf(wish.getStatus()));
            response.setWish(wish.getWish());
            responseList.add(response);
        }
        return responseList;
    }
}
