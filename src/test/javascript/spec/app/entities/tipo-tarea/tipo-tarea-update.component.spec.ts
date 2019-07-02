/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoTareaUpdateComponent } from 'app/entities/tipo-tarea/tipo-tarea-update.component';
import { TipoTareaService } from 'app/entities/tipo-tarea/tipo-tarea.service';
import { TipoTarea } from 'app/shared/model/tipo-tarea.model';

describe('Component Tests', () => {
  describe('TipoTarea Management Update Component', () => {
    let comp: TipoTareaUpdateComponent;
    let fixture: ComponentFixture<TipoTareaUpdateComponent>;
    let service: TipoTareaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoTareaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoTareaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoTareaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoTareaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoTarea(123);
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
        const entity = new TipoTarea();
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
