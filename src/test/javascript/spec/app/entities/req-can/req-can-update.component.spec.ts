/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ReqCanUpdateComponent } from 'app/entities/req-can/req-can-update.component';
import { ReqCanService } from 'app/entities/req-can/req-can.service';
import { ReqCan } from 'app/shared/model/req-can.model';

describe('Component Tests', () => {
  describe('ReqCan Management Update Component', () => {
    let comp: ReqCanUpdateComponent;
    let fixture: ComponentFixture<ReqCanUpdateComponent>;
    let service: ReqCanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ReqCanUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReqCanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReqCanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReqCanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReqCan(123);
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
        const entity = new ReqCan();
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
