package com.crio.coderHack.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private String userId;

    private String username;

    private int score;

    private List<Badge> badges = new ArrayList<>();

}
