import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/services/users.service';
import { User } from 'src/entities/user';
import { Subscription } from 'src/entities/subscription';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.css']
})
export class PricingComponent {

  user: User

  constructor(
    private usersService: UsersService  ){}

  ngOnInit(): void {
    this.usersService.getUser().subscribe(u => this.user = User.clone(u));
  }

  addSubscription(months: number): void {
    const startDate = new Date()
    const endDate = new Date()
    endDate.setMonth(endDate.getMonth() + months)
    const subscription = new Subscription(this.user.id, startDate, endDate)
    this.usersService.addSubsciption(subscription).subscribe(sub => console.log(sub))
  }

}
