/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { SkillRequerimientoUpdateComponent } from 'app/entities/skill-requerimiento/skill-requerimiento-update.component';
import { SkillRequerimientoService } from 'app/entities/skill-requerimiento/skill-requerimiento.service';
import { SkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';

describe('Component Tests', () => {
  describe('SkillRequerimiento Management Update Component', () => {
    let comp: SkillRequerimientoUpdateComponent;
    let fixture: ComponentFixture<SkillRequerimientoUpdateComponent>;
    let service: SkillRequerimientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [SkillRequerimientoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SkillRequerimientoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillRequerimientoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillRequerimientoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SkillRequerimiento(123);
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
        const entity = new SkillRequerimiento();
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
