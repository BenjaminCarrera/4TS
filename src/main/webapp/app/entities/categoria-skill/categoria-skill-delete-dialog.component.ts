import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaSkill } from 'app/shared/model/categoria-skill.model';
import { CategoriaSkillService } from './categoria-skill.service';

@Component({
  selector: 'jhi-categoria-skill-delete-dialog',
  templateUrl: './categoria-skill-delete-dialog.component.html'
})
export class CategoriaSkillDeleteDialogComponent {
  categoriaSkill: ICategoriaSkill;

  constructor(
    protected categoriaSkillService: CategoriaSkillService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.categoriaSkillService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'categoriaSkillListModification',
        content: 'Deleted an categoriaSkill'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-categoria-skill-delete-popup',
  template: ''
})
export class CategoriaSkillDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ categoriaSkill }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CategoriaSkillDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.categoriaSkill = categoriaSkill;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/categoria-skill', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/categoria-skill', { outlets: { popup: null } }]);
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
