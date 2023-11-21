package com.trueman.KP_Vacancy.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "list_of_works")
@Data
public class ListOfWorks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name_work")
    private Long name_work;
}
