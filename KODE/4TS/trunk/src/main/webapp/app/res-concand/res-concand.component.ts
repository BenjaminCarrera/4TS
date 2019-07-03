import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-res-concand',
  templateUrl: './res-concand.component.html',
  styleUrls: [
    'res-concand.component.scss'
  ]
})
export class ResConcandComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'ResConcandComponent message';
  }

  ngOnInit() {
  }

}
