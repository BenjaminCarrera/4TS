import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColonia } from 'app/shared/model/colonia.model';
import { ColoniaService } from './colonia.service';

@Component({
  selector: 'jhi-colonia-delete-dialog',
  templateUrl: './colonia-delete-dialog.component.html'
})
export class ColoniaDeleteDialogComponent {
  colonia: IColonia;

  constructor(protected coloniaService: ColoniaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.coloniaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'coloniaListModification',
        content: 'Deleted an colonia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-colonia-delete-popup',
  template: ''
})
export class ColoniaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ colonia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ColoniaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.colonia = colonia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/colonia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/colonia', { outlets: { popup: null } }]);
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
