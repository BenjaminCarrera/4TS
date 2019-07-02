import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReqCan } from 'app/shared/model/req-can.model';
import { ReqCanService } from './req-can.service';

@Component({
  selector: 'jhi-req-can-delete-dialog',
  templateUrl: './req-can-delete-dialog.component.html'
})
export class ReqCanDeleteDialogComponent {
  reqCan: IReqCan;

  constructor(protected reqCanService: ReqCanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reqCanService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reqCanListModification',
        content: 'Deleted an reqCan'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-req-can-delete-popup',
  template: ''
})
export class ReqCanDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reqCan }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReqCanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reqCan = reqCan;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/req-can', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/req-can', { outlets: { popup: null } }]);
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
