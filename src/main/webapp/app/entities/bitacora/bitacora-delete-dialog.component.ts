import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBitacora } from 'app/shared/model/bitacora.model';
import { BitacoraService } from './bitacora.service';

@Component({
  selector: 'jhi-bitacora-delete-dialog',
  templateUrl: './bitacora-delete-dialog.component.html'
})
export class BitacoraDeleteDialogComponent {
  bitacora: IBitacora;

  constructor(protected bitacoraService: BitacoraService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bitacoraService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bitacoraListModification',
        content: 'Deleted an bitacora'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-bitacora-delete-popup',
  template: ''
})
export class BitacoraDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bitacora }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BitacoraDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bitacora = bitacora;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/bitacora', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/bitacora', { outlets: { popup: null } }]);
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
