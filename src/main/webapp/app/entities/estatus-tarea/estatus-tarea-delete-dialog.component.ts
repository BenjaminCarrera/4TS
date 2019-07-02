import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusTarea } from 'app/shared/model/estatus-tarea.model';
import { EstatusTareaService } from './estatus-tarea.service';

@Component({
  selector: 'jhi-estatus-tarea-delete-dialog',
  templateUrl: './estatus-tarea-delete-dialog.component.html'
})
export class EstatusTareaDeleteDialogComponent {
  estatusTarea: IEstatusTarea;

  constructor(
    protected estatusTareaService: EstatusTareaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusTareaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusTareaListModification',
        content: 'Deleted an estatusTarea'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-tarea-delete-popup',
  template: ''
})
export class EstatusTareaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusTarea }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusTareaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estatusTarea = estatusTarea;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus-tarea', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus-tarea', { outlets: { popup: null } }]);
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
