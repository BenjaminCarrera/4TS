/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { SkillCandidatoDetailComponent } from 'app/entities/skill-candidato/skill-candidato-detail.component';
import { SkillCandidato } from 'app/shared/model/skill-candidato.model';

describe('Component Tests', () => {
  describe('SkillCandidato Management Detail Component', () => {
    let comp: SkillCandidatoDetailComponent;
    let fixture: ComponentFixture<SkillCandidatoDetailComponent>;
    const route = ({ data: of({ skillCandidato: new SkillCandidato(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [SkillCandidatoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SkillCandidatoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillCandidatoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skillCandidato).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
