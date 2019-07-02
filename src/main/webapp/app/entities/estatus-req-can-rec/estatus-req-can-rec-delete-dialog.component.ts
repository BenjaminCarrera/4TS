import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';
import { EstatusReqCanRecService } from './estatus-req-can-rec.service';

@Component({
  selector: 'jhi-estatus-req-can-rec-delete-dialog',
  templateUrl: './estatus-req-can-rec-delete-dialog.component.html'
})
export class EstatusReqCanRecDeleteDialogComponent {
  estatusReqCanRec: IEstatusReqCanRec;

  constructor(
    protected estatusReqCanRecService: EstatusReqCanRecService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusReqCanRecService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusReqCanRecListModification',
        content: 'Deleted an estatusReqCanRec'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-req-can-rec-delete-popup',
  template: ''
})
export class EstatusReqCanRecDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusReqCanRec }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusReqCanRecDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estatusReqCanRec = estatusReqCanRec;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus-req-can-rec', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus-req-can-rec', { outlets: { popup: null } }]);
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
