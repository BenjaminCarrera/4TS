import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusTarea } from 'app/shared/model/estatus-tarea.model';

@Component({
  selector: 'jhi-estatus-tarea-detail',
  templateUrl: './estatus-tarea-detail.component.html'
})
export class EstatusTareaDetailComponent implements OnInit {
  estatusTarea: IEstatusTarea;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusTarea }) => {
      this.estatusTarea = estatusTarea;
    });
  }

  previousState() {
    window.history.back();
  }
}
