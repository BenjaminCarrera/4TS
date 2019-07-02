/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoPeriodoUpdateComponent } from 'app/entities/tipo-periodo/tipo-periodo-update.component';
import { TipoPeriodoService } from 'app/entities/tipo-periodo/tipo-periodo.service';
import { TipoPeriodo } from 'app/shared/model/tipo-periodo.model';

describe('Component Tests', () => {
  describe('TipoPeriodo Management Update Component', () => {
    let comp: TipoPeriodoUpdateComponent;
    let fixture: ComponentFixture<TipoPeriodoUpdateComponent>;
    let service: TipoPeriodoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoPeriodoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoPeriodoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoPeriodoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPeriodoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoPeriodo(123);
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
        const entity = new TipoPeriodo();
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
