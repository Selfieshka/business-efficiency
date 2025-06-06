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
            WHERE o.id = :id
            """)
    void updateOwnerProfile(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("patronymic") String patronymic,
            @Param("age") Integer age,
            @Param("phoneNumber") String phoneNumber
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
            SET o.profilePhotoUrl = :profilePhotoUrl
            WHERE o.id = :ownerId
            """)
    void updateProfilePhotoUrlByOwnerId(
            @Param("ownerId") long ownerId,
            @Param("profilePhotoUrl") String profilePhotoUrl);
}
