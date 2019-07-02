import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDominioSkill } from 'app/shared/model/dominio-skill.model';
import { DominioSkillService } from './dominio-skill.service';

@Component({
  selector: 'jhi-dominio-skill-delete-dialog',
  templateUrl: './dominio-skill-delete-dialog.component.html'
})
export class DominioSkillDeleteDialogComponent {
  dominioSkill: IDominioSkill;

  constructor(
    protected dominioSkillService: DominioSkillService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dominioSkillService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dominioSkillListModification',
        content: 'Deleted an dominioSkill'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dominio-skill-delete-popup',
  template: ''
})
export class DominioSkillDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dominioSkill }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DominioSkillDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dominioSkill = dominioSkill;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dominio-skill', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dominio-skill', { outlets: { popup: null } }]);
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
