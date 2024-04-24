import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, map, Observable } from 'rxjs';
import { Course } from 'src/entities/course';
import { Video } from 'src/entities/video';
import { MessageService } from './message.service';

export const DEFAULT_REDIRECT_AFTER_LOGIN = "/";

@Injectable({
    providedIn: 'root'
  })
export class CourseService {


    url = 'http://localhost:8080/';

    constructor(private http: HttpClient, 
        private messageService: MessageService) { }


    getAllCourses(): Observable<Course[]> {
        return this.http.get<Course[]>(this.url + 'course/').pipe(
            map(jsonObject => jsonObject.map(jsonCourse => Course.clone(jsonCourse))),
            catchError(error => this.processError(error))
            );
        }

    getCourseVideos(id: number): Observable<Video[]> {
        return this.http.get<Video[]>(this.url + 'course/'+ id + '/videos').pipe(
            map(jsonObject => jsonObject.map(jsonVideo => Video.clone(jsonVideo))),
            catchError(error => this.processError(error))
            );
        }

      processError(error:any): Observable<never> {
        console.log(error)
        if (error instanceof HttpErrorResponse) {
          if (error.status === 0) {
            this.messageService.errorMessage("Server unavailable");
            return EMPTY;
          }
          if (error.status < 500) {
            const message = error.error.errorMessage || JSON.parse(error.error).errorMessage;
            this.messageService.errorMessage(message);
            return EMPTY;
          }
          this.messageService.errorMessage("Server failed");
        }
        console.error(error);
        return EMPTY;
      }
}