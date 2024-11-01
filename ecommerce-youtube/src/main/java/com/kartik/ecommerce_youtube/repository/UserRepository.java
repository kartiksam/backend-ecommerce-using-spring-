package com.kartik.ecommerce_youtube.repository;
import com.kartik.ecommerce_youtube.model.User;
//const a=reequire
//
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//two thuings need tpo menyion foer whiuch class and in them id its type
public interface UserRepository extends JpaRepository<User,Long>
{

//by default some methods but some extra method by email find user need to give implemnetation

    public User findByEmail(String email);











}
