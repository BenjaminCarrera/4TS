import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-agreg-req',
  templateUrl: './agreg-req.component.html',
  styleUrls: [
    'agreg-req.component.scss'
  ]
})
export class AgregReqComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'AgregReqComponent message';
  }

  ngOnInit() {
  }

}
