import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';
import { SkillRequerimientoService } from './skill-requerimiento.service';

@Component({
  selector: 'jhi-skill-requerimiento-delete-dialog',
  templateUrl: './skill-requerimiento-delete-dialog.component.html'
})
export class SkillRequerimientoDeleteDialogComponent {
  skillRequerimiento: ISkillRequerimiento;

  constructor(
    protected skillRequerimientoService: SkillRequerimientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.skillRequerimientoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'skillRequerimientoListModification',
        content: 'Deleted an skillRequerimiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-skill-requerimiento-delete-popup',
  template: ''
})
export class SkillRequerimientoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillRequerimiento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SkillRequerimientoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.skillRequerimiento = skillRequerimiento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/skill-requerimiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/skill-requerimiento', { outlets: { popup: null } }]);
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
