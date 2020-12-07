package com.abc.core.serviceTests.wish.add;

import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.WishResponse;

public interface AddWishService {
    WishResponse addWish(WishRequest request);
}
