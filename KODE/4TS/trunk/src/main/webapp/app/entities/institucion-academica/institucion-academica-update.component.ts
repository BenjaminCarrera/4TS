import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInstitucionAcademica, InstitucionAcademica } from 'app/shared/model/institucion-academica.model';
import { InstitucionAcademicaService } from './institucion-academica.service';

@Component({
  selector: 'jhi-institucion-academica-update',
  templateUrl: './institucion-academica-update.component.html'
})
export class InstitucionAcademicaUpdateComponent implements OnInit {
  institucionAcademica: IInstitucionAcademica;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    institucion: [null, [Validators.maxLength(200)]]
  });

  constructor(
    protected institucionAcademicaService: InstitucionAcademicaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ institucionAcademica }) => {
      this.updateForm(institucionAcademica);
      this.institucionAcademica = institucionAcademica;
    });
  }

  updateForm(institucionAcademica: IInstitucionAcademica) {
    this.editForm.patchValue({
      id: institucionAcademica.id,
      institucion: institucionAcademica.institucion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const institucionAcademica = this.createFromForm();
    if (institucionAcademica.id !== undefined) {
      this.subscribeToSaveResponse(this.institucionAcademicaService.update(institucionAcademica));
    } else {
      this.subscribeToSaveResponse(this.institucionAcademicaService.create(institucionAcademica));
    }
  }

  private createFromForm(): IInstitucionAcademica {
    const entity = {
      ...new InstitucionAcademica(),
      id: this.editForm.get(['id']).value,
      institucion: this.editForm.get(['institucion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstitucionAcademica>>) {
    result.subscribe((res: HttpResponse<IInstitucionAcademica>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
