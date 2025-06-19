package com.epi.coursemanagement.config;

import com.epi.coursemanagement.model.Category;
import com.epi.coursemanagement.model.Course;
import com.epi.coursemanagement.model.Instructor;
import com.epi.coursemanagement.model.Student;
import com.epi.coursemanagement.repository.CategoryRepository;
import com.epi.coursemanagement.repository.CourseRepository;
import com.epi.coursemanagement.repository.InstructorRepository;
import com.epi.coursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner loadData(
            CategoryRepository categoryRepository,
            InstructorRepository instructorRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        
        return args -> {
            // Only initialize if the database is empty
            if (categoryRepository.count() == 0) {
                // Initialize Categories
                Category programming = new Category();
                programming.setName("Programming");
                programming.setDescription("Courses related to programming languages and software development");
                categoryRepository.save(programming);
                
                Category webDev = new Category();
                webDev.setName("Web Development");
                webDev.setDescription("Courses focused on web technologies and frameworks");
                categoryRepository.save(webDev);
                
                Category dataScience = new Category();
                dataScience.setName("Data Science");
                dataScience.setDescription("Courses covering data analysis, machine learning, and statistics");
                categoryRepository.save(dataScience);
                
                Category mobileDev = new Category();
                mobileDev.setName("Mobile Development");
                mobileDev.setDescription("Courses for iOS, Android, and cross-platform mobile development");
                categoryRepository.save(mobileDev);
                
                Category devOps = new Category();
                devOps.setName("DevOps");
                devOps.setDescription("Courses on DevOps practices, tools, and methodologies");
                categoryRepository.save(devOps);
                
                // Initialize Instructors
                Instructor john = new Instructor();
                john.setFirstName("John");
                john.setLastName("Smith");
                john.setEmail("john.smith@example.com");
                john.setBio("Experienced Java developer with 10+ years in the industry");
                instructorRepository.save(john);
                
                Instructor sarah = new Instructor();
                sarah.setFirstName("Sarah");
                sarah.setLastName("Johnson");
                sarah.setEmail("sarah.johnson@example.com");
                sarah.setBio("Web development expert specializing in modern JavaScript frameworks");
                instructorRepository.save(sarah);
                
                Instructor michael = new Instructor();
                michael.setFirstName("Michael");
                michael.setLastName("Brown");
                michael.setEmail("michael.brown@example.com");
                michael.setBio("Data scientist with a background in machine learning and AI");
                instructorRepository.save(michael);
                
                Instructor emily = new Instructor();
                emily.setFirstName("Emily");
                emily.setLastName("Davis");
                emily.setEmail("emily.davis@example.com");
                emily.setBio("Mobile app developer with expertise in iOS and Android development");
                instructorRepository.save(emily);
                
                Instructor david = new Instructor();
                david.setFirstName("David");
                david.setLastName("Wilson");
                david.setEmail("david.wilson@example.com");
                david.setBio("DevOps engineer with experience in cloud infrastructure and CI/CD pipelines");
                instructorRepository.save(david);
                
                // Initialize Students
                Student alex = new Student();
                alex.setFirstName("Alex");
                alex.setLastName("Martinez");
                alex.setEmail("alex.martinez@example.com");
                studentRepository.save(alex);
                
                Student jessica = new Student();
                jessica.setFirstName("Jessica");
                jessica.setLastName("Taylor");
                jessica.setEmail("jessica.taylor@example.com");
                studentRepository.save(jessica);
                
                Student ryan = new Student();
                ryan.setFirstName("Ryan");
                ryan.setLastName("Anderson");
                ryan.setEmail("ryan.anderson@example.com");
                studentRepository.save(ryan);
                
                Student olivia = new Student();
                olivia.setFirstName("Olivia");
                olivia.setLastName("Thomas");
                olivia.setEmail("olivia.thomas@example.com");
                studentRepository.save(olivia);
                
                Student ethan = new Student();
                ethan.setFirstName("Ethan");
                ethan.setLastName("Jackson");
                ethan.setEmail("ethan.jackson@example.com");
                studentRepository.save(ethan);
                
                // Initialize Courses
                Course java = new Course();
                java.setTitle("Java Programming Fundamentals");
                java.setDescription("Learn the basics of Java programming language");
                java.setDurationInHours(30);
                java.setInstructor(john);
                java.setCategory(programming);
                courseRepository.save(java);
                
                Course javascript = new Course();
                javascript.setTitle("Modern JavaScript");
                javascript.setDescription("Master modern JavaScript concepts and frameworks");
                javascript.setDurationInHours(25);
                javascript.setInstructor(sarah);
                javascript.setCategory(webDev);
                courseRepository.save(javascript);
                
                Course python = new Course();
                python.setTitle("Python for Data Science");
                python.setDescription("Learn Python programming for data analysis and visualization");
                python.setDurationInHours(40);
                python.setInstructor(michael);
                python.setCategory(dataScience);
                courseRepository.save(python);
                
                Course android = new Course();
                android.setTitle("Android App Development");
                android.setDescription("Build Android applications using Kotlin");
                android.setDurationInHours(35);
                android.setInstructor(emily);
                android.setCategory(mobileDev);
                courseRepository.save(android);
                
                Course docker = new Course();
                docker.setTitle("Docker and Kubernetes");
                docker.setDescription("Learn containerization and orchestration with Docker and Kubernetes");
                docker.setDurationInHours(20);
                docker.setInstructor(david);
                docker.setCategory(devOps);
                courseRepository.save(docker);
                
                // Enroll students in courses
                alex.getEnrolledCourses().add(java);
                alex.getEnrolledCourses().add(javascript);
                studentRepository.save(alex);
                
                jessica.getEnrolledCourses().add(javascript);
                jessica.getEnrolledCourses().add(python);
                studentRepository.save(jessica);
                
                ryan.getEnrolledCourses().add(python);
                ryan.getEnrolledCourses().add(android);
                studentRepository.save(ryan);
                
                olivia.getEnrolledCourses().add(android);
                olivia.getEnrolledCourses().add(docker);
                studentRepository.save(olivia);
                
                ethan.getEnrolledCourses().add(docker);
                ethan.getEnrolledCourses().add(java);
                studentRepository.save(ethan);
            }
        };
    }
}
