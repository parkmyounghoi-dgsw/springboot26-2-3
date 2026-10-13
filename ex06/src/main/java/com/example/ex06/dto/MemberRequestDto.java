package com.example.ex06.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequestDto {

    @NotBlank(message = "이름은 빈공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일은 빈공백일 수 없습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
