package com.erayyildirim.blog.repository;

import com.erayyildirim.blog.model.Entry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


//@Repository
//public interface EntryRepository extends CrudRepository<Entry, Integer> {

//}
@Transactional // sorgular için transaction açılıyor.
@Component
@Repository
public interface EntryRepository extends CrudRepository<Entry, Integer> {

    @Query("SELECT e FROM Entry e")
    List<Entry> findAllEntries();

    //JPQL yazdım tarzıdır.param id yukarıdaki :id yerıne gecıyor : dısardan parametre.
    @Query("SELECT e FROM Entry e WHERE id = :id")
    Entry findEntryById(@Param("id") Integer id);

    @Modifying // koyulmazsa update delete işlemleri yapılamaz
    @Query("DELETE FROM Entry e WHERE e.id = :id")
    void deleteEntryById(@Param("id") Integer id);

    @Query("SELECT e FROM Entry e WHERE e.title LIKE %:str%")
    List<Entry> findByTitle(@Param("str") String str);


}
