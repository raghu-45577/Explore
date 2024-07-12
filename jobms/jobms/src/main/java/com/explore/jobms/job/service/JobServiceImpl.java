package com.explore.jobms.job.service;

import com.explore.jobms.job.clients.CompanyClient;
import com.explore.jobms.job.clients.ReviewClient;
import com.explore.jobms.job.dto.JobDTO;
import com.explore.jobms.job.mapper.JobMapper;
import com.explore.jobms.job.model.Job;
import com.explore.jobms.job.pojo.Company;
import com.explore.jobms.job.pojo.Review;
import com.explore.jobms.job.repository.JobRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService{

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;


    @Override
    @CircuitBreaker(name = "companyBreaker", fallbackMethod="companyBreakerFallback")
    public List<JobDTO> findAll() {
        List<Job>jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> res = new ArrayList<>();
        res.add("dummy");
        return res;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {

        Job job = jobRepository.findById(id).orElse(null);
        if(job != null) {
            return convertToDto(job);
        }
        return null;
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobCheck = jobRepository.findById(id);
        if(jobCheck.isPresent()){
            Job job = jobCheck.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    public JobDTO convertToDto(Job job){
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDTO(job,company, reviews);
    }
}
