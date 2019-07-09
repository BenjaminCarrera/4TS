import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-res-conreq',
  templateUrl: './res-conreq.component.html',
  styleUrls: [
    'res-conreq.component.scss'
  ]
})
export class ResConreqComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'ResConreqComponent message';
  }

  ngOnInit() {
  }

}
