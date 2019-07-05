import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-con-cand',
  templateUrl: './con-cand.component.html',
  styleUrls: [
    'con-cand.component.scss'
  ]
})
export class ConCandComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'ConCandComponent message';
  }

  ngOnInit() {
  }

}
