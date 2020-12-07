package com.abc.core.controller;

import com.abc.api.common.v1.WishDTO;
import com.abc.core.serviceTests.WishRequest;
import com.abc.core.serviceTests.WishResponse;
import com.abc.core.serviceTests.wish.add.AddWishService;
import com.abc.core.serviceTests.wish.delete.DeleteWishService;
import com.abc.core.serviceTests.wish.get.GetWishService;
import com.abc.core.serviceTests.wish.update.UpdateWishService;
import com.abc.core.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/wishList", produces = MediaType.APPLICATION_JSON_VALUE)
public class WishListController {

    @Autowired
    AddWishService addWishService;

    @Autowired
    GetWishService getWishService;

    @Autowired
    UpdateWishService updateWishService;

    @Autowired
    DeleteWishService deleteWishService;

    @PostMapping(value = "/add")
    public ResponseEntity<WishDTO> add(@RequestBody WishDTO wishDTO) {
        WishRequest wishRequest = new WishRequest();
        wishRequest.setWish(wishDTO.getWish());

        WishResponse wishResponse = addWishService.addWish(wishRequest);
        wishDTO.setStatus(wishResponse.getStatus());
        wishDTO.setId(wishResponse.getId());

        return ResponseEntity.ok(wishDTO);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<WishDTO> get(@PathVariable("id") String wishId) {
        WishRequest request = new WishRequest();
        request.setId(wishId);

        WishResponse response = getWishService.getWish(request);

        WishDTO wishDTO = new WishDTO();
        wishDTO.setId(response.getId());
        wishDTO.setStatus(response.getStatus());
        wishDTO.setWish(response.getWish());

        return ResponseEntity.ok(wishDTO);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<WishDTO>> getAll() {
        List<WishResponse> wishes = getWishService.getAllWishes();

        List<WishDTO> wishDTOS = Util.mapWishResponseToWishDTO(wishes);
        return ResponseEntity.ok(wishDTOS);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String wishId, @RequestBody WishDTO wishDTO) {
        WishRequest wishRequest = new WishRequest();
        wishRequest.setId(wishId);
        wishRequest.setWish(wishDTO.getWish());
        wishRequest.setStatus(wishDTO.getStatus());

        updateWishService.updateWish(wishRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String wishId) {
        WishRequest wishRequest = new WishRequest();
        wishRequest.setId(wishId);

        deleteWishService.deleteWish(wishRequest);

        return ResponseEntity.ok().build();
    }

}
