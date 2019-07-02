import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IColonia } from 'app/shared/model/colonia.model';

@Component({
  selector: 'jhi-colonia-detail',
  templateUrl: './colonia-detail.component.html'
})
export class ColoniaDetailComponent implements OnInit {
  colonia: IColonia;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ colonia }) => {
      this.colonia = colonia;
    });
  }

  previousState() {
    window.history.back();
  }
}
