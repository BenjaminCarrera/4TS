import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPermisoAuthority } from 'app/shared/model/permiso-authority.model';
import { PermisoAuthorityService } from './permiso-authority.service';

@Component({
  selector: 'jhi-permiso-authority-delete-dialog',
  templateUrl: './permiso-authority-delete-dialog.component.html'
})
export class PermisoAuthorityDeleteDialogComponent {
  permisoAuthority: IPermisoAuthority;

  constructor(
    protected permisoAuthorityService: PermisoAuthorityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.permisoAuthorityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'permisoAuthorityListModification',
        content: 'Deleted an permisoAuthority'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-permiso-authority-delete-popup',
  template: ''
})
export class PermisoAuthorityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ permisoAuthority }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PermisoAuthorityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.permisoAuthority = permisoAuthority;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/permiso-authority', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/permiso-authority', { outlets: { popup: null } }]);
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
