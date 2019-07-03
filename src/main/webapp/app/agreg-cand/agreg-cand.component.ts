import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-agreg-cand',
  templateUrl: './agreg-cand.component.html',
  styleUrls: [
    'agreg-cand.component.scss'
  ]
})
export class AgregCandComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'AgregCandComponent message';
  }

  ngOnInit() {
  }

}
