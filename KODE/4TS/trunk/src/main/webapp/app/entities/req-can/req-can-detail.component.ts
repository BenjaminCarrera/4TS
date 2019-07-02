import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReqCan } from 'app/shared/model/req-can.model';

@Component({
  selector: 'jhi-req-can-detail',
  templateUrl: './req-can-detail.component.html'
})
export class ReqCanDetailComponent implements OnInit {
  reqCan: IReqCan;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reqCan }) => {
      this.reqCan = reqCan;
    });
  }

  previousState() {
    window.history.back();
  }
}
