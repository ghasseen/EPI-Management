-- Categories
INSERT INTO category (name, description) VALUES ('Programming', 'Courses related to programming languages and software development');
INSERT INTO category (name, description) VALUES ('Web Development', 'Courses focused on web technologies and frameworks');
INSERT INTO category (name, description) VALUES ('Data Science', 'Courses covering data analysis, machine learning, and statistics');
INSERT INTO category (name, description) VALUES ('Mobile Development', 'Courses for iOS, Android, and cross-platform mobile development');
INSERT INTO category (name, description) VALUES ('DevOps', 'Courses on DevOps practices, tools, and methodologies');

-- Instructors
INSERT INTO instructor (first_name, last_name, email, bio) VALUES ('John', 'Smith', 'john.smith@example.com', 'Experienced Java developer with 10+ years in the industry');
INSERT INTO instructor (first_name, last_name, email, bio) VALUES ('Sarah', 'Johnson', 'sarah.johnson@example.com', 'Web development expert specializing in modern JavaScript frameworks');
INSERT INTO instructor (first_name, last_name, email, bio) VALUES ('Michael', 'Brown', 'michael.brown@example.com', 'Data scientist with a background in machine learning and AI');
INSERT INTO instructor (first_name, last_name, email, bio) VALUES ('Emily', 'Davis', 'emily.davis@example.com', 'Mobile app developer with expertise in  bio) VALUES ('Emily', 'Davis', 'emily.davis@example.com', 'Mobile app developer with expertise in iOS and Android development');
INSERT INTO instructor (first_name, last_name, email, bio) VALUES ('David', 'Wilson', 'david.wilson@example.com', 'DevOps engineer with experience in cloud infrastructure and CI/CD pipelines');

-- Students
INSERT INTO student (first_name, last_name, email) VALUES ('Alex', 'Martinez', 'alex.martinez@example.com');
INSERT INTO student (first_name, last_name, email) VALUES ('Jessica', 'Taylor', 'jessica.taylor@example.com');
INSERT INTO student (first_name, last_name, email) VALUES ('Ryan', 'Anderson', 'ryan.anderson@example.com');
INSERT INTO student (first_name, last_name, email) VALUES ('Olivia', 'Thomas', 'olivia.thomas@example.com');
INSERT INTO student (first_name, last_name, email) VALUES ('Ethan', 'Jackson', 'ethan.jackson@example.com');

-- Courses
INSERT INTO course (title, description, duration_in_hours, instructor_id, category_id) 
VALUES ('Java Programming Fundamentals', 'Learn the basics of Java programming language', 30, 1, 1);

INSERT INTO course (title, description, duration_in_hours, instructor_id, category_id) 
VALUES ('Modern JavaScript', 'Master modern JavaScript concepts and frameworks', 25, 2, 2);

INSERT INTO course (title, description, duration_in_hours, instructor_id, category_id) 
VALUES ('Python for Data Science', 'Learn Python programming for data analysis and visualization', 40, 3, 3);

INSERT INTO course (title, description, duration_in_hours, instructor_id, category_id) 
VALUES ('Android App Development', 'Build Android applications using Kotlin', 35, 4, 4);

INSERT INTO course (title, description, duration_in_hours, instructor_id, category_id) 
VALUES ('Docker and Kubernetes', 'Learn containerization and orchestration with Docker and Kubernetes', 20, 5, 5);

