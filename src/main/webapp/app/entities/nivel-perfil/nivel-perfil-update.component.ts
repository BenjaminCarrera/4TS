import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { INivelPerfil, NivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from './nivel-perfil.service';

@Component({
  selector: 'jhi-nivel-perfil-update',
  templateUrl: './nivel-perfil-update.component.html'
})
export class NivelPerfilUpdateComponent implements OnInit {
  nivelPerfil: INivelPerfil;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nivel: [null, [Validators.maxLength(200)]]
  });

  constructor(protected nivelPerfilService: NivelPerfilService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nivelPerfil }) => {
      this.updateForm(nivelPerfil);
      this.nivelPerfil = nivelPerfil;
    });
  }

  updateForm(nivelPerfil: INivelPerfil) {
    this.editForm.patchValue({
      id: nivelPerfil.id,
      nivel: nivelPerfil.nivel
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nivelPerfil = this.createFromForm();
    if (nivelPerfil.id !== undefined) {
      this.subscribeToSaveResponse(this.nivelPerfilService.update(nivelPerfil));
    } else {
      this.subscribeToSaveResponse(this.nivelPerfilService.create(nivelPerfil));
    }
  }

  private createFromForm(): INivelPerfil {
    const entity = {
      ...new NivelPerfil(),
      id: this.editForm.get(['id']).value,
      nivel: this.editForm.get(['nivel']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INivelPerfil>>) {
    result.subscribe((res: HttpResponse<INivelPerfil>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
