import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstitucionAcademica } from 'app/shared/model/institucion-academica.model';
import { InstitucionAcademicaService } from './institucion-academica.service';

@Component({
  selector: 'jhi-institucion-academica-delete-dialog',
  templateUrl: './institucion-academica-delete-dialog.component.html'
})
export class InstitucionAcademicaDeleteDialogComponent {
  institucionAcademica: IInstitucionAcademica;

  constructor(
    protected institucionAcademicaService: InstitucionAcademicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.institucionAcademicaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'institucionAcademicaListModification',
        content: 'Deleted an institucionAcademica'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-institucion-academica-delete-popup',
  template: ''
})
export class InstitucionAcademicaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ institucionAcademica }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InstitucionAcademicaDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.institucionAcademica = institucionAcademica;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/institucion-academica', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/institucion-academica', { outlets: { popup: null } }]);
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
