package com.erayyildirim.blog.repository;

import com.erayyildirim.blog.model.Like;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface LikeRepository extends CrudRepository<Like,Integer> {

    @Query("SELECT t FROM Like t")
    List<Like> findAllLikes();


}
