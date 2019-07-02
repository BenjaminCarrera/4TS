/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { DominioSkillUpdateComponent } from 'app/entities/dominio-skill/dominio-skill-update.component';
import { DominioSkillService } from 'app/entities/dominio-skill/dominio-skill.service';
import { DominioSkill } from 'app/shared/model/dominio-skill.model';

describe('Component Tests', () => {
  describe('DominioSkill Management Update Component', () => {
    let comp: DominioSkillUpdateComponent;
    let fixture: ComponentFixture<DominioSkillUpdateComponent>;
    let service: DominioSkillService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [DominioSkillUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DominioSkillUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DominioSkillUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DominioSkillService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DominioSkill(123);
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
        const entity = new DominioSkill();
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
