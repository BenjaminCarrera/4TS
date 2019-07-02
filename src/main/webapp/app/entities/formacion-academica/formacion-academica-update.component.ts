import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFormacionAcademica, FormacionAcademica } from 'app/shared/model/formacion-academica.model';
import { FormacionAcademicaService } from './formacion-academica.service';

@Component({
  selector: 'jhi-formacion-academica-update',
  templateUrl: './formacion-academica-update.component.html'
})
export class FormacionAcademicaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    formacionAcademica: []
  });

  constructor(
    protected formacionAcademicaService: FormacionAcademicaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ formacionAcademica }) => {
      this.updateForm(formacionAcademica);
    });
  }

  updateForm(formacionAcademica: IFormacionAcademica) {
    this.editForm.patchValue({
      id: formacionAcademica.id,
      formacionAcademica: formacionAcademica.formacionAcademica
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const formacionAcademica = this.createFromForm();
    if (formacionAcademica.id !== undefined) {
      this.subscribeToSaveResponse(this.formacionAcademicaService.update(formacionAcademica));
    } else {
      this.subscribeToSaveResponse(this.formacionAcademicaService.create(formacionAcademica));
    }
  }

  private createFromForm(): IFormacionAcademica {
    return {
      ...new FormacionAcademica(),
      id: this.editForm.get(['id']).value,
      formacionAcademica: this.editForm.get(['formacionAcademica']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormacionAcademica>>) {
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
