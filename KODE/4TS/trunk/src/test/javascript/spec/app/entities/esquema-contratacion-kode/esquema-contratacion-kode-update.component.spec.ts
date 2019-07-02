/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EsquemaContratacionKodeUpdateComponent } from 'app/entities/esquema-contratacion-kode/esquema-contratacion-kode-update.component';
import { EsquemaContratacionKodeService } from 'app/entities/esquema-contratacion-kode/esquema-contratacion-kode.service';
import { EsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';

describe('Component Tests', () => {
  describe('EsquemaContratacionKode Management Update Component', () => {
    let comp: EsquemaContratacionKodeUpdateComponent;
    let fixture: ComponentFixture<EsquemaContratacionKodeUpdateComponent>;
    let service: EsquemaContratacionKodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EsquemaContratacionKodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EsquemaContratacionKodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EsquemaContratacionKodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EsquemaContratacionKodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EsquemaContratacionKode(123);
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
        const entity = new EsquemaContratacionKode();
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
