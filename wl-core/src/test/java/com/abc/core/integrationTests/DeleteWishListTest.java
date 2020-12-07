package com.abc.core.integrationTests;

import com.abc.core.domain.Wish;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class DeleteWishListTest extends AddWishListTest {

    @Test
    public void deleteWishSuccess() {
        addWishSuccess();

        restTemplate.delete(deleteWishEndPoint + wishId);

        Optional<Wish> wish = wishRepository.findById(Long.valueOf(wishId));
        Assert.assertTrue(wish.isEmpty());
    }

    @Test
    public void deleteWishNotExistingInDB() {
        addWishSuccess();

        restTemplate.delete(
                deleteWishEndPoint + "9999",
                String.class);

        Optional<Wish> wish = wishRepository.findById(Long.valueOf(wishId));
        Assert.assertFalse(wish.isEmpty());
    }

}
