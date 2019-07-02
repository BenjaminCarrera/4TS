import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from './requerimiento.service';

@Component({
  selector: 'jhi-requerimiento-delete-dialog',
  templateUrl: './requerimiento-delete-dialog.component.html'
})
export class RequerimientoDeleteDialogComponent {
  requerimiento: IRequerimiento;

  constructor(
    protected requerimientoService: RequerimientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.requerimientoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'requerimientoListModification',
        content: 'Deleted an requerimiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-requerimiento-delete-popup',
  template: ''
})
export class RequerimientoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RequerimientoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.requerimiento = requerimiento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/requerimiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/requerimiento', { outlets: { popup: null } }]);
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
