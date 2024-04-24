import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, map, Observable } from 'rxjs';
import { Comment } from 'src/entities/comment';
import { MessageService } from './message.service';

export const DEFAULT_REDIRECT_AFTER_LOGIN = "/";

@Injectable({
    providedIn: 'root'
  })
export class CommentService {


    url = 'http://localhost:8080/';

    constructor(private http: HttpClient, 
        private messageService: MessageService) { }

    addComment(comment: Comment) {
        return this.http.post(this.url + 'comment/add/', comment).pipe(
            catchError(error => this.processError(error))
        )
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