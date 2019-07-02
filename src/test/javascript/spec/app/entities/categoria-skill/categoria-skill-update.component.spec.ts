/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { CategoriaSkillUpdateComponent } from 'app/entities/categoria-skill/categoria-skill-update.component';
import { CategoriaSkillService } from 'app/entities/categoria-skill/categoria-skill.service';
import { CategoriaSkill } from 'app/shared/model/categoria-skill.model';

describe('Component Tests', () => {
  describe('CategoriaSkill Management Update Component', () => {
    let comp: CategoriaSkillUpdateComponent;
    let fixture: ComponentFixture<CategoriaSkillUpdateComponent>;
    let service: CategoriaSkillService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [CategoriaSkillUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriaSkillUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaSkillUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaSkillService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriaSkill(123);
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
        const entity = new CategoriaSkill();
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
