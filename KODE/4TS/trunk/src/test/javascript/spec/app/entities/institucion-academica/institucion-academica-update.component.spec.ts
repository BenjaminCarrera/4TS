/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { InstitucionAcademicaUpdateComponent } from 'app/entities/institucion-academica/institucion-academica-update.component';
import { InstitucionAcademicaService } from 'app/entities/institucion-academica/institucion-academica.service';
import { InstitucionAcademica } from 'app/shared/model/institucion-academica.model';

describe('Component Tests', () => {
  describe('InstitucionAcademica Management Update Component', () => {
    let comp: InstitucionAcademicaUpdateComponent;
    let fixture: ComponentFixture<InstitucionAcademicaUpdateComponent>;
    let service: InstitucionAcademicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [InstitucionAcademicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InstitucionAcademicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstitucionAcademicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstitucionAcademicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InstitucionAcademica(123);
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
        const entity = new InstitucionAcademica();
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
