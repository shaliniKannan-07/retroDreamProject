import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.css']
})
export class TimerComponent implements OnInit {

  constructor() { }
timeLeft: number ;
  interval;
  min:number=0;
  sec:number=0;
  skip:boolean=false;
  show:boolean=false;
  scroll:boolean=false;
  ngOnInit(): void {
    
  }
  

startTimer(e) {
    this.scroll=false;
    this.skip=true;
    this.min=e.target.value;
    this.timeLeft=e.target.value*60;
    console.log(this.timeLeft);
    this.interval = setInterval(() => {
      console.log(this.timeLeft);
      if(this.timeLeft > 0) {
        if(this.timeLeft%60==0){
        this.sec=60;
        this.min--;
      }
        this.timeLeft--;
        this.sec--;
        if(this.timeLeft < 11 ){
        this.show=true;     
      }
      } else {
        this.skip=false;
        this.show=false;
        clearInterval(this.interval);
      }  
    },1000)
  }
  reset(){
    clearInterval(this.interval);
    this.timeLeft=0;
    this.sec=0;
    this.min=0;
    this.skip=false;
    this.show=false;
  }
  submit(){
  this.scroll=true;
}
  
}
