import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISkillRequerimiento, SkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';
import { SkillRequerimientoService } from './skill-requerimiento.service';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from 'app/entities/requerimiento';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { ITipoSkill } from 'app/shared/model/tipo-skill.model';
import { TipoSkillService } from 'app/entities/tipo-skill';

@Component({
  selector: 'jhi-skill-requerimiento-update',
  templateUrl: './skill-requerimiento-update.component.html'
})
export class SkillRequerimientoUpdateComponent implements OnInit {
  skillRequerimiento: ISkillRequerimiento;
  isSaving: boolean;

  requerimientos: IRequerimiento[];

  skills: ISkill[];

  tiposkills: ITipoSkill[];

  editForm = this.fb.group({
    id: [],
    idRequerimientoId: [],
    idSkillId: [],
    tipoSkillId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected skillRequerimientoService: SkillRequerimientoService,
    protected requerimientoService: RequerimientoService,
    protected skillService: SkillService,
    protected tipoSkillService: TipoSkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skillRequerimiento }) => {
      this.updateForm(skillRequerimiento);
      this.skillRequerimiento = skillRequerimiento;
    });
    this.requerimientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRequerimiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRequerimiento[]>) => response.body)
      )
      .subscribe((res: IRequerimiento[]) => (this.requerimientos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.skillService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISkill[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISkill[]>) => response.body)
      )
      .subscribe((res: ISkill[]) => (this.skills = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoSkillService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoSkill[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoSkill[]>) => response.body)
      )
      .subscribe((res: ITipoSkill[]) => (this.tiposkills = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(skillRequerimiento: ISkillRequerimiento) {
    this.editForm.patchValue({
      id: skillRequerimiento.id,
      idRequerimientoId: skillRequerimiento.idRequerimientoId,
      idSkillId: skillRequerimiento.idSkillId,
      tipoSkillId: skillRequerimiento.tipoSkillId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skillRequerimiento = this.createFromForm();
    if (skillRequerimiento.id !== undefined) {
      this.subscribeToSaveResponse(this.skillRequerimientoService.update(skillRequerimiento));
    } else {
      this.subscribeToSaveResponse(this.skillRequerimientoService.create(skillRequerimiento));
    }
  }

  private createFromForm(): ISkillRequerimiento {
    const entity = {
      ...new SkillRequerimiento(),
      id: this.editForm.get(['id']).value,
      idRequerimientoId: this.editForm.get(['idRequerimientoId']).value,
      idSkillId: this.editForm.get(['idSkillId']).value,
      tipoSkillId: this.editForm.get(['tipoSkillId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkillRequerimiento>>) {
    result.subscribe((res: HttpResponse<ISkillRequerimiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRequerimientoById(index: number, item: IRequerimiento) {
    return item.id;
  }

  trackSkillById(index: number, item: ISkill) {
    return item.id;
  }

  trackTipoSkillById(index: number, item: ITipoSkill) {
    return item.id;
  }
}
