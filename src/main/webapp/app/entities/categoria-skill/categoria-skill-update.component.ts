import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICategoriaSkill, CategoriaSkill } from 'app/shared/model/categoria-skill.model';
import { CategoriaSkillService } from './categoria-skill.service';

@Component({
  selector: 'jhi-categoria-skill-update',
  templateUrl: './categoria-skill-update.component.html'
})
export class CategoriaSkillUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    categoria: [null, [Validators.maxLength(100)]]
  });

  constructor(protected categoriaSkillService: CategoriaSkillService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ categoriaSkill }) => {
      this.updateForm(categoriaSkill);
    });
  }

  updateForm(categoriaSkill: ICategoriaSkill) {
    this.editForm.patchValue({
      id: categoriaSkill.id,
      categoria: categoriaSkill.categoria
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const categoriaSkill = this.createFromForm();
    if (categoriaSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaSkillService.update(categoriaSkill));
    } else {
      this.subscribeToSaveResponse(this.categoriaSkillService.create(categoriaSkill));
    }
  }

  private createFromForm(): ICategoriaSkill {
    return {
      ...new CategoriaSkill(),
      id: this.editForm.get(['id']).value,
      categoria: this.editForm.get(['categoria']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaSkill>>) {
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
