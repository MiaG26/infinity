import { Component } from '@angular/core';
import { UsersService } from 'src/services/users.service';
import { CourseService } from 'src/services/course.service';
import { User } from 'src/entities/user';
import { Course } from 'src/entities/course';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css'],
  imports: [CommonModule],
  standalone: true
})
export class CoursesComponent {

  user: User
  courses: Course[]

  constructor(
    private usersService: UsersService,
    private courseService: CourseService ){}

  ngOnInit(): void {
    this.usersService.getUser().subscribe(u => this.user = User.clone(u));
    this.courseService.getAllCourses().subscribe(c => this.courses = c)
  }

  addCourseToUser(courseId: number): void {
     this.usersService.addCourseToUser(this.user.id, courseId).subscribe()
  }
  

}
