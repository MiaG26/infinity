import { Component, OnInit } from '@angular/core';
import { Course } from 'src/entities/course';
import { CourseService } from 'src/services/course.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  courses: Course[]
  indexes: Array<number>
  
  constructor( private courseService: CourseService) {this.indexes = [0,1,2,3,4,5]}


  ngOnInit(): void {
    this.courseService.getAllCourses().subscribe(c => this.courses = c)
  }
}


