import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoSkill } from 'app/shared/model/tipo-skill.model';
import { TipoSkillService } from './tipo-skill.service';

@Component({
  selector: 'jhi-tipo-skill-delete-dialog',
  templateUrl: './tipo-skill-delete-dialog.component.html'
})
export class TipoSkillDeleteDialogComponent {
  tipoSkill: ITipoSkill;

  constructor(protected tipoSkillService: TipoSkillService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoSkillService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoSkillListModification',
        content: 'Deleted an tipoSkill'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-skill-delete-popup',
  template: ''
})
export class TipoSkillDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoSkill }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoSkillDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoSkill = tipoSkill;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-skill', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-skill', { outlets: { popup: null } }]);
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
