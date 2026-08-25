package com.dgsw.org.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class MemberRequest {

    private MemberRequest() {
    }

    @Schema(description = "회원 생성/수정 요청")
    public record CreateOrUpdate(
            @Schema(description = "회원 이름", example = "홍길동")
            @NotBlank(message = "name은 비어 있을 수 없습니다.")
            @Size(max = 100, message = "name은 100자 이하여야 합니다.")
            String name
    ) {
    }

    @Schema(description = "회원 일부 수정 요청")
    public record Patch(
            @Schema(description = "변경할 회원 이름", example = "김철수")
            @Size(max = 100, message = "name은 100자 이하여야 합니다.")
            String name
    ) {
    }
}
