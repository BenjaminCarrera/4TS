/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BaseTarifaUpdateComponent } from 'app/entities/base-tarifa/base-tarifa-update.component';
import { BaseTarifaService } from 'app/entities/base-tarifa/base-tarifa.service';
import { BaseTarifa } from 'app/shared/model/base-tarifa.model';

describe('Component Tests', () => {
  describe('BaseTarifa Management Update Component', () => {
    let comp: BaseTarifaUpdateComponent;
    let fixture: ComponentFixture<BaseTarifaUpdateComponent>;
    let service: BaseTarifaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [BaseTarifaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BaseTarifaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BaseTarifaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BaseTarifaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BaseTarifa(123);
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
        const entity = new BaseTarifa();
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
