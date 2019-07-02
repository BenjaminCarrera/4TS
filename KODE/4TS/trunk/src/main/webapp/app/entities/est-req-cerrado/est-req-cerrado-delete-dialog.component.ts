import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstReqCerrado } from 'app/shared/model/est-req-cerrado.model';
import { EstReqCerradoService } from './est-req-cerrado.service';

@Component({
  selector: 'jhi-est-req-cerrado-delete-dialog',
  templateUrl: './est-req-cerrado-delete-dialog.component.html'
})
export class EstReqCerradoDeleteDialogComponent {
  estReqCerrado: IEstReqCerrado;

  constructor(
    protected estReqCerradoService: EstReqCerradoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estReqCerradoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estReqCerradoListModification',
        content: 'Deleted an estReqCerrado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-est-req-cerrado-delete-popup',
  template: ''
})
export class EstReqCerradoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estReqCerrado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstReqCerradoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estReqCerrado = estReqCerrado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/est-req-cerrado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/est-req-cerrado', { outlets: { popup: null } }]);
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
