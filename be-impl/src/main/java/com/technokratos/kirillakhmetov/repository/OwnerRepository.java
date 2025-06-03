package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmail(String email);

    @Modifying
    @Query("""
            UPDATE Owner o
            SET o.firstName = :firstName,
                o.lastName = :lastName,
                o.patronymic = :patronymic,
                o.age = :age,
                o.phoneNumber = :phoneNumber
            WHERE o.email = :email
            """)
    int updateOwnerProfile(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("patronymic") String patronymic,
            @Param("age") int age,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email
    );

    @Modifying
    @Query("""
            DELETE FROM Owner o
            WHERE o.email = :email
            """)
    void deleteByEmail(@Param("email") String email);

    @Modifying
    @Query("""
            UPDATE Owner o
            SET o.profilePhotoUrl = :url
            WHERE o.email = :email
            """)
    void updateProfilePhotoUrlByEmail(
            @Param("url") String url,
            @Param("email") String email);
}
