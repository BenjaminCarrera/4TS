/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoSkillUpdateComponent } from 'app/entities/tipo-skill/tipo-skill-update.component';
import { TipoSkillService } from 'app/entities/tipo-skill/tipo-skill.service';
import { TipoSkill } from 'app/shared/model/tipo-skill.model';

describe('Component Tests', () => {
  describe('TipoSkill Management Update Component', () => {
    let comp: TipoSkillUpdateComponent;
    let fixture: ComponentFixture<TipoSkillUpdateComponent>;
    let service: TipoSkillService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoSkillUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoSkillUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoSkillUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoSkillService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoSkill(123);
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
        const entity = new TipoSkill();
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
