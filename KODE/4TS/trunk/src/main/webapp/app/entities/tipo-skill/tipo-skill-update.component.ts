import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoSkill, TipoSkill } from 'app/shared/model/tipo-skill.model';
import { TipoSkillService } from './tipo-skill.service';

@Component({
  selector: 'jhi-tipo-skill-update',
  templateUrl: './tipo-skill-update.component.html'
})
export class TipoSkillUpdateComponent implements OnInit {
  tipoSkill: ITipoSkill;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.maxLength(100)]]
  });

  constructor(protected tipoSkillService: TipoSkillService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoSkill }) => {
      this.updateForm(tipoSkill);
      this.tipoSkill = tipoSkill;
    });
  }

  updateForm(tipoSkill: ITipoSkill) {
    this.editForm.patchValue({
      id: tipoSkill.id,
      tipo: tipoSkill.tipo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoSkill = this.createFromForm();
    if (tipoSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoSkillService.update(tipoSkill));
    } else {
      this.subscribeToSaveResponse(this.tipoSkillService.create(tipoSkill));
    }
  }

  private createFromForm(): ITipoSkill {
    const entity = {
      ...new TipoSkill(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoSkill>>) {
    result.subscribe((res: HttpResponse<ITipoSkill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
