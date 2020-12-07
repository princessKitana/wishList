package com.abc.core.util;

import com.abc.api.common.v1.WishDTO;
import com.abc.core.serviceTests.WishResponse;

import java.util.ArrayList;
import java.util.List;

public final class Util {
    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static List<WishDTO> mapWishResponseToWishDTO(List<WishResponse> wishes){
        List<WishDTO> wishDTOS = new ArrayList<>();
        for (WishResponse wishResponse: wishes){
            WishDTO wish = new WishDTO();
            wish.setStatus(wishResponse.getStatus());
            wish.setId(wishResponse.getId());
            wish.setWish(wishResponse.getWish());
            wishDTOS.add(wish);
        }
        return wishDTOS;
    }
}
