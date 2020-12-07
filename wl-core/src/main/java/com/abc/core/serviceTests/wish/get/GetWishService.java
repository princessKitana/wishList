package com.abc.core.serviceTests.wish.get;

import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.WishResponse;

import java.util.List;

public interface GetWishService {
    WishResponse getWish(WishRequest request);
    List<WishResponse> getAllWishes();
}
