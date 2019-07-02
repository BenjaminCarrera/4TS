import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';
import { FuenteReclutamientoService } from './fuente-reclutamiento.service';

@Component({
  selector: 'jhi-fuente-reclutamiento-delete-dialog',
  templateUrl: './fuente-reclutamiento-delete-dialog.component.html'
})
export class FuenteReclutamientoDeleteDialogComponent {
  fuenteReclutamiento: IFuenteReclutamiento;

  constructor(
    protected fuenteReclutamientoService: FuenteReclutamientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fuenteReclutamientoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fuenteReclutamientoListModification',
        content: 'Deleted an fuenteReclutamiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fuente-reclutamiento-delete-popup',
  template: ''
})
export class FuenteReclutamientoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fuenteReclutamiento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FuenteReclutamientoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.fuenteReclutamiento = fuenteReclutamiento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fuente-reclutamiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fuente-reclutamiento', { outlets: { popup: null } }]);
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
