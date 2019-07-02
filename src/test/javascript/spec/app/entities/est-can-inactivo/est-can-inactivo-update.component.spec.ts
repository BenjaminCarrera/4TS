/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstCanInactivoUpdateComponent } from 'app/entities/est-can-inactivo/est-can-inactivo-update.component';
import { EstCanInactivoService } from 'app/entities/est-can-inactivo/est-can-inactivo.service';
import { EstCanInactivo } from 'app/shared/model/est-can-inactivo.model';

describe('Component Tests', () => {
  describe('EstCanInactivo Management Update Component', () => {
    let comp: EstCanInactivoUpdateComponent;
    let fixture: ComponentFixture<EstCanInactivoUpdateComponent>;
    let service: EstCanInactivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstCanInactivoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstCanInactivoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstCanInactivoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstCanInactivoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstCanInactivo(123);
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
        const entity = new EstCanInactivo();
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
