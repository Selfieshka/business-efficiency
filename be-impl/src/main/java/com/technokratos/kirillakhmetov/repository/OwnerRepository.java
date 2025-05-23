//package com.technokratos.kirillakhmetov.repository;
//
//import com.technokratos.kirillakhmetov.entity.Owner;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Optional;
//
//public interface OwnerRepository extends JpaRepository<Owner, Long> {
//    Optional<Owner> findByEmail(String email);
//
//    @Modifying
//    @Query("""
//            UPDATE Owner o
//            SET o.firstName = :firstName,
//                o.lastName = :lastName,
//                o.patronymic = :patronymic,
//                o.age = :age,
//                o.phoneNumber = :phoneNumber
//            WHERE o.email = :email
//            """)
//    int updateOwnerProfile(String firstName, String lastName, String patronymic,
//                           Integer age, String phoneNumber, String email);
//
//    @Modifying
//    @Query("DELETE FROM Owner o WHERE o.email = :email")
//    void deleteByEmail(String email);
//
//    @Modifying
//    @Query("UPDATE Owner o SET o.profilePhotoUrl = :url WHERE o.email = :email")
//    void updateProfilePhotoUrlByEmail(String url, String email);
//}
