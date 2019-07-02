/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { FormacionAcademicaUpdateComponent } from 'app/entities/formacion-academica/formacion-academica-update.component';
import { FormacionAcademicaService } from 'app/entities/formacion-academica/formacion-academica.service';
import { FormacionAcademica } from 'app/shared/model/formacion-academica.model';

describe('Component Tests', () => {
  describe('FormacionAcademica Management Update Component', () => {
    let comp: FormacionAcademicaUpdateComponent;
    let fixture: ComponentFixture<FormacionAcademicaUpdateComponent>;
    let service: FormacionAcademicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [FormacionAcademicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormacionAcademicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormacionAcademicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormacionAcademicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FormacionAcademica(123);
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
        const entity = new FormacionAcademica();
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
