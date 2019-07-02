import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoTarea } from 'app/shared/model/tipo-tarea.model';
import { TipoTareaService } from './tipo-tarea.service';

@Component({
  selector: 'jhi-tipo-tarea-delete-dialog',
  templateUrl: './tipo-tarea-delete-dialog.component.html'
})
export class TipoTareaDeleteDialogComponent {
  tipoTarea: ITipoTarea;

  constructor(protected tipoTareaService: TipoTareaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoTareaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoTareaListModification',
        content: 'Deleted an tipoTarea'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-tarea-delete-popup',
  template: ''
})
export class TipoTareaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoTarea }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoTareaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoTarea = tipoTarea;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-tarea', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-tarea', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
