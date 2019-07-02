import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISkill, Skill } from 'app/shared/model/skill.model';
import { SkillService } from './skill.service';
import { ICategoriaSkill } from 'app/shared/model/categoria-skill.model';
import { CategoriaSkillService } from 'app/entities/categoria-skill';

@Component({
  selector: 'jhi-skill-update',
  templateUrl: './skill-update.component.html'
})
export class SkillUpdateComponent implements OnInit {
  isSaving: boolean;

  categoriaskills: ICategoriaSkill[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.maxLength(100)]],
    categoriaSkillId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected skillService: SkillService,
    protected categoriaSkillService: CategoriaSkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skill }) => {
      this.updateForm(skill);
    });
    this.categoriaSkillService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoriaSkill[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoriaSkill[]>) => response.body)
      )
      .subscribe((res: ICategoriaSkill[]) => (this.categoriaskills = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(skill: ISkill) {
    this.editForm.patchValue({
      id: skill.id,
      nombre: skill.nombre,
      categoriaSkillId: skill.categoriaSkillId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skill = this.createFromForm();
    if (skill.id !== undefined) {
      this.subscribeToSaveResponse(this.skillService.update(skill));
    } else {
      this.subscribeToSaveResponse(this.skillService.create(skill));
    }
  }

  private createFromForm(): ISkill {
    return {
      ...new Skill(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      categoriaSkillId: this.editForm.get(['categoriaSkillId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkill>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCategoriaSkillById(index: number, item: ICategoriaSkill) {
    return item.id;
  }
}
