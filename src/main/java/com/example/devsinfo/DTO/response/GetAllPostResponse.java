package com.example.devsinfo.DTO.response;

import com.example.devsinfo.DTO.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostResponse {
    private boolean success;
    private List<PostDTO> allPost;
}
