/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusAcademicoUpdateComponent } from 'app/entities/estatus-academico/estatus-academico-update.component';
import { EstatusAcademicoService } from 'app/entities/estatus-academico/estatus-academico.service';
import { EstatusAcademico } from 'app/shared/model/estatus-academico.model';

describe('Component Tests', () => {
  describe('EstatusAcademico Management Update Component', () => {
    let comp: EstatusAcademicoUpdateComponent;
    let fixture: ComponentFixture<EstatusAcademicoUpdateComponent>;
    let service: EstatusAcademicoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusAcademicoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusAcademicoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusAcademicoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusAcademicoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusAcademico(123);
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
        const entity = new EstatusAcademico();
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
