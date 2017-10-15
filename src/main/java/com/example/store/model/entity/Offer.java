package com.example.store.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "offers")
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String desc;

    @Column
    private LocalDateTime dateAdded;

    @Column
    private LocalDateTime dateExpire;

    @Column
    private boolean isActive;
}
