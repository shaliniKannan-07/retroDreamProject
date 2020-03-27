import { Component, OnInit, Input } from '@angular/core';



@Component({
  selector: 'app-navbars',
  templateUrl: './navbars.component.html',
  styleUrls: ['./navbars.component.css']
})


export class NavbarsComponent implements OnInit {



  submitted = false;

  constructor() { }

  ngOnInit() {
    console.log("hoiiiiiii");

  }

 
  onSubmit() { 
    this.submitted = true; 
  }
 

}
