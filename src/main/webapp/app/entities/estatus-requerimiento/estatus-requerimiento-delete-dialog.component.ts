import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';
import { EstatusRequerimientoService } from './estatus-requerimiento.service';

@Component({
  selector: 'jhi-estatus-requerimiento-delete-dialog',
  templateUrl: './estatus-requerimiento-delete-dialog.component.html'
})
export class EstatusRequerimientoDeleteDialogComponent {
  estatusRequerimiento: IEstatusRequerimiento;

  constructor(
    protected estatusRequerimientoService: EstatusRequerimientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusRequerimientoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusRequerimientoListModification',
        content: 'Deleted an estatusRequerimiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-requerimiento-delete-popup',
  template: ''
})
export class EstatusRequerimientoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusRequerimiento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusRequerimientoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.estatusRequerimiento = estatusRequerimiento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus-requerimiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus-requerimiento', { outlets: { popup: null } }]);
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
