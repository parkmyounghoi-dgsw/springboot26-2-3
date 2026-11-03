package hs.kr.pmh.suhang.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Schema(description = "회원 정보")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gen")
    @SequenceGenerator(name = "member_seq_gen", sequenceName = "member_seq", allocationSize = 1)
    @Schema(description = "회원 ID", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @Column(nullable = false, length = 50)
    @Schema(description = "회원 이름", example = "홍길동")
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "회원 이메일", example = "hong@example.com")
    private String email;

    public Member(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
