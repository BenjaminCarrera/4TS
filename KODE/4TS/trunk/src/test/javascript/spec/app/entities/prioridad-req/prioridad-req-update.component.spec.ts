/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { PrioridadReqUpdateComponent } from 'app/entities/prioridad-req/prioridad-req-update.component';
import { PrioridadReqService } from 'app/entities/prioridad-req/prioridad-req.service';
import { PrioridadReq } from 'app/shared/model/prioridad-req.model';

describe('Component Tests', () => {
  describe('PrioridadReq Management Update Component', () => {
    let comp: PrioridadReqUpdateComponent;
    let fixture: ComponentFixture<PrioridadReqUpdateComponent>;
    let service: PrioridadReqService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [PrioridadReqUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrioridadReqUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrioridadReqUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrioridadReqService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrioridadReq(123);
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
        const entity = new PrioridadReq();
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
