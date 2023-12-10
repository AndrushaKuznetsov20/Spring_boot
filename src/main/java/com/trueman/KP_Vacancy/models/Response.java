package com.trueman.KP_Vacancy.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "response")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn
    private Announcement announcement;

    @ManyToOne
    @JoinColumn
    private User user_response;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Response response = (Response) o;
        return id != null && Objects.equals(id, response.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
