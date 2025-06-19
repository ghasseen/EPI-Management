package com.epi.coursemanagement.service;

import com.epi.coursemanagement.model.Instructor;
import com.epi.coursemanagement.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    public Page<Instructor> findAll(Pageable pageable) {
        return instructorRepository.findAll(pageable);
    }

    public Optional<Instructor> findById(Long id) {
        return instructorRepository.findById(id);
    }

    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteById(Long id) {
        instructorRepository.deleteById(id);
    }
}
