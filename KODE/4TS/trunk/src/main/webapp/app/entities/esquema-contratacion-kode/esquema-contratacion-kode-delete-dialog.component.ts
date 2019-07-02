import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';
import { EsquemaContratacionKodeService } from './esquema-contratacion-kode.service';

@Component({
  selector: 'jhi-esquema-contratacion-kode-delete-dialog',
  templateUrl: './esquema-contratacion-kode-delete-dialog.component.html'
})
export class EsquemaContratacionKodeDeleteDialogComponent {
  esquemaContratacionKode: IEsquemaContratacionKode;

  constructor(
    protected esquemaContratacionKodeService: EsquemaContratacionKodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.esquemaContratacionKodeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'esquemaContratacionKodeListModification',
        content: 'Deleted an esquemaContratacionKode'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-esquema-contratacion-kode-delete-popup',
  template: ''
})
export class EsquemaContratacionKodeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ esquemaContratacionKode }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EsquemaContratacionKodeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.esquemaContratacionKode = esquemaContratacionKode;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/esquema-contratacion-kode', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/esquema-contratacion-kode', { outlets: { popup: null } }]);
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
