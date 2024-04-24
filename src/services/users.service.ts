import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, map, Observable, Subject } from 'rxjs';
import { Auth } from 'src/entities/auth';
import { User } from 'src/entities/user';
import { CreditCard } from 'src/entities/creditCard';
import { Subscription } from 'src/entities/subscription';
import { Address } from 'src/entities/address';
import { Course } from 'src/entities/course';
import { MessageService } from './message.service';

export const DEFAULT_REDIRECT_AFTER_LOGIN = "/";

const httpOptions = {
    headers: new HttpHeaders({ 
      'Access-Control-Allow-Origin':'*',
    })
  };

@Injectable({
    providedIn: 'root'
  })
export class UsersService {

  url = 'http://localhost:8080/';
  public redirectAfterLogin = DEFAULT_REDIRECT_AFTER_LOGIN;
  
  userNameSubject = new Subject<string>();
  user: User;
  userCreditCards: CreditCard[];
  userSubscription: Subscription;
  userAddresses: Address[];
  userCourses: Course[];

  public selectedCourse: Course;

  constructor(private http: HttpClient, 
              private messageService: MessageService) { }


  public getSelectedCourse(): Course {
    return this.selectedCourse;
  }
        
  public setSelectedCourse(course: Course): void {
    this.selectedCourse = course;
  }

  private get token(): string {
    return localStorage.getItem('token') || '';
  }

  private set token(value: string) {
    if (value) {
      localStorage.setItem('token',value);
    } else {
      localStorage.removeItem('token');
    }
  }

  get username(): string {
    return localStorage.getItem('username') || '';
  }

  private set username(value: string) {
    if (value) {
      localStorage.setItem('username',value);
    } else {
      localStorage.removeItem('username');
    }
    this.userNameSubject.next(value);
  }

  userNameChanges(): Observable<string> {
    return this.userNameSubject.asObservable();
  }

    logIn(auth:Auth): Observable<boolean>{
        console.log(auth)
        return this.http.post(this.url + 'login', auth, {responseType: 'text'}).pipe(
          map(token => {
            this.username = auth.login;
            this.token = token;
            this.messageService.successMessage(
              "User " + this.username + " logged in successfully.");
            return true;
          }),
          catchError(error => this.processError(error))
        );
      }

      logout() {
        this.http.get(this.url + 'logout/' + this.token).pipe(
          catchError(error => this.processError(error))
        ).subscribe(()=> {
          this.username = '';
          this.token = '';
        });
      }

      register(user: User) {
        return this.http.post(this.url+`register`, user, httpOptions);
    }

    getUser(): Observable<User> {
        return this.http.get<User>(this.url + 'user/' + this.token).pipe(
          map(jsonUser => this.user = User.clone(jsonUser)),
          catchError(error => this.processError(error))
          );
      }

      getUserAddress(id: number): Observable<Address[]> {
        return this.http.get<Address[]>(this.url + 'address/' + id).pipe(
            map(jsonObject => jsonObject.map(jsonAddress => Address.clone(jsonAddress))),
            catchError(error => this.processError(error))
            );
      }

      getUserCreditCard(id: number): Observable<CreditCard[]> {
        return this.http.get<CreditCard[]>(this.url + 'creditCard/' + id).pipe(
          map(jsonObject => jsonObject.map(jsonCard => CreditCard.clone(jsonCard))),
          catchError(error => this.processError(error))
          );
      }

      getUserLatestSubscription(id: number): Observable<Subscription> {
        return this.http.get<Subscription>(this.url + 'subscription/' + id).pipe(
          map(jsonSub => this.userSubscription = Subscription.clone(jsonSub)),
          catchError(error => this.processError(error))
          );
      }

      getUserCourses(id: number): Observable<Course[]> {
        return this.http.get<Course[]>(this.url + 'user/courses/' + id).pipe(
          map(jsonObject => jsonObject.map(jsonCourse => Course.clone(jsonCourse))),
          catchError(error => this.processError(error))
          );
      }

      addSubsciption(subscription: Subscription): Observable<Subscription> {
        console.log(subscription)
        return this.http.post(this.url + 'subscription/add/', subscription).pipe(
            map(sub => this.userSubscription = Subscription.clone(sub as Subscription)),
            catchError(error => this.processError(error))
        )
      }

      addCourseToUser(userId: number, courseId: number) {
        return this.http.post(this.url + 'course/add/' + userId + '/' + courseId, null).pipe(
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