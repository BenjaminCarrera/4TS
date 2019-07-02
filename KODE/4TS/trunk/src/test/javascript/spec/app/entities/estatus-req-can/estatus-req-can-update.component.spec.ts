/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusReqCanUpdateComponent } from 'app/entities/estatus-req-can/estatus-req-can-update.component';
import { EstatusReqCanService } from 'app/entities/estatus-req-can/estatus-req-can.service';
import { EstatusReqCan } from 'app/shared/model/estatus-req-can.model';

describe('Component Tests', () => {
  describe('EstatusReqCan Management Update Component', () => {
    let comp: EstatusReqCanUpdateComponent;
    let fixture: ComponentFixture<EstatusReqCanUpdateComponent>;
    let service: EstatusReqCanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusReqCanUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusReqCanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusReqCanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusReqCanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusReqCan(123);
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
        const entity = new EstatusReqCan();
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
