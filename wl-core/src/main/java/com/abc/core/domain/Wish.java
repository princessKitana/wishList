package com.abc.core.domain;

import com.abc.core.enums.WishStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "wishList")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wish {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wish", length = 200)
    private String wish;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private WishStatus status = WishStatus.NEW;
}
