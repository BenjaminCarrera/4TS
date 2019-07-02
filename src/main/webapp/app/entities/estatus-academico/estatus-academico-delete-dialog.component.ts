import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusAcademico } from 'app/shared/model/estatus-academico.model';
import { EstatusAcademicoService } from './estatus-academico.service';

@Component({
  selector: 'jhi-estatus-academico-delete-dialog',
  templateUrl: './estatus-academico-delete-dialog.component.html'
})
export class EstatusAcademicoDeleteDialogComponent {
  estatusAcademico: IEstatusAcademico;

  constructor(
    protected estatusAcademicoService: EstatusAcademicoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusAcademicoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusAcademicoListModification',
        content: 'Deleted an estatusAcademico'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-academico-delete-popup',
  template: ''
})
export class EstatusAcademicoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusAcademico }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusAcademicoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estatusAcademico = estatusAcademico;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus-academico', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus-academico', { outlets: { popup: null } }]);
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
