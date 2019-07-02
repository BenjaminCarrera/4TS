import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoTarea } from 'app/shared/model/tipo-tarea.model';

@Component({
  selector: 'jhi-tipo-tarea-detail',
  templateUrl: './tipo-tarea-detail.component.html'
})
export class TipoTareaDetailComponent implements OnInit {
  tipoTarea: ITipoTarea;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoTarea }) => {
      this.tipoTarea = tipoTarea;
    });
  }

  previousState() {
    window.history.back();
  }
}
