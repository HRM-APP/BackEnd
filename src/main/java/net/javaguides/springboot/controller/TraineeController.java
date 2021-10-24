package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Trainee;
import net.javaguides.springboot.repository.TraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class TraineeController {

    @Autowired
    private TraineeRepository traineeRepository;

    // get all trainees
    @GetMapping("/trainees")
    public List<Trainee> getAllTrainees(){
        return traineeRepository.findAll();
    }

    // create trainee rest api
    @PostMapping("/trainees")
    public Trainee createTrainee(@RequestBody Trainee trainee){
        return traineeRepository.save(trainee);
    }

    // get trainee by id rest api
    @GetMapping("/trainees/{id}")
    public ResponseEntity<Trainee>getTraineeById(@PathVariable Long id) {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not exist with id :" + id));
        return ResponseEntity.ok(trainee);
    }

    // update trainee rest api
    @PutMapping("/trainees/{id}")
    public ResponseEntity<Trainee> updateTrainee(@PathVariable Long id, @RequestBody Trainee traineeDetails){
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not exist with id :" + id));

        trainee.setFirstName(traineeDetails.getFirstName());
        trainee.setEmailId(traineeDetails.getEmailId());
        trainee.setLastName(traineeDetails.getLastName());
        trainee.setDepartment(traineeDetails.getDepartment());

        Trainee updatedTrainee = traineeRepository.save(trainee);
        return ResponseEntity.ok(updatedTrainee);
    }

    // delete trainee rest api
    @DeleteMapping("/trainees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTrainee(@PathVariable Long id){
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not exist with id :" + id));

        traineeRepository.delete(trainee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
