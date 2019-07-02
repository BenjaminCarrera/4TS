import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';
import { ReferenciasLaboralesService } from './referencias-laborales.service';

@Component({
  selector: 'jhi-referencias-laborales-delete-dialog',
  templateUrl: './referencias-laborales-delete-dialog.component.html'
})
export class ReferenciasLaboralesDeleteDialogComponent {
  referenciasLaborales: IReferenciasLaborales;

  constructor(
    protected referenciasLaboralesService: ReferenciasLaboralesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.referenciasLaboralesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'referenciasLaboralesListModification',
        content: 'Deleted an referenciasLaborales'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-referencias-laborales-delete-popup',
  template: ''
})
export class ReferenciasLaboralesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ referenciasLaborales }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReferenciasLaboralesDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.referenciasLaborales = referenciasLaborales;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/referencias-laborales', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/referencias-laborales', { outlets: { popup: null } }]);
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
