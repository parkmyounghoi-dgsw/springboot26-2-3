package com.example.ex06.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

// entity 를 만드는것은 mysql Table 생성...

/*
    entity 패키지에
    @Entity
    Product 클래스 -> 테이블 생성됨...
    id, name, price, desc
    테이블 생성 되었는지 확인해주세요..
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Data
@Builder
public class Member {

//기본생성자
//    Member(){}
//    Member(Long id, String name,String email){
//        this.id = id;
//        this.name = name;
//        this.email = email;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(example = "홍길동")
    private String name;
    @Schema(example = "aaa@naver.com")
    private String email;

}
