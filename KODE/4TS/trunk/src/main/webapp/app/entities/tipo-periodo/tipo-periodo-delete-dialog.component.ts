import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';
import { TipoPeriodoService } from './tipo-periodo.service';

@Component({
  selector: 'jhi-tipo-periodo-delete-dialog',
  templateUrl: './tipo-periodo-delete-dialog.component.html'
})
export class TipoPeriodoDeleteDialogComponent {
  tipoPeriodo: ITipoPeriodo;

  constructor(
    protected tipoPeriodoService: TipoPeriodoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoPeriodoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoPeriodoListModification',
        content: 'Deleted an tipoPeriodo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-periodo-delete-popup',
  template: ''
})
export class TipoPeriodoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoPeriodo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoPeriodoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoPeriodo = tipoPeriodo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-periodo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-periodo', { outlets: { popup: null } }]);
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
