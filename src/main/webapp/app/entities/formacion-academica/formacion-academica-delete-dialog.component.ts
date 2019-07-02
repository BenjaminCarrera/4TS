import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormacionAcademica } from 'app/shared/model/formacion-academica.model';
import { FormacionAcademicaService } from './formacion-academica.service';

@Component({
  selector: 'jhi-formacion-academica-delete-dialog',
  templateUrl: './formacion-academica-delete-dialog.component.html'
})
export class FormacionAcademicaDeleteDialogComponent {
  formacionAcademica: IFormacionAcademica;

  constructor(
    protected formacionAcademicaService: FormacionAcademicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.formacionAcademicaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'formacionAcademicaListModification',
        content: 'Deleted an formacionAcademica'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-formacion-academica-delete-popup',
  template: ''
})
export class FormacionAcademicaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ formacionAcademica }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FormacionAcademicaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.formacionAcademica = formacionAcademica;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/formacion-academica', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/formacion-academica', { outlets: { popup: null } }]);
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
