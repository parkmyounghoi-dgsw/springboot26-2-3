package hs.kr.pmh.suhang.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private int rating;

    @Column(nullable = false)
    private String description;

    // id 없이 생성하는 생성자 (persist용)
    public Course(String name, String category, int rating, String description) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.description = description;
    }

    // PUT: 전체 수정 (모든 필드 교체)
    public void update(String name, String category, int rating, String description) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.description = description;
    }

    // PATCH: 부분 수정 (null이 아닌 필드만 반영)
    public void patch(String name, String category, Integer rating, String description) {
        if (name != null && !name.isBlank())        this.name = name;
        if (category != null && !category.isBlank()) this.category = category;
        if (rating != null)                          this.rating = rating;
        if (description != null && !description.isBlank()) this.description = description;
    }
}
