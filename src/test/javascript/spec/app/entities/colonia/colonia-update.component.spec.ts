/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ColoniaUpdateComponent } from 'app/entities/colonia/colonia-update.component';
import { ColoniaService } from 'app/entities/colonia/colonia.service';
import { Colonia } from 'app/shared/model/colonia.model';

describe('Component Tests', () => {
  describe('Colonia Management Update Component', () => {
    let comp: ColoniaUpdateComponent;
    let fixture: ComponentFixture<ColoniaUpdateComponent>;
    let service: ColoniaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ColoniaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ColoniaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ColoniaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColoniaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Colonia(123);
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
        const entity = new Colonia();
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
