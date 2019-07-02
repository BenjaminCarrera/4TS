/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusReqCanRecUpdateComponent } from 'app/entities/estatus-req-can-rec/estatus-req-can-rec-update.component';
import { EstatusReqCanRecService } from 'app/entities/estatus-req-can-rec/estatus-req-can-rec.service';
import { EstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';

describe('Component Tests', () => {
  describe('EstatusReqCanRec Management Update Component', () => {
    let comp: EstatusReqCanRecUpdateComponent;
    let fixture: ComponentFixture<EstatusReqCanRecUpdateComponent>;
    let service: EstatusReqCanRecService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusReqCanRecUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstatusReqCanRecUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstatusReqCanRecUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusReqCanRecService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstatusReqCanRec(123);
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
        const entity = new EstatusReqCanRec();
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
