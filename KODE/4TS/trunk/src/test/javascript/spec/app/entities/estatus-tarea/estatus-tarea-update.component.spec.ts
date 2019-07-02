/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusTareaUpdateComponent } from 'app/entities/estatus-tarea/estatus-tarea-update.component';
import { EstatusTareaService } from 'app/entities/estatus-tarea/estatus-tarea.service';
import { EstatusTarea } from 'app/shared/model/estatus-tarea.model';

describe('Component Tests', () => {
  describe('EstatusTarea Management Update Component', () => {
    let comp: EstatusTareaUpdateComponent;
    let fixture: ComponentFixture<EstatusTareaUpdateComponent>;
    let service: EstatusTareaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusTareaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusTareaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusTareaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusTareaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusTarea(123);
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
        const entity = new EstatusTarea();
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
