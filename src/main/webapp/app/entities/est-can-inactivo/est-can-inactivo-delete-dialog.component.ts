import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstCanInactivo } from 'app/shared/model/est-can-inactivo.model';
import { EstCanInactivoService } from './est-can-inactivo.service';

@Component({
  selector: 'jhi-est-can-inactivo-delete-dialog',
  templateUrl: './est-can-inactivo-delete-dialog.component.html'
})
export class EstCanInactivoDeleteDialogComponent {
  estCanInactivo: IEstCanInactivo;

  constructor(
    protected estCanInactivoService: EstCanInactivoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estCanInactivoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estCanInactivoListModification',
        content: 'Deleted an estCanInactivo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-est-can-inactivo-delete-popup',
  template: ''
})
export class EstCanInactivoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estCanInactivo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstCanInactivoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estCanInactivo = estCanInactivo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/est-can-inactivo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/est-can-inactivo', { outlets: { popup: null } }]);
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
