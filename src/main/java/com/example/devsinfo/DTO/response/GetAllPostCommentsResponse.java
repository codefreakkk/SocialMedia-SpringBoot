package com.example.devsinfo.DTO.response;

import com.example.devsinfo.DTO.CommentsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostCommentsResponse {
    private boolean sucess;
    private List<CommentsDTO> comments;
}
