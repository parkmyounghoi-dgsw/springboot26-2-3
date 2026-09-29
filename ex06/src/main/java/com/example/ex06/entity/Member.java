package com.example.ex06.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Course> courses = new ArrayList<>();

    // 수정용 메서드 (PUT - 전체 교체)
    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // 수정용 메서드 (PATCH - 부분 수정)
    public void patch(String name, String email) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
    }
}
