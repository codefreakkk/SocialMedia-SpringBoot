package com.example.devsinfo.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {
    private int id;
    private String photo;
    private String description;
    int likeCount;
    List<String> comments;
}
