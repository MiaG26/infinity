import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/services/users.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  username = ''

  constructor(private usersService: UsersService){}

  ngOnInit(): void {
    this.username = this.usersService.username;
    this.usersService.userNameChanges().subscribe(un => this.username = un);
  }

  logout() {
    this.usersService.logout();
  }

}
