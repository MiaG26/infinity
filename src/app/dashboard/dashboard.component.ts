import { Component } from '@angular/core';
import { UsersService } from 'src/services/users.service';
import { User } from 'src/entities/user';
import { Course } from 'src/entities/course';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/services/course.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  imports: [CommonModule],
  standalone: true
})
export class DashboardComponent {

  user: User
  courses: Course[]
  selectedCourse: Course

  constructor(
    private usersService: UsersService,
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute){}

    ngOnInit(): void {
      this.usersService.getUser().subscribe(u => {
        this.user = User.clone(u);
        this.usersService.getUserCourses(this.user.id).subscribe(c => this.courses = c);
      });
    }

    public get course(): Course {
      return this.selectedCourse;
    }

    selectCourse(course: Course) {
      this.usersService.setSelectedCourse(course);
      this.router.navigate(['../course'], { relativeTo: this.route });
    }
}
