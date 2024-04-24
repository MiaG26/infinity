import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/services/users.service';
import { User } from 'src/entities/user';
import { CreditCard } from 'src/entities/creditCard';
import { Subscription } from 'src/entities/subscription';
import { Address } from 'src/entities/address';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{


  username = ''
  user: User
  creditCards: CreditCard[]
  latestSubscription: Subscription
  addresses: Address[]

  constructor(
    private usersService: UsersService  ){}

  ngOnInit(): void {
    this.username = this.usersService.username;
    this.creditCards = this.usersService.userCreditCards;
    this.usersService.getUser().subscribe(u => {
      this.user = User.clone(u)
      this.usersService.getUserCreditCard(this.user.id).subscribe(c => this.creditCards = c)
      this.usersService.getUserLatestSubscription(this.user.id).subscribe(s => this.latestSubscription = s)
      this.usersService.getUserAddress(this.user.id).subscribe(a => this.addresses = a)  
    });
    this.usersService.userNameChanges().subscribe(un => this.username = un);
  }


}
