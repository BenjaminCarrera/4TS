import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstatusCandidato } from 'app/shared/model/estatus-candidato.model';
import { EstatusCandidatoService } from './estatus-candidato.service';

@Component({
  selector: 'jhi-estatus-candidato-delete-dialog',
  templateUrl: './estatus-candidato-delete-dialog.component.html'
})
export class EstatusCandidatoDeleteDialogComponent {
  estatusCandidato: IEstatusCandidato;

  constructor(
    protected estatusCandidatoService: EstatusCandidatoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estatusCandidatoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estatusCandidatoListModification',
        content: 'Deleted an estatusCandidato'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estatus-candidato-delete-popup',
  template: ''
})
export class EstatusCandidatoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusCandidato }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstatusCandidatoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estatusCandidato = estatusCandidato;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estatus-candidato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estatus-candidato', { outlets: { popup: null } }]);
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
