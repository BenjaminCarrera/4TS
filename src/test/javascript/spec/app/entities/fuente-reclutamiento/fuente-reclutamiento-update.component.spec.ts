/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { FuenteReclutamientoUpdateComponent } from 'app/entities/fuente-reclutamiento/fuente-reclutamiento-update.component';
import { FuenteReclutamientoService } from 'app/entities/fuente-reclutamiento/fuente-reclutamiento.service';
import { FuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';

describe('Component Tests', () => {
  describe('FuenteReclutamiento Management Update Component', () => {
    let comp: FuenteReclutamientoUpdateComponent;
    let fixture: ComponentFixture<FuenteReclutamientoUpdateComponent>;
    let service: FuenteReclutamientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [FuenteReclutamientoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FuenteReclutamientoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuenteReclutamientoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuenteReclutamientoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FuenteReclutamiento(123);
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
        const entity = new FuenteReclutamiento();
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
