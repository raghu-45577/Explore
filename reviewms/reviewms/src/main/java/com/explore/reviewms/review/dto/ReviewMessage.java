package com.explore.reviewms.review.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessage {

    private Long id;
    private String title;
    private String description;
    private double rating;
    private Long companyId;
}
