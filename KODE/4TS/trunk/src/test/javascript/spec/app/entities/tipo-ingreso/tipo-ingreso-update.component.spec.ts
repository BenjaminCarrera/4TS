/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoIngresoUpdateComponent } from 'app/entities/tipo-ingreso/tipo-ingreso-update.component';
import { TipoIngresoService } from 'app/entities/tipo-ingreso/tipo-ingreso.service';
import { TipoIngreso } from 'app/shared/model/tipo-ingreso.model';

describe('Component Tests', () => {
  describe('TipoIngreso Management Update Component', () => {
    let comp: TipoIngresoUpdateComponent;
    let fixture: ComponentFixture<TipoIngresoUpdateComponent>;
    let service: TipoIngresoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoIngresoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoIngresoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoIngresoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoIngresoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoIngreso(123);
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
        const entity = new TipoIngreso();
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
