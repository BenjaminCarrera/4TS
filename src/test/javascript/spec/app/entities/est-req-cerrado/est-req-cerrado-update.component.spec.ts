/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstReqCerradoUpdateComponent } from 'app/entities/est-req-cerrado/est-req-cerrado-update.component';
import { EstReqCerradoService } from 'app/entities/est-req-cerrado/est-req-cerrado.service';
import { EstReqCerrado } from 'app/shared/model/est-req-cerrado.model';

describe('Component Tests', () => {
  describe('EstReqCerrado Management Update Component', () => {
    let comp: EstReqCerradoUpdateComponent;
    let fixture: ComponentFixture<EstReqCerradoUpdateComponent>;
    let service: EstReqCerradoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstReqCerradoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstReqCerradoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstReqCerradoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstReqCerradoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstReqCerrado(123);
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
        const entity = new EstReqCerrado();
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
