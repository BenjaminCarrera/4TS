/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { SkillCandidatoUpdateComponent } from 'app/entities/skill-candidato/skill-candidato-update.component';
import { SkillCandidatoService } from 'app/entities/skill-candidato/skill-candidato.service';
import { SkillCandidato } from 'app/shared/model/skill-candidato.model';

describe('Component Tests', () => {
  describe('SkillCandidato Management Update Component', () => {
    let comp: SkillCandidatoUpdateComponent;
    let fixture: ComponentFixture<SkillCandidatoUpdateComponent>;
    let service: SkillCandidatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [SkillCandidatoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SkillCandidatoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillCandidatoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillCandidatoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SkillCandidato(123);
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
        const entity = new SkillCandidato();
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
