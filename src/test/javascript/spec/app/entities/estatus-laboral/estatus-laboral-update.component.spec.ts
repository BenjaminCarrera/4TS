/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusLaboralUpdateComponent } from 'app/entities/estatus-laboral/estatus-laboral-update.component';
import { EstatusLaboralService } from 'app/entities/estatus-laboral/estatus-laboral.service';
import { EstatusLaboral } from 'app/shared/model/estatus-laboral.model';

describe('Component Tests', () => {
  describe('EstatusLaboral Management Update Component', () => {
    let comp: EstatusLaboralUpdateComponent;
    let fixture: ComponentFixture<EstatusLaboralUpdateComponent>;
    let service: EstatusLaboralService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusLaboralUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusLaboralUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusLaboralUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusLaboralService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusLaboral(123);
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
        const entity = new EstatusLaboral();
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
