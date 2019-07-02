import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillCandidato } from 'app/shared/model/skill-candidato.model';
import { SkillCandidatoService } from './skill-candidato.service';

@Component({
  selector: 'jhi-skill-candidato-delete-dialog',
  templateUrl: './skill-candidato-delete-dialog.component.html'
})
export class SkillCandidatoDeleteDialogComponent {
  skillCandidato: ISkillCandidato;

  constructor(
    protected skillCandidatoService: SkillCandidatoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.skillCandidatoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'skillCandidatoListModification',
        content: 'Deleted an skillCandidato'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-skill-candidato-delete-popup',
  template: ''
})
export class SkillCandidatoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillCandidato }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SkillCandidatoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.skillCandidato = skillCandidato;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/skill-candidato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/skill-candidato', { outlets: { popup: null } }]);
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
