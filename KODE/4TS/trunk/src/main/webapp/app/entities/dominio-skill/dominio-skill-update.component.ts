import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDominioSkill, DominioSkill } from 'app/shared/model/dominio-skill.model';
import { DominioSkillService } from './dominio-skill.service';

@Component({
  selector: 'jhi-dominio-skill-update',
  templateUrl: './dominio-skill-update.component.html'
})
export class DominioSkillUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    dominio: [null, [Validators.maxLength(100)]]
  });

  constructor(protected dominioSkillService: DominioSkillService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dominioSkill }) => {
      this.updateForm(dominioSkill);
    });
  }

  updateForm(dominioSkill: IDominioSkill) {
    this.editForm.patchValue({
      id: dominioSkill.id,
      dominio: dominioSkill.dominio
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dominioSkill = this.createFromForm();
    if (dominioSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.dominioSkillService.update(dominioSkill));
    } else {
      this.subscribeToSaveResponse(this.dominioSkillService.create(dominioSkill));
    }
  }

  private createFromForm(): IDominioSkill {
    return {
      ...new DominioSkill(),
      id: this.editForm.get(['id']).value,
      dominio: this.editForm.get(['dominio']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDominioSkill>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
