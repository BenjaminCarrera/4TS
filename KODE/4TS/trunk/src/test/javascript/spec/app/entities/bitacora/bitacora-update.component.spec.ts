/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BitacoraUpdateComponent } from 'app/entities/bitacora/bitacora-update.component';
import { BitacoraService } from 'app/entities/bitacora/bitacora.service';
import { Bitacora } from 'app/shared/model/bitacora.model';

describe('Component Tests', () => {
  describe('Bitacora Management Update Component', () => {
    let comp: BitacoraUpdateComponent;
    let fixture: ComponentFixture<BitacoraUpdateComponent>;
    let service: BitacoraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [BitacoraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BitacoraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BitacoraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BitacoraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bitacora(123);
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
        const entity = new Bitacora();
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
