import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBaseTarifa } from 'app/shared/model/base-tarifa.model';
import { BaseTarifaService } from './base-tarifa.service';

@Component({
  selector: 'jhi-base-tarifa-delete-dialog',
  templateUrl: './base-tarifa-delete-dialog.component.html'
})
export class BaseTarifaDeleteDialogComponent {
  baseTarifa: IBaseTarifa;

  constructor(
    protected baseTarifaService: BaseTarifaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.baseTarifaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'baseTarifaListModification',
        content: 'Deleted an baseTarifa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-base-tarifa-delete-popup',
  template: ''
})
export class BaseTarifaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ baseTarifa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BaseTarifaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.baseTarifa = baseTarifa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/base-tarifa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/base-tarifa', { outlets: { popup: null } }]);
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
