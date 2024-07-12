package com.explore.jobms.job.pojo;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private Long id;
    private String title;
    private String description;
    private double rating;
}
