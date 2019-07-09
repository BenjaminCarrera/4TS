import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-con-req',
  templateUrl: './con-req.component.html',
  styleUrls: [
    'con-req.component.scss'
  ]
})
export class ConReqComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'ConReqComponent message';
  }

  ngOnInit() {
  }

}
