/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusCandidatoUpdateComponent } from 'app/entities/estatus-candidato/estatus-candidato-update.component';
import { EstatusCandidatoService } from 'app/entities/estatus-candidato/estatus-candidato.service';
import { EstatusCandidato } from 'app/shared/model/estatus-candidato.model';

describe('Component Tests', () => {
  describe('EstatusCandidato Management Update Component', () => {
    let comp: EstatusCandidatoUpdateComponent;
    let fixture: ComponentFixture<EstatusCandidatoUpdateComponent>;
    let service: EstatusCandidatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusCandidatoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusCandidatoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusCandidatoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusCandidatoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusCandidato(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusCandidato();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
