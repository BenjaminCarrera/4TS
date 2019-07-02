import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrioridadReq } from 'app/shared/model/prioridad-req.model';
import { PrioridadReqService } from './prioridad-req.service';

@Component({
  selector: 'jhi-prioridad-req-delete-dialog',
  templateUrl: './prioridad-req-delete-dialog.component.html'
})
export class PrioridadReqDeleteDialogComponent {
  prioridadReq: IPrioridadReq;

  constructor(
    protected prioridadReqService: PrioridadReqService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.prioridadReqService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'prioridadReqListModification',
        content: 'Deleted an prioridadReq'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-prioridad-req-delete-popup',
  template: ''
})
export class PrioridadReqDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prioridadReq }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PrioridadReqDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.prioridadReq = prioridadReq;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/prioridad-req', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/prioridad-req', { outlets: { popup: null } }]);
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
