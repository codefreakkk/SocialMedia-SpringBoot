package com.example.devsinfo.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private int id;
    private String photo;
    private String description;
    private int likeCount;
    private List<String> comments;
}
