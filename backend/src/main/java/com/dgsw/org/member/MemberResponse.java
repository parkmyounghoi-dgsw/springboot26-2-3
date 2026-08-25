package com.dgsw.org.member;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 응답")
public record MemberResponse(
        @Schema(description = "회원 ID", example = "1") Long id,
        @Schema(description = "회원 이름", example = "홍길동") String name
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}
