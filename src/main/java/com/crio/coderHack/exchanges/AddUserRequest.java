package com.crio.coderHack.exchanges;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddUserRequest {
    @NotEmpty
    private String username;
}
