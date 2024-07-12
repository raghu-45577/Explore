package com.explore.jobms.job.dto;

import com.explore.jobms.job.pojo.Company;
import com.explore.jobms.job.pojo.Review;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Company company;
    private List<Review> reviews;
}
