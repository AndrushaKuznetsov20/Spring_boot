package com.trueman.KP_Vacancy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="announcement")
@Data
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "conditions_and_requirements")
    private String conditions_and_requirements;
    @Column(name = "contract_status")
    private String contract_status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "announcement")
    @JsonIgnore
    private Set<Response> responses = new HashSet<>();

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listUser")
//    private Set<User> list_users = new HashSet<>();

    @ManyToMany
    @JoinColumn(name = "list_users")
    private List<User> list_users;

    public List<User> getList_users() {
        return list_users;
    }

    public void setList_users(List<User> list_users) {
        this.list_users = list_users;
    }
//    public Set<User> getList_users() {
//        return list_users;
//    }
//
//    public void setList_users(Set<User> list_users) {
//        this.list_users = list_users;
//    }
}
