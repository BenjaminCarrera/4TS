import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoIngreso } from 'app/shared/model/tipo-ingreso.model';
import { TipoIngresoService } from './tipo-ingreso.service';

@Component({
  selector: 'jhi-tipo-ingreso-delete-dialog',
  templateUrl: './tipo-ingreso-delete-dialog.component.html'
})
export class TipoIngresoDeleteDialogComponent {
  tipoIngreso: ITipoIngreso;

  constructor(
    protected tipoIngresoService: TipoIngresoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoIngresoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoIngresoListModification',
        content: 'Deleted an tipoIngreso'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-ingreso-delete-popup',
  template: ''
})
export class TipoIngresoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoIngreso }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoIngresoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoIngreso = tipoIngreso;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-ingreso', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-ingreso', { outlets: { popup: null } }]);
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
