import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISkillCandidato, SkillCandidato } from 'app/shared/model/skill-candidato.model';
import { SkillCandidatoService } from './skill-candidato.service';
import { ICandidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from 'app/entities/candidato';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { IDominioSkill } from 'app/shared/model/dominio-skill.model';
import { DominioSkillService } from 'app/entities/dominio-skill';

@Component({
  selector: 'jhi-skill-candidato-update',
  templateUrl: './skill-candidato-update.component.html'
})
export class SkillCandidatoUpdateComponent implements OnInit {
  isSaving: boolean;

  candidatoes: ICandidato[];

  skills: ISkill[];

  dominioskills: IDominioSkill[];

  editForm = this.fb.group({
    id: [],
    calificacionSkill: [],
    idCandidatoId: [],
    idSkillId: [],
    nivelSkillId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected skillCandidatoService: SkillCandidatoService,
    protected candidatoService: CandidatoService,
    protected skillService: SkillService,
    protected dominioSkillService: DominioSkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skillCandidato }) => {
      this.updateForm(skillCandidato);
    });
    this.candidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICandidato[]>) => response.body)
      )
      .subscribe((res: ICandidato[]) => (this.candidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.skillService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISkill[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISkill[]>) => response.body)
      )
      .subscribe((res: ISkill[]) => (this.skills = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.dominioSkillService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDominioSkill[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDominioSkill[]>) => response.body)
      )
      .subscribe((res: IDominioSkill[]) => (this.dominioskills = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(skillCandidato: ISkillCandidato) {
    this.editForm.patchValue({
      id: skillCandidato.id,
      calificacionSkill: skillCandidato.calificacionSkill,
      idCandidatoId: skillCandidato.idCandidatoId,
      idSkillId: skillCandidato.idSkillId,
      nivelSkillId: skillCandidato.nivelSkillId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skillCandidato = this.createFromForm();
    if (skillCandidato.id !== undefined) {
      this.subscribeToSaveResponse(this.skillCandidatoService.update(skillCandidato));
    } else {
      this.subscribeToSaveResponse(this.skillCandidatoService.create(skillCandidato));
    }
  }

  private createFromForm(): ISkillCandidato {
    return {
      ...new SkillCandidato(),
      id: this.editForm.get(['id']).value,
      calificacionSkill: this.editForm.get(['calificacionSkill']).value,
      idCandidatoId: this.editForm.get(['idCandidatoId']).value,
      idSkillId: this.editForm.get(['idSkillId']).value,
      nivelSkillId: this.editForm.get(['nivelSkillId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkillCandidato>>) {
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

  trackCandidatoById(index: number, item: ICandidato) {
    return item.id;
  }

  trackSkillById(index: number, item: ISkill) {
    return item.id;
  }

  trackDominioSkillById(index: number, item: IDominioSkill) {
    return item.id;
  }
}
