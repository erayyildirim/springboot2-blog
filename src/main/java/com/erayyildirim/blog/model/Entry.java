package com.erayyildirim.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data // getter stterlar
@Entity
@Table(name="ENTRY")
public class Entry {
    @Id
    @Column(name = "ID")
    //bu id columnu hibernate biliyor otomatik generate için generated value
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;

    @NotEmpty
    @Column(name = "TITLE")
    private String title;

    @NotEmpty //bu column bos veya null olamaz
    @Column(name = "BODY")
    private String body;

    @NotNull
    @Column(name = "CREATE_DATE")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate createDate = LocalDate.now();

    @NotEmpty
    @Column(name = "WRITER")
    private String writer;

    @Column(name ="IS_DELETED")
    private Boolean isDeleted;


    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = false)
    @JoinColumn(name = "ENTRY_ID")
    List<Like> likes = new ArrayList<>();

    @ManyToMany
    private List<Category> categories;


}
