/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EsqContRecUpdateComponent } from 'app/entities/esq-cont-rec/esq-cont-rec-update.component';
import { EsqContRecService } from 'app/entities/esq-cont-rec/esq-cont-rec.service';
import { EsqContRec } from 'app/shared/model/esq-cont-rec.model';

describe('Component Tests', () => {
  describe('EsqContRec Management Update Component', () => {
    let comp: EsqContRecUpdateComponent;
    let fixture: ComponentFixture<EsqContRecUpdateComponent>;
    let service: EsqContRecService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EsqContRecUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EsqContRecUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EsqContRecUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EsqContRecService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EsqContRec(123);
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
        const entity = new EsqContRec();
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
