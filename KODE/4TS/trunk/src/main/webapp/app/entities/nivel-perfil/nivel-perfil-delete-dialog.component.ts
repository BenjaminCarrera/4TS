import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from './nivel-perfil.service';

@Component({
  selector: 'jhi-nivel-perfil-delete-dialog',
  templateUrl: './nivel-perfil-delete-dialog.component.html'
})
export class NivelPerfilDeleteDialogComponent {
  nivelPerfil: INivelPerfil;

  constructor(
    protected nivelPerfilService: NivelPerfilService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nivelPerfilService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'nivelPerfilListModification',
        content: 'Deleted an nivelPerfil'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nivel-perfil-delete-popup',
  template: ''
})
export class NivelPerfilDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nivelPerfil }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NivelPerfilDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.nivelPerfil = nivelPerfil;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/nivel-perfil', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/nivel-perfil', { outlets: { popup: null } }]);
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
