import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusReqCan } from 'app/shared/model/estatus-req-can.model';
import { EstatusReqCanService } from './estatus-req-can.service';

@Component({
  selector: 'jhi-estatus-req-can-delete-dialog',
  templateUrl: './estatus-req-can-delete-dialog.component.html'
})
export class EstatusReqCanDeleteDialogComponent {
  estatusReqCan: IEstatusReqCan;

  constructor(
    protected estatusReqCanService: EstatusReqCanService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusReqCanService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusReqCanListModification',
        content: 'Deleted an estatusReqCan'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-req-can-delete-popup',
  template: ''
})
export class EstatusReqCanDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusReqCan }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusReqCanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estatusReqCan = estatusReqCan;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus-req-can', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus-req-can', { outlets: { popup: null } }]);
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
