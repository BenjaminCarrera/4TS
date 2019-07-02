/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ReferenciasLaboralesUpdateComponent } from 'app/entities/referencias-laborales/referencias-laborales-update.component';
import { ReferenciasLaboralesService } from 'app/entities/referencias-laborales/referencias-laborales.service';
import { ReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';

describe('Component Tests', () => {
  describe('ReferenciasLaborales Management Update Component', () => {
    let comp: ReferenciasLaboralesUpdateComponent;
    let fixture: ComponentFixture<ReferenciasLaboralesUpdateComponent>;
    let service: ReferenciasLaboralesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ReferenciasLaboralesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReferenciasLaboralesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReferenciasLaboralesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReferenciasLaboralesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReferenciasLaborales(123);
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
        const entity = new ReferenciasLaborales();
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
