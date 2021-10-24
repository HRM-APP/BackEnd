package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.javaguides.springboot.model.Job;
import net.javaguides.springboot.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // get all jobs
    @GetMapping("/jobs")
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    // create job rest api
    @PostMapping("/jobs")
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    // get job by id rest api
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not exist with id :" + id));
        return ResponseEntity.ok(job);
    }

    // update job rest api
    @PutMapping("/jobs/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job jobDetails){
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not exist with id :" + id));

        job.setJobName(jobDetails.getJobName());
        job.setDescription(jobDetails.getDescription());
        job.setPostDate(jobDetails.getPostDate());
        job.setStatus(jobDetails.getStatus());

        Job updatedJob = jobRepository.save(job);
        return ResponseEntity.ok(updatedJob);
    }

    // delete job rest api
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteJob(@PathVariable Long id){
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not exist with id :" + id));

        jobRepository.delete(job);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
