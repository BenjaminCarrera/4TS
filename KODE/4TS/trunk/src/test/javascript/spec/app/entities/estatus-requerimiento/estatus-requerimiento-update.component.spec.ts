/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusRequerimientoUpdateComponent } from 'app/entities/estatus-requerimiento/estatus-requerimiento-update.component';
import { EstatusRequerimientoService } from 'app/entities/estatus-requerimiento/estatus-requerimiento.service';
import { EstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';

describe('Component Tests', () => {
  describe('EstatusRequerimiento Management Update Component', () => {
    let comp: EstatusRequerimientoUpdateComponent;
    let fixture: ComponentFixture<EstatusRequerimientoUpdateComponent>;
    let service: EstatusRequerimientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusRequerimientoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusRequerimientoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusRequerimientoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusRequerimientoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusRequerimiento(123);
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
        const entity = new EstatusRequerimiento();
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
