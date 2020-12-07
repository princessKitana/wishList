package com.abc.core.integrationTests;

import com.abc.api.common.v1.WishDTO;
import com.abc.core.domain.Wish;
import com.abc.core.enums.WishStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;


public class UpdateWishListTest extends AddWishListTest {

    @Test
    public void updateWishSuccess() {
        addWishSuccess();

        WishDTO updatedWish = getWishDTO();
        updatedWish.setWish("Updated wish");
        updatedWish.setStatus(WishStatus.DONE.name());

        restTemplate.put(updateWishEndPoint + wishId, updatedWish);

        Optional<Wish> wish = wishRepository.findById(Long.valueOf(wishId));
        Assert.assertTrue(wish.isPresent());
        Assert.assertEquals("Updated wish", wish.get().getWish());
        Assert.assertEquals(WishStatus.DONE, wish.get().getStatus());
    }

    @Test
    public void updateWishIncorrectStatus() {
        addWishSuccess();

        WishDTO updatedWish = getWishDTO();
        updatedWish.setWish("Updated wish");
        updatedWish.setStatus("Incorrect STATUS");

        restTemplate.put(updateWishEndPoint + wishId, updatedWish);

        Optional<Wish> wish = wishRepository.findById(Long.valueOf(wishId));
        Assert.assertFalse(wish.get().getWish().equals("Updated wish"));
    }

}
