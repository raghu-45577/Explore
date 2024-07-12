package com.explore.jobms.job.service;

import com.explore.jobms.job.dto.JobDTO;
import com.explore.jobms.job.model.Job;

import java.util.List;

public interface JobService {

    List<JobDTO> findAll();
    void createJob(Job job);
    JobDTO getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
