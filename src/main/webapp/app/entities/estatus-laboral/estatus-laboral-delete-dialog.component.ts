import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusLaboral } from 'app/shared/model/estatus-laboral.model';
import { EstatusLaboralService } from './estatus-laboral.service';

@Component({
  selector: 'jhi-estatus-laboral-delete-dialog',
  templateUrl: './estatus-laboral-delete-dialog.component.html'
})
export class EstatusLaboralDeleteDialogComponent {
  estatusLaboral: IEstatusLaboral;

  constructor(
    protected estatusLaboralService: EstatusLaboralService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusLaboralService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusLaboralListModification',
        content: 'Deleted an estatusLaboral'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-laboral-delete-popup',
  template: ''
})
export class EstatusLaboralDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusLaboral }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusLaboralDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estatusLaboral = estatusLaboral;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus-laboral', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus-laboral', { outlets: { popup: null } }]);
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
