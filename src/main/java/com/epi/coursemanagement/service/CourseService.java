package com.epi.coursemanagement.service;

import com.epi.coursemanagement.model.Course;
import com.epi.coursemanagement.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final StorageService storageService;

    @Autowired
    public CourseService(CourseRepository courseRepository, StorageService storageService) {
        this.courseRepository = courseRepository;
        this.storageService = storageService;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Page<Course> findByCategoryId(Long categoryId, Pageable pageable) {
        return courseRepository.findByCategoryId(categoryId, pageable);
    }

    public Page<Course> findByInstructorId(Long instructorId, Pageable pageable) {
        return courseRepository.findByInstructorId(instructorId, pageable);
    }

    @Transactional
    public Course save(Course course, MultipartFile thumbnailFile) {
        logger.info("Saving course: {}", course.getTitle());
        
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            try {
                String filename = storageService.store(thumbnailFile);
                course.setThumbnailUrl(filename);
                logger.info("Stored thumbnail with filename: {}", filename);
            } catch (Exception e) {
                logger.error("Failed to store thumbnail: {}", e.getMessage());
            }
        }
        
        return courseRepository.save(course);
    }

    @Transactional
    public Course update(Course course, MultipartFile thumbnailFile) {
        logger.info("Updating course with ID: {}", course.getId());
        
        // Retrieve existing course to preserve data that might not be in the form
        Optional<Course> existingCourse = courseRepository.findById(course.getId());
        
        if (existingCourse.isPresent()) {
            // Preserve the existing thumbnail if no new one is uploaded
            if ((thumbnailFile == null || thumbnailFile.isEmpty()) && 
                existingCourse.get().getThumbnailUrl() != null) {
                course.setThumbnailUrl(existingCourse.get().getThumbnailUrl());
            }
            
            // If new thumbnail is uploaded, store it
            if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
                try {
                    String filename = storageService.store(thumbnailFile);
                    course.setThumbnailUrl(filename);
                    logger.info("Updated thumbnail with filename: {}", filename);
                } catch (Exception e) {
                    logger.error("Failed to store updated thumbnail: {}", e.getMessage());
                }
            }
        }
        
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        logger.info("Deleting course with ID: {}", id);
        courseRepository.deleteById(id);
    }
}
