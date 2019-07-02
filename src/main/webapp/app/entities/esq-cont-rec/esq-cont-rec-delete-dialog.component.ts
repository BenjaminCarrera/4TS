import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';
import { EsqContRecService } from './esq-cont-rec.service';

@Component({
  selector: 'jhi-esq-cont-rec-delete-dialog',
  templateUrl: './esq-cont-rec-delete-dialog.component.html'
})
export class EsqContRecDeleteDialogComponent {
  esqContRec: IEsqContRec;

  constructor(
    protected esqContRecService: EsqContRecService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.esqContRecService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'esqContRecListModification',
        content: 'Deleted an esqContRec'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-esq-cont-rec-delete-popup',
  template: ''
})
export class EsqContRecDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ esqContRec }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EsqContRecDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.esqContRec = esqContRec;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/esq-cont-rec', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/esq-cont-rec', { outlets: { popup: null } }]);
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
